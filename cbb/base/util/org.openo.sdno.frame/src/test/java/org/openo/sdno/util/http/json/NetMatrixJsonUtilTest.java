/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.util.http.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;
import org.openo.sdno.util.http.json.NetMatrixJsonUtil;

/**
 * NetMatrixJsonUtil test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-18
 */
public class NetMatrixJsonUtilTest {

    @Test
    public void testParser1() {
        try {
            Map testMap = new HashMap();
            testMap.put("id", 1);
            Map<String, String> childMap = new HashMap<String, String>();
            childMap.put("name", "childname");
            testMap.put("child", childMap);
            List<Map> childrenMap = new ArrayList<Map>();
            childMap = new HashMap<String, String>();
            childMap.put("name", "children1name");
            childrenMap.add(childMap);
            childMap = new HashMap<String, String>();
            childMap.put("name", "children2name");
            childrenMap.add(childMap);
            testMap.put("children", childrenMap);
            TestParserClass obj = NetMatrixJsonUtil.parser(testMap, TestParserClass.class);
            assertTrue(obj.id == 1);
            assertTrue(obj.child.name.equals("childname"));
        } catch(Exception e) {
            fail("NetMatrixJsonUtil parser failed." + e.getMessage());
        }
    }

    @Test
    public void testParser2() {
        new MockUp<Class>() {

            @Mock
            public Method newInstance() throws InstantiationException, IllegalAccessException {
                throw new InstantiationException();
            }
        };

        Map testMap = new HashMap();

        assertEquals(null, NetMatrixJsonUtil.parser(testMap, TestParserClass.class));
    }

    @Test
    public void testParser3() {
        new MockUp<Class>() {

            @Mock
            public Method newInstance() throws InstantiationException, IllegalAccessException {
                throw new IllegalAccessException();
            }
        };

        Map testMap = new HashMap();

        assertEquals(null, NetMatrixJsonUtil.parser(testMap, TestParserClass.class));
    }

}
