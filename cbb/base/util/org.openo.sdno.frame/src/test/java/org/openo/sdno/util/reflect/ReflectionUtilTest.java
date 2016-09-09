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

import java.lang.reflect.Field;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;
import org.openo.sdno.util.reflect.ReflectionUtil;

/**
 * ReflectionUtil test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-19
 */
public class ReflectionUtilTest {

    private static final String FIELD1 = "field1";

    private static final String FIELD2 = "field2";

    private static final String FIELD3 = "field3";

    private static final String FIELD4 = "field4";

    private static final String FIELD_VALUE = "field_static";

    @Test
    public void testGetFieldValueParent() {
        TestReflection obj = new TestReflection(FIELD1, FIELD2, FIELD3);
        assertEquals(FIELD1, ReflectionUtil.getFieldValue(obj, FIELD1));
    }

    @Test
    public void testGetFieldValueParentNegtive() {
        NegtiveMock();
        TestReflection obj = new TestReflection(FIELD1, FIELD2, FIELD3);
        assertEquals(null, ReflectionUtil.getFieldValue(obj, FIELD1));
    }

    @Test
    public void testGetFieldValueStatic() {
        assertEquals(FIELD_VALUE, ReflectionUtil.getFieldValue(TestReflection.class, FIELD4));
    }

    @Test
    public void testGetFieldValueStaticNegtive() {
        NegtiveMock();
        assertEquals(null, ReflectionUtil.getFieldValue(TestReflection.class, FIELD4));
    }

    @Test
    public void testSetFieldValueObject() {
        TestReflection obj = new TestReflection(FIELD1, FIELD2, FIELD3);
        ReflectionUtil.setFieldValue(obj, FIELD1, FIELD_VALUE);
        assertEquals(FIELD_VALUE, ReflectionUtil.getFieldValue(obj, FIELD1));
    }

    @Test
    public void testSetFieldValueObjectNegtive() {
        TestReflection obj1 = new TestReflection(FIELD1, FIELD2, FIELD3);
        TestObject obj2 = new TestObject(FIELD1, FIELD2);
        ReflectionUtil.setFieldValue(obj1, FIELD1, obj2);
        assertEquals(FIELD1, ReflectionUtil.getFieldValue(obj1, FIELD1));
    }

    @Test
    public void testSetFieldValueClass() {
        TestReflection obj = new TestReflection(FIELD1, FIELD2, FIELD3);
        ReflectionUtil.setFieldValue(obj, TestReflection.class, FIELD3, FIELD_VALUE);
        assertEquals(FIELD_VALUE, ReflectionUtil.getFieldValue(obj, FIELD3));
    }

    @Test
    public void testSetFieldValueClassNegtive() {
        TestReflection obj1 = new TestReflection(FIELD1, FIELD2, FIELD3);
        TestObject obj2 = new TestObject(FIELD1, FIELD2);
        ReflectionUtil.setFieldValue(obj1, TestReflection.class, FIELD3, obj2);
        assertEquals(FIELD3, ReflectionUtil.getFieldValue(obj1, FIELD3));
    }

    @Test
    public void testInvokeMethod() {
        TestReflection obj = new TestReflection(FIELD1, FIELD2, FIELD3);
        assertEquals(FIELD1, ReflectionUtil.invokeMethod(obj, "getField1", null, null));
    }

    @Test
    public void testInvokeMethodNegtive() {
        TestReflection obj = new TestReflection(FIELD1, FIELD2, FIELD3);
        assertEquals(null, ReflectionUtil.invokeMethod(obj, "getField3", null, obj));
    }

    @Test
    public void testInvokeStaticMethod() {
        assertEquals(FIELD_VALUE, ReflectionUtil.invokeStaticMethod(TestReflection.class, "getField4", null, null));
    }

    @Test
    public void testInvokeStaticMethodNegtive() {
        TestReflection obj = new TestReflection(FIELD1, FIELD2, FIELD3);
        assertEquals(null, ReflectionUtil.invokeStaticMethod(TestReflection.class, "getField4", null, obj));
    }

    @Test
    public void testNewEnumInstance() {
        assertEquals(TestEnum.BBB, ReflectionUtil.newEnumInstance(TestEnum.class, "BBB"));
    }

    private void NegtiveMock() {
        new MockUp<Field>() {

            @Mock
            public Object get(Object obj) throws IllegalArgumentException, IllegalAccessException {
                throw new IllegalArgumentException();
            }
        };
    }
}

class TestReflection extends TestObject {

    private String field3;

    private static String field4 = "field_static";

    public TestReflection(String field1, String field2, String field3) {
        super(field1, field2);
        this.field3 = field3;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public static String getField4() {
        return field4;
    }

}

enum TestEnum {
    AAA("a"), BBB("b"), CCC("c");

    private String value;

    TestEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
