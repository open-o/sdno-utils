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

package org.openo.sdno.util.reflect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.sdno.util.reflect.ClassFieldManager;
import org.openo.sdno.util.reflect.FieldKey;
import org.openo.sdno.util.reflect.JavaEntityUtil;

/**
 * ClassFieldManager test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-18
 */
public class ClassFieldManagerTest {

    private static final String FIELD_NAME1 = "field1";

    @Test
    public void testGetFiled() throws NoSuchFieldException, SecurityException {
        Field test1 = ClassFieldManager.getInstance().getFiled(TestObject.class, FIELD_NAME1);
        Field test2 = TestObject.class.getDeclaredField(FIELD_NAME1);
        assertEquals(test1, test2);
    }

    @Test
    public void testAllFiled() throws NoSuchFieldException, SecurityException {
        List<Field> testList1 = ClassFieldManager.getInstance().getAllField(TestObject.class);
        List<Field> testList2 = new ArrayList<Field>();
        JavaEntityUtil.getFields(TestObject.class, testList2);
        assertEquals(testList1, testList2);
    }

    @Test
    public void testFieldKeyEqual() {
        FieldKey key = new FieldKey(TestObject.class.getName(), FIELD_NAME1);
        assertTrue(key.equals(new FieldKey(TestObject.class.getName(), FIELD_NAME1)));
    }
}

class TestObject {

    private String field1;

    private String field2;

    public TestObject(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

}
