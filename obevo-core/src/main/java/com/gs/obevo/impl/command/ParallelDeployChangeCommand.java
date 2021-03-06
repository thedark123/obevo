/**
 * Copyright 2017 Goldman Sachs.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.gs.obevo.impl.command;

import java.util.List;

import com.gs.obevo.api.appdata.Change;
import com.gs.obevo.api.appdata.DeployExecution;
import com.gs.obevo.api.platform.ChangeAuditDao;
import com.gs.obevo.api.platform.CommandExecutionContext;
import com.gs.obevo.impl.ChangeTypeBehaviorRegistry;
import com.gs.obevo.impl.ExecuteChangeCommand;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.collection.ImmutableCollection;
import org.eclipse.collections.impl.parallel.ParallelIterate;

public class ParallelDeployChangeCommand implements ExecuteChangeCommand {
    private final String schema;
    private final int numThreads;
    private final ImmutableCollection<? extends Change> changes;
    private boolean drop = false;

    public ParallelDeployChangeCommand(String schema, ImmutableCollection<? extends Change> changes, int numThreads) {
        this.changes = changes;
        this.schema = schema;
        this.numThreads = numThreads;
    }

    @Override
    public void execute(final ChangeTypeBehaviorRegistry changeTypeBehaviorRegistry, final CommandExecutionContext cec) {
        // 2 value -> only fork to parallelism if we have 2 tasks. 1 task will not require thread pool usage
        ParallelIterate.forEach(changes, new Procedure<Change>() {
            @Override
            public void value(Change change) {
                changeTypeBehaviorRegistry.deploy(change, cec);
            }
        }, 2, numThreads);
    }

    @Override
    public boolean isDrop() {
        return drop;
    }

    @Override
    public ExecuteChangeCommand withDrop(boolean drop) {
        this.drop = drop;
        return this;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    @Override
    public void markAuditTable(ChangeTypeBehaviorRegistry changeTypeBehaviorRegistry, ChangeAuditDao artifactDeployerDao, DeployExecution deployExecution) {
        for (Change change : changes) {
            changeTypeBehaviorRegistry.manage(change, artifactDeployerDao, deployExecution);
        }
    }

    @Override
    public List<Change> getChanges() {
        return (List<Change>) changes.toList();
    }

    @Override
    public String getCommandDescription() {
        return "Parallel set of changes:\n" + changes.collect(Change::getDisplayString).makeString("\n");
    }
}
