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

package org.openo.sdno.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Astract class of properties load. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-16
 */
public abstract class ResourceLoader {

    private static Class<?> clazz = DefaultResourceLoader.class;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceLoader.class);

    private ResourceLoader() {
        // Private constructor to prohibit instantiation.
    }

    /**
     * Set loder calss for properties file load. <br>
     * 
     * @param clazzImpl Loder class.
     * @since SDNO 0.5
     */
    public static void setLoaderClass(Class<?> clazzImpl) {
        clazz = clazzImpl;
    }

    /**
     * Create loader.<br>
     * 
     * @return ResourceLoader class instance.
     * @since SDNO 0.5
     */
    public static ResourceLoader createLoader() {
        try {
            return (ResourceLoader)clazz.newInstance();
        } catch(InstantiationException e) {
            LOGGER.error("", e);
        } catch(IllegalAccessException e) {
            LOGGER.error("", e);
        }

        return null;
    }

    /**
     * Load properties file. <br>
     * 
     * @param is InputStream.
     * @throws IOException IOException
     * @since SDNO 0.5
     */
    public abstract void load(InputStream is) throws IOException;

    /**
     * Get keys in properties. <br>
     * 
     * @return Keys iterator.
     * @since SDNO 0.5
     */
    public abstract Iterator<String> getKeys();

    /**
     * Get value by key. <br>
     * 
     * @param key Key
     * @return Value
     * @since SDNO 0.5
     */
    public abstract String getValue(String key);

    /**
     * Class for properties load. <br>
     * 
     * @author
     * @version SDNO 0.5 2016-6-16
     */
    public static class DefaultResourceLoader extends ResourceLoader {

        Properties props = null;

        @Override
        public void load(InputStream is) throws IOException {
            props = new Properties();
            props.load(is);
        }

        @Override
        public Iterator<String> getKeys() {
            final Enumeration<Object> keys = props.keys();

            return new Iterator<String>() {

                @Override
                public boolean hasNext() {
                    return keys.hasMoreElements();
                }

                @Override
                public String next() {
                    return (String)keys.nextElement();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public String getValue(String key) {
            return props.getProperty(key);
        }
    }
}
