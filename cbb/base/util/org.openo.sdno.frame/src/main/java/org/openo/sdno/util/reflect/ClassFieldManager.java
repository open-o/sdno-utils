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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reflect to lookup field or class definition.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-13
 */
public class ClassFieldManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassFieldManager.class);

    private final Map<FieldKey, Field> fieldCache = new Hashtable<FieldKey, Field>();

    private final Map<Class, List<Field>> allFieldCache = new Hashtable<Class, List<Field>>();

    private static final ClassFieldManager INSTANCE = new ClassFieldManager();

    private ClassFieldManager() {

    }

    /**
     * Get the instance of ClassFieldManager.<br>
     * 
     * @return Instance of ClassFieldManager
     * @since SDNO 0.5
     */
    public static ClassFieldManager getInstance() {
        return INSTANCE;
    }

    /**
     * Get the specified field from class object.<br>
     * 
     * @param clazz Class object
     * @param fieldName Field name
     * @return The specified field
     * @since SDNO 0.5
     */
    public Field getFiled(Class<?> clazz, String fieldName) {
        FieldKey key = new FieldKey(clazz.getName(), fieldName);
        if(!fieldCache.containsKey(key)) {
            Field field = getNamedField(clazz, fieldName);
            if(field != null) {
                fieldCache.put(key, field);
            }
            return field;
        } else {
            return fieldCache.get(key);
        }
    }

    /**
     * Get all fields from class object.<br>
     * 
     * @param clazz Class object
     * @return Collection of all fields
     * @since SDNO 0.5
     */
    public List<Field> getAllField(Class clazz) {
        if(allFieldCache.containsKey(clazz)) {
            return allFieldCache.get(clazz);
        } else {
            List<Field> fields = JavaEntityUtil.getAllField(clazz);
            allFieldCache.put(clazz, fields);
            return fields;
        }
    }

    private Field getNamedField(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch(SecurityException e) {
            LOGGER.warn("", e);
        } catch(NoSuchFieldException e) {
            LOGGER.warn("", e);
            // If current class does not have the field, looking for super class.
            Class<?> superclass = clazz.getSuperclass();
            if(superclass != null) {
                field = getNamedField(superclass, fieldName);
            }
        }

        return field;
    }
}

/**
 * Lookup the key of the field in the system.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-13
 */
class FieldKey {

    private String className;

    private String fieldName;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public FieldKey(String className, String fieldName) {
        this.className = className;
        this.fieldName = fieldName;
    }

    /**
     * Get hash code of current object.<br>
     * 
     * @return Hash code of current object
     * @since SDNO 0.5
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((className == null) ? 0 : className.hashCode());
        result = (prime * result) + ((fieldName == null) ? 0 : fieldName.hashCode());
        return result;
    }

    /**
     * Check whether the object is equal to the current object.<br>
     * 
     * @param obj Object to be checked
     * @return Return true if equal, otherwise returns false
     * @since SDNO 0.5
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        FieldKey other = (FieldKey)obj;
        if(className == null) {
            if(other.className != null) {
                return false;
            }
        } else if(!className.equals(other.className)) {
            return false;
        }
        if(fieldName == null) {
            if(other.fieldName != null) {
                return false;
            }
        } else if(!fieldName.equals(other.fieldName)) {
            return false;
        }
        return true;
    }

    /**
     * @return Returns the className.
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className The className to set.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return Returns the fieldName.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName The fieldName to set.
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
