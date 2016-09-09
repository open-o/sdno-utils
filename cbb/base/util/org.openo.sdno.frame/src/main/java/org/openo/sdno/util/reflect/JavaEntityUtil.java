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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.sdno.exception.InnerErrorServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Java entity class, containing methods to operate the filed and value in the object.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-13
 */
public class JavaEntityUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaEntityUtil.class);

    private JavaEntityUtil() {
    }

    /**
     * Get the object attitudes, returns an array of strings.<br>
     * 
     * @param o Object that contains field names
     * @return Collection of field names
     * @since SDNO 0.5
     */
    private static String[] getFiledName(Object o) throws InnerErrorServiceException {
        try {
            List<Field> fields = JavaEntityUtil.getAllField(o.getClass());
            String[] fieldNames = new String[fields.size()];
            int i = 0;
            for(Field field : fields) {
                fieldNames[i++] = field.getName();
            }
            return fieldNames;
        } catch(SecurityException e) {
            LOGGER.error(e.toString());
            throw new InnerErrorServiceException(e);
        }
    }

    /**
     * Reflect to get field value by field name.<br>
     * 
     * @param fieldName Field name
     * @param o Object that contains the field
     * @return Field value
     * @throws InnerErrorServiceException if get method failed
     * @since SDNO 0.5
     */
    public static Object getFieldValueByName(String fieldName, Object o) throws InnerErrorServiceException {
        Object value = ReflectionUtil.getFieldValue(o, fieldName);
        if(value != null) {
            return value;
        }
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = getMethod(fieldName, o, getter);
            return method.invoke(o, new Object[] {});
        } catch(NoSuchMethodException ex) {
            LOGGER.warn("", ex);
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            throw new InnerErrorServiceException(e);
        }
        return null;
    }

    private static Method getMethod(String fieldName, Object o, String getter) throws NoSuchMethodException {
        Method method = null;
        try {
            method = o.getClass().getMethod(getter, new Class[] {});
        } catch(Exception e) {
            LOGGER.warn("getFieldValueByName getMethod error.", e);
            method = o.getClass().getMethod("get" + fieldName, new Class[] {});
        }
        return method;
    }

    /**
     * Set value of the specified field in the object, support the enumeration type.<br>
     * 
     * @param field The specified field to be modified
     * @param o The object where the field exists
     * @param value The value wanting to set
     * @return Object after modified
     * @since SDNO 0.5
     */
    public static Object setFieldValue(Field field, Object o, Object value) {
        Object v = value;
        if(field.getType().isEnum()) {
            v = ReflectionUtil.newEnumInstance(field.getType(), v.toString());
        }
        // If the field type in the Java class is not consistent with the assigned parameter, a
        // strong rotation is required. After the incoming object is converted to a string, the
        // parse method is used to parse the incoming object. The data type supported by the current
        // is integer, float, and long.
        if((v != null) && !field.getType().equals(v.getClass())) {
            try {
                if(field.getType().equals(Long.class)) {
                    v = Long.parseLong(v.toString());
                } else if(field.getType().equals(Float.class)) {
                    v = Float.parseFloat(v.toString());
                } else if(field.getType().equals(Integer.class)) {
                    v = Integer.parseInt(v.toString());
                } else {
                    LOGGER.warn("Dismatch type for class is " + field.getType().toString() + " but value is "
                            + v.getClass().toString());
                }
            } catch(NumberFormatException e) {
                LOGGER.warn(e.getMessage());
            }
        }
        ReflectionUtil.setFieldValue(o, field.getName(), v);
        return o;
    }

    /**
     * Reflect to get field value.<br>
     * 
     * @param fieldName Field name
     * @param o Object that contains the field
     * @return Field value
     * @since SDNO 0.5
     */
    public static Object getFieldValue(String fieldName, Object o) {
        return ReflectionUtil.getFieldValue(o, fieldName);
    }

    /**
     * Gets all the field values of the object.<br>
     * 
     * @param o Object that contains the field
     * @return Map of field names and values
     * @since SDNO 0.5
     */
    public static Map<String, Object> getFiledValues(Object o) throws InnerErrorServiceException {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        String[] fieldNames = getFiledName(o);
        Object[] value = new Object[fieldNames.length];
        for(int i = 0; i < fieldNames.length; i++) {
            value[i] = getFieldValueByName(fieldNames[i], o);
            if(value[i] != null) {
                valuesMap.put(fieldNames[i], value[i]);
            }
        }
        return valuesMap;
    }

    /**
     * Get object list by specified field values.<br>
     * 
     * @param mapList Collection of specified field names and values
     * @param clazz class object
     * @return objects list of specified field names and values
     * @throws InnerErrorServiceException if create class object failed
     * @since SDNO 0.5
     */
    public static List<Object> getValueObjectList(List<Map<String, Object>> mapList, Class clazz)
            throws InnerErrorServiceException {
        List<Object> valueList = new ArrayList<Object>();

        try {
            List<Field> fieldList = JavaEntityUtil.getAllField(clazz);

            for(Map<String, Object> amap : mapList) {
                Object o = clazz.newInstance();
                for(Field field : fieldList) {
                    if(amap.containsKey(field.getName())) {
                        setFieldValue(field, o, amap.get(field.getName()));
                    }
                }
                valueList.add(o);
            }
        } catch(InstantiationException e) {
            LOGGER.error(e.toString());
            throw new InnerErrorServiceException(e);
        } catch(IllegalAccessException e) {
            LOGGER.error(e.toString());
            throw new InnerErrorServiceException(e);
        }
        return valueList;
    }

    /**
     * Gets all the fields of the object, including the public, private, and parent class.<br>
     * 
     * @param clazz Object class
     * @return Collection of all fields
     * @since SDNO 0.5
     */
    public static List<Field> getAllField(Class clazz) {
        List<Field> allfield = new ArrayList<Field>();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            allfield.add(field);
        }
        fields = clazz.getFields();
        for(Field field : fields) {
            if(!allfield.contains(field)) {
                allfield.add(field);
            }
        }
        Class<?> superclass = clazz.getSuperclass();
        if(superclass != null) {
            JavaEntityUtil.getFields(superclass, allfield);
        }
        return allfield;
    }

    /**
     * Gets all the fields of the class.<br>
     * 
     * @param clazz Class object
     * @param allfield Collection of all fields
     * @since SDNO 0.5
     */
    public static void getFields(Class clazz, List<Field> allfield) {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            if(!allfield.contains(field)) {
                allfield.add(field);
            }
        }
        fields = clazz.getFields();
        for(Field field : fields) {
            if(!allfield.contains(field)) {
                allfield.add(field);
            }
        }
        Class<?> superclass = clazz.getSuperclass();
        if(superclass != null) {
            JavaEntityUtil.getFields(superclass, allfield);
        }
    }

    /**
     * Gets the field type, and if the field is a container, the generic type in the container is
     * taken.<br>
     * 
     * @param field The specified field
     * @return Field type
     * @since SDNO 0.5
     */
    public static Class getFiledType(Field field) {
        Class fieldClazz = field.getType();
        if(Collection.class.isAssignableFrom(fieldClazz) || Map.class.isAssignableFrom(fieldClazz)) {
            Type fc = field.getGenericType();
            // If it is a type of generic parameter.
            if(fc instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType)fc;
                // Get the class type object in the generic.
                return (Class)pt.getActualTypeArguments()[0];
            }
        }
        return fieldClazz;
    }
}
