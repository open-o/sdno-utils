/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.resource;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class ResourceUtilTest {

    @Test
    public void testAddAppProperties() {
        String appPath = "src" + File.separator + "test" + File.separator + "resources";
        int result = ResourceUtil.addAppProperties(appPath, "test1");
        assertEquals(result, 0);
    }

    @Test
    public void testAddAppPropertiesContains() {
        String appPath = "src" + File.separator + "test" + File.separator + "resources";
        ResourceUtil.addAppProperties(appPath, "test");
        int result = ResourceUtil.addAppProperties(appPath, "test");
        assertEquals(result, -1);
    }

    @Test
    public void testGetMessage() {
        String appPath = "src" + File.separator + "test" + File.separator + "resources";
        ResourceUtil.addAppProperties(appPath, "test");
        String message = ResourceUtil.getMessage("serverport");
        assertEquals(message, "8080");
    }

    @Test
    public void testAddAppPropertiesNull() {
        String appPath = "src" + File.separator + "test" + File.separator + "resource";
        int result = ResourceUtil.addAppProperties(appPath, "test2");
        assertEquals(result, 0);
    }

}
