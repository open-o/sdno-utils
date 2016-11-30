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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.openo.sdno.exception.InnerErrorServiceException;

import mockit.Mock;
import mockit.MockUp;

/**
 * JavaEntityUtil test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-27
 */
public class JavaEntityUtilTest {

    private static final String FIELD_NAME1 = "name";

    private static final String FIELD_NAME2 = "newname";

    private static final String FIELD_NAME3 = "lastname";

    @Test
    public void testGetFieldValueByName() throws InnerErrorServiceException {
        Name o = new Name();
        Object getobj = o.getName();
        Object obj = JavaEntityUtil.getFieldValueByName(FIELD_NAME1, o);
        assertTrue(getobj.equals(obj));
    }

    @Test
    public void testGetFieldValueByNameNegtive() throws InnerErrorServiceException {
        new MockUp<ReflectionUtil>() {

            @Mock
            public Object getFieldValue(Object object, String fieldName) {
                return null;
            }
        };
        new MockUp<Class>() {

            @Mock
            public Method getMethod(String name, Class<?>... parameterTypes)
                    throws NoSuchMethodException, SecurityException {
                throw new NoSuchMethodException();
            }
        };

        Name o = new Name();
        assertEquals(null, JavaEntityUtil.getFieldValueByName(FIELD_NAME1, o));
    }

    @Test
    public void testGetFiledValues() throws InnerErrorServiceException {
        Name obj = new Name();
        Map<String, Object> testMap = JavaEntityUtil.getFiledValues(obj);
        assertEquals(testMap.get(FIELD_NAME1), obj.getName());
        assertEquals(testMap.get(FIELD_NAME2), obj.getNewname());
        assertEquals(testMap.get(FIELD_NAME3), obj.getLastname());        
    }

    @Test
    public void testGetValueObjectList() throws InnerErrorServiceException {
        Map<String, Object> fieldMap = JavaEntityUtil.getFiledValues(new Name());
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        mapList.add(fieldMap);

        List<Object> valueList = JavaEntityUtil.getValueObjectList(mapList, Name.class);
        Object Test_object1 = new Name();
        assertEquals(Test_object1, valueList.get(0));
    }

    @Test(expected = InnerErrorServiceException.class)
    public void testGetValueObjectListNegtive() throws InnerErrorServiceException {
        new MockUp<Class>() {

            @Mock
            public T newInstance() throws InstantiationException, IllegalAccessException {
                throw new InstantiationException();
            }
        };

        Map<String, Object> fieldMap = JavaEntityUtil.getFiledValues(new Name());
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        mapList.add(fieldMap);

        JavaEntityUtil.getValueObjectList(mapList, Name.class);
    }

    @Test
    public void testGetAllField() {
        List<Field> fieldList = JavaEntityUtil.getAllField(Child.class);
        assertTrue(!fieldList.isEmpty());
    }

    @Test
    public void testGetFiledType() throws NoSuchFieldException, SecurityException {
        Class fieldList = JavaEntityUtil.getFiledType(CollectionClass.class.getField("testList"));
        assertTrue(fieldList.equals(String.class));
    }
}

class CollectionClass {

    public List<String> testList;
}

class Name {

    private String name = "1";

    private String newname = "2";

    private String lastname = "3";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewname() {
        return newname;
    }

    public void setNewname(String newname) {
        this.newname = newname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Name() {

    }

    public Name(String name, String newname, String lastname) {
        this.name = name;
        this.newname = newname;
        this.lastname = lastname;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((newname == null) ? 0 : newname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        Name other = (Name)obj;
        if(lastname == null) {
            if(other.lastname != null)
                return false;
        } else if(!lastname.equals(other.lastname))
            return false;
        if(name == null) {
            if(other.name != null)
                return false;
        } else if(!name.equals(other.name))
            return false;
        if(newname == null) {
            if(other.newname != null)
                return false;
        } else if(!newname.equals(other.newname))
            return false;
        return true;
    }
}

class Parent {

    protected String field;
}

class Child extends Parent {

}
