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

import org.openo.baseservice.util.impl.SystemEnvVariablesFactory;
import org.openo.baseservice.util.inf.SystemEnvVariables;

/**
 * Utility class to provide interfaces to operate upon the system or environment settings.<br/>
 * 
 * @author
 * @version SDNO 0.5 24-Mar-2016
 */
public class SysEnvironment {

    private static final String LOOP_BACK_ADDRESS = "127.0.0.1";

    /**
     * Application configuration directory.
     */
    private static final String CONFIG = "config";

    private static final String RTSP_CACHE = "puer_cache";

    private static final String MODULE = "module";

    /**
     * private Constructor to avoid instantiation.<br/>
     * 
     * @since SDNO 0.5
     */
    private SysEnvironment() {
    }

    /**
     * Get the application root directory.<br/>
     * 
     * @return path of the application root directory
     * @since SDNO 0.5
     */
    public static final String getHome() {
        return DefaultEnvUtil.getOssRoot();
    }

    /**
     * System Global configuration directory.<br/>
     * 
     * @return path of the Global configuration directory
     * @since SDNO 0.5
     */
    public static final String getGlobalEtcPath() {
        return SystemEnvVariablesFactory.getInstance().getAppRoot() + File.separator + "rtsp" + File.separator + RTSP_CACHE
                + File.separator + "etc";
    }

    /**
     * Application configuration directory.<br/>
     * 
     * @return path of the Application configuration directory
     * @since SDNO 0.5
     */
    public static final String getEtcPath() {
        return SystemEnvVariablesFactory.getInstance().getAppRoot() + File.separator + "etc";
    }

    /**
     * Get the parent of Application configuration directory.<br/>
     * 
     * @return path of the parent of Application configuration directory
     * @since SDNO 0.5
     */
    public static final String getAppRoot() {
        return SystemEnvVariablesFactory.getInstance().getAppRoot();
    }

    /**
     * Get specific application path.<br/>
     * 
     * @param appName specific app name
     * @return path of the specific application
     * @since SDNO 0.5
     */
    public static final String getAppPath(String appName) {
        return getAppRoot() + File.separator + MODULE + File.separator + appName;
    }

    /**
     * Get specific application config directory.<br/>
     * 
     * @param appName specific application name
     * @return specific application config directory path
     * @since SDNO 0.5
     */
    public static final String getConfPath(String appName) {
        return getAppPath(appName) + File.separator + CONFIG;
    }

    /**
     * Get specific application var directory.<br/>
     * 
     * @return Get specific application var directory
     * @since SDNO 0.5
     */
    public static final String getVarPath() {
        return DefaultEnvUtil.getAppShareDir();
    }

    /**
     * Get the current OS language information.<br/>
     * 
     * @return the current OS language information
     * @since SDNO 0.5
     */
    public static final String getLanguage() {
        return DefaultEnvUtil.getOssLang();
    }

    /**
     * Get the loopback IP address 127.0.0.1.<br/>
     * 
     * @return the loopback IP address 127.0.0.1
     * @since SDNO 0.5
     */
    public static final String getLocalHostIp() {
        return LOOP_BACK_ADDRESS;
    }
}
