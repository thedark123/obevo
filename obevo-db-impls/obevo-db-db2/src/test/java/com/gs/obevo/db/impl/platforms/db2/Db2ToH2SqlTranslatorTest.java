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
package com.gs.obevo.db.impl.platforms.db2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Db2ToH2SqlTranslatorTest {
    private final Db2ToH2SqlTranslator translator = new Db2ToH2SqlTranslator();

    @Test
    public void testPostColumnIdentity() throws Exception {
        assertEquals("auto_increment  NOT NULL",
                this.translator.handlePostColumnText("GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1, CACHE 20) NOT NULL", null, null));
        assertEquals("auto_increment  NULL",
                this.translator.handlePostColumnText("GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1, CACHE 20) NULL", null, null));
        assertEquals("auto_increment NOT NULL",
                this.translator.handlePostColumnText("GENERATED BY DEFAULT AS IDENTITY NOT NULL", null, null));
        assertEquals("auto_increment NULL",
                this.translator.handlePostColumnText("GENERATED BY DEFAULT AS IDENTITY NULL", null, null));
        assertEquals("auto_increment ",
                this.translator.handlePostColumnText("GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1, CACHE 20)", null, null));
        assertEquals("auto_increment ",
                this.translator.handlePostColumnText("GENERATED BY DEFAULT AS IDENTITY", null, null));
    }
}