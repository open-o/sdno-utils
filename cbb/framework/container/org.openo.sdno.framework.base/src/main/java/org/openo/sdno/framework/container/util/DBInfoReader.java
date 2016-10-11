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

package org.openo.sdno.framework.container.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Read the database information in the AppVer file which under the OSS3.0 platform.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-3-26
 */
public class DBInfoReader {

    public static final String SERVER_NAME = "serverName";

    public static final String PWD = "passwd";

    public static final String USER = "user";

    public static final String PORT = "port";

    private static String APP_CONF_FILE = DefaultEnvUtil.getAppConfFile();

    private static final Logger LOGGER = LoggerFactory.getLogger(DBInfoReader.class);

    public static final Object TYPE = "type";

    private DBInfoReader() {

    }

    /**
     * Read the DB information.<br/>
     * 
     * @param appName Application name as string
     * @return The DB information map
     * @since SDNO 0.5
     */
    public static Map<String, Map<String, Object>> readDbInfo(String appName) {
        Map<String, Map<String, Object>> dbInfoMap = new HashMap<String, Map<String, Object>>();

        File appCfgJson = new File(APP_CONF_FILE);

        InputStream is = null;
        try {
            LOGGER.info("Begin read database datasouce file {}", appCfgJson.getName());

            is = new FileInputStream(appCfgJson);
            parseJson2Map(IOUtils.toString(is), dbInfoMap, "databases");

            LOGGER.info("End read database datasouce file");
            is.close();
        } catch(IOException e) {
            IOUtils.closeQuietly(is);
            LOGGER.info("Read database datasource file failed:{}", e);
        }

        LOGGER.info("Read database datasouce file finished, count  = {}", dbInfoMap.size());
        return dbInfoMap;
    }

    @SuppressWarnings("unchecked")
    private static void parseJson2Map(String jsonStr, Map<String, Map<String, Object>> infoMap, String type) {
        // Analysis of the outermost
        JSONObject json = JSONObject.fromObject(jsonStr);

        Object dbObj = json.get(type);
        // Configure service which has the corresponding type
        if(dbObj != null) {
            JSONObject db = JSONObject.fromObject(dbObj);
            // Traversal of all instances
            for(Object insKey : db.keySet()) {
                Object ins = db.get(insKey);
                if(!(ins instanceof JSONArray)) {
                    LOGGER.error("Invalid db instance, key = {}", insKey);
                    continue;
                }

                // Traversal of all REDIS services configured in this instance
                Iterator<JSONObject> it = ((JSONArray)ins).iterator();
                while(it.hasNext()) {
                    JSONObject srv = it.next();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.putAll(srv);
                    infoMap.put(map.get("dbName").toString(), map);
                }
            }
        }
    }
}
