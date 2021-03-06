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
package com.gs.obevo.impl;

import com.gs.obevo.api.appdata.ChangeInput;
import org.eclipse.collections.api.list.ImmutableList;

/**
 * Strategy to determine how changes are read in and passed to the Obevo deployer.
 */
public interface SourceReaderStrategy {
    ImmutableList<ChangeInput> getChanges();

    ImmutableList<ChangeInput> getChanges(boolean useBaseline);
}
