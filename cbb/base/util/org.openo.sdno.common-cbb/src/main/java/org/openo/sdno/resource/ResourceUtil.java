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

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for properties load, property get. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-16
 */
public class ResourceUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtil.class);

    private static final String RES_DIR = "WEB-INF/properties";

    private static Set<String> containedApps = new HashSet<String>();

    private static Map<String, String> resMap = Collections.synchronizedMap(new HashMap<String, String>());

    private static String[] fileTypes = {".properties"};

    private ResourceUtil() {
        // Private constructor to prohibit instantiation.
    }

    /**
     * Load all the properties in application properties folder.<br/>
     * 
     * @param appPath Application root directory.
     * @param appName Application name.
     * @return Operation result, 0 for success, -1 for failed.
     * @since SDNO 0.5
     */
    public static int addAppProperties(String appPath, String appName) {
        if(containedApps.contains(appName)) {
            return -1;
        }

        containedApps.add(appName);

        String propsPath = appPath + File.separator + RES_DIR;
        LOGGER.info("load App Properties from path {}", propsPath);
        loadPropertiesFromDir(propsPath);

        return 0;

    }

    /**
     * Get message from resource map; <br/>
     * 
     * @param key Key of resource to get.
     * @return Value of key, if can't find in map return key as String instead.
     * @since SDNO 0.5
     */
    public static String getMessage(String key) {
        return resMap.get(key) == null ? key : resMap.get(key);
    }

    private static int loadPropertiesFromDir(String dirPath) {

        LOGGER.info("load Properties from dir path {}", dirPath);
        File file = new File(dirPath);
        if(!file.exists() || !file.canRead()) {
            LOGGER.info("load AppProperties fail from path {}, maybe i18n directory not exist!", dirPath);
            return -1;

        }

        String[] files = file.list(new MyFilter(fileTypes));
        if(files == null) {
            LOGGER.info("there are no any Properties files in the path {}", dirPath);
            return -1;
        }

        for(String fileName : files) {
            String fullFileName = dirPath + File.separator + fileName;
            LOGGER.info("load properties from file:{}", fullFileName);
            ResourceLoader loader = loadProperties(fullFileName);
            putPropsIntoMap(loader);
        }
        return 0;
    }

    private static ResourceLoader loadProperties(String filename) {
        ResourceLoader loader = ResourceLoader.createLoader();
        File propsFile = new File(filename);
        if(!propsFile.exists() || !propsFile.canRead() || propsFile.isDirectory()) {
            LOGGER.info("load propsfile fail from file {}", filename);
            return null;
        }

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(propsFile);
            loader.load(fileInputStream);

        } catch(IOException e) {
            LOGGER.error("load Properties error: {}", e);
        } finally {
            try {
                if(fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch(IOException e) {
                LOGGER.error("close IO exception: {}", e);
            }
        }
        return loader;

    }

    private static void putPropsIntoMap(ResourceLoader loader) {
        Iterator<String> keys = loader.getKeys();
        while(keys.hasNext()) {
            String key = keys.next();
            resMap.put(key, loader.getValue(key));
        }
    }

    /**
     * Class for file type filter. <br/>
     * 
     * @author
     * @version SDNO 0.5 2016-6-15
     */
    private static class MyFilter implements FilenameFilter {

        /**
         * File types filter wanted.
         */
        private final String[] fileTypes;

        /**
         * Constructor<br/>
         * 
         * @param fileTypes List of file types(.extensionName).
         * @since SDNO 0.5
         */
        public MyFilter(String[] fileTypes) {
            this.fileTypes = fileTypes;
        }

        @Override
        public boolean accept(File dir, String name) {

            if(name != null) {
                for(String fileType : this.fileTypes) {
                    if(name.endsWith(fileType)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

}
