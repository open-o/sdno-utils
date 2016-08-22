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

package org.openo.sdno.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tools class of reflection.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-5-19
 */
public class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    private ReflectionUtil() {

    }

    /**
     * Used to access private members of the parent class.<br/>
     * 
     * @param object The object containing the field
     * @param fieldName Field name
     * @return Field value
     * @since SDNO 0.5
     */
    public static Object getFieldValue(Object object, String fieldName) {
        try {
            final Field field = ClassFieldManager.getInstance().getFiled(object.getClass(), fieldName);
            if(field != null) {
                AccessController.doPrivileged(new PrivilegedAction<Object>() {

                    @Override
                    public Object run() {
                        field.setAccessible(true);
                        return null;
                    }
                });
                return field.get(object);
            }
        } catch(IllegalArgumentException | IllegalAccessException | SecurityException e) {
            LOGGER.warn("", e);
        }
        return null;
    }

    /**
     * Gets the value of a static member by reflection.<br/>
     * 
     * @param classVar Object class
     * @param fieldName Field name
     * @return Field value
     * @since SDNO 0.5
     */
    public static Object getFieldValue(Class<?> classVar, String fieldName) {
        try {
            final Field field = ClassFieldManager.getInstance().getFiled(classVar, fieldName);
            if(field != null) {
                AccessController.doPrivileged(new PrivilegedAction<Object>() {

                    @Override
                    public Object run() {
                        field.setAccessible(true);
                        return null;
                    }
                });
                return field.get(null);
            }
        } catch(IllegalArgumentException | IllegalAccessException e) {
            LOGGER.warn("", e);
        }
        return null;
    }

    /**
     * Set value of the specified field in the object, used to set private members.<br/>
     * 
     * @param object The object containing the field
     * @param fieldName Field name
     * @param value The value wanting to set
     * @since SDNO 0.5
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        try {
            final Field field = ClassFieldManager.getInstance().getFiled(object.getClass(), fieldName);
            if(field != null) {
                AccessController.doPrivileged(new PrivilegedAction<Object>() {

                    @Override
                    public Object run() {
                        field.setAccessible(true);
                        return null;
                    }
                });
                field.set(object, value);
                return;
            }
        } catch(IllegalArgumentException | IllegalAccessException e) {
            LOGGER.warn("", e);
        }
    }

    /**
     * Used to set the private members in specified class.<br/>
     * 
     * @param object The object containing the field
     * @param clazz Specified class
     * @param fieldName Field name
     * @param value The value wanting to set
     * @since SDNO 0.5
     */
    public static void setFieldValue(Object object, Class<?> clazz, String fieldName, Object value) {
        try {
            // Only find in specified class.
            final Field field = clazz.getDeclaredField(fieldName);
            if(field != null) {
                AccessController.doPrivileged(new PrivilegedAction<Object>() {

                    @Override
                    public Object run() {
                        field.setAccessible(true);
                        return null;
                    }
                });
                field.set(object, value);
                return;
            }
        } catch(IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            LOGGER.warn("", e);
        }
    }

    /**
     * Invoke the method of the object.<br/>
     * 
     * @param object Destination object
     * @param methodName Invoke method name
     * @param argtypes arguments types
     * @param args arguments
     * @return Function execution result
     * @since SDNO 0.5
     */
    public static Object invokeMethod(Object object, String methodName, Class[] argtypes, Object... args) {
        final Method method = getNamedMethod(object.getClass(), methodName, argtypes);
        if(method != null) {
            AccessController.doPrivileged(new PrivilegedAction<Object>() {

                @Override
                public Object run() {
                    method.setAccessible(true);
                    return null;
                }
            });
            try {
                return method.invoke(object, args);
            } catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.warn("", e);
            }

        }
        return null;
    }

    /**
     * Invoke the static function.
     * 
     * @param destClass Destination class
     * @param methodName Invoke method name
     * @param argtypes arguments types
     * @param args arguments
     * @return Function execution result
     */
    public static Object invokeStaticMethod(Class destClass, String methodName, Class[] argtypes, Object... args) {
        final Method method = getNamedMethod(destClass, methodName, argtypes);
        if(method != null) {
            AccessController.doPrivileged(new PrivilegedAction<Object>() {

                @Override
                public Object run() {
                    method.setAccessible(true);
                    return null;
                }
            });

            try {
                return method.invoke(null, args);
            } catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.warn("", e);
            }

        }
        return null;
    }

    private static Method getNamedMethod(Class<?> clazz, String methodName, Class<?>... args) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, args);
        } catch(SecurityException e) {
            LOGGER.warn("", e);
        } catch(NoSuchMethodException e) {
            LOGGER.warn("", e);
            // If current class does not have the method, looking for the super class.
            Class<?> superclass = clazz.getSuperclass();
            if(superclass != null) {
                method = getNamedMethod(superclass, methodName, args);
            }
        }
        return method;
    }

    /**
     * Incoming enumeration class object, as well as the string of enumeration value, return the
     * corresponding enumeration.<br/>
     * 
     * @param clazz Class object
     * @param value enumeration value
     * @return Enumeration
     * @since SDNO 0.5
     */
    public static Object newEnumInstance(Class<?> clazz, String value) {
        Object[] objs = clazz.getEnumConstants();
        if(objs != null) {
            for(Object enuCons : objs) {
                if(enuCons.toString().equals(value)) {
                    return enuCons;
                }
            }
        }
        return null;
    }
}
