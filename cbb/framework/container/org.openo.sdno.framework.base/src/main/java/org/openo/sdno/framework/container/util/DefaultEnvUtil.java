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
import java.util.Locale;

import org.openo.baseservice.util.impl.SystemEnvVariablesFactory;
import org.openo.baseservice.util.inf.SystemEnvVariables;

/**
 * System environment class, get application root path.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-12
 */
public class DefaultEnvUtil {

    private static SystemEnvVariables systemEnv = SystemEnvVariablesFactory.getInstance();

    private static Locale locale = null;

    private static String appVersion;

    private static String appName;

    private static String localRegion;

    private DefaultEnvUtil() {

    }

    public static String getOssRoot() {
        return "";
    }

    public static String getAppRoot() {
        return System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
                + "resources";
    }

    public static String getSslRoot() {
        return "";
    }

    public static String getOssPubRoot() {
        return "";
    }

    public static String getAppName() {

        return "InvSvc";
    }

    public static String getAppVersion() {
        return "1.0.1";
    }

    public static String getAppLogDir() {
        return "";
    }

    public static String getAppTmpDir() {

        return (new StringBuilder()).append(systemEnv.getAppRoot()).append("/tmp").toString();

    }

    public static String getAppShareDir() {
        return "";
    }

    public static String getAppConfFile() {
        return "";
    }

    public static String getLocalRegion() {
        return "";
    }

    public static String getRuntimeCenterRoot() {
        return "";
    }

    public static String getKernelRtspRoot() {
        return "";
    }

    public static String getThirdPartyRoot() {
        return "";
    }

    public static String getProcessName() {
        return "inv";
    }

    public static String getLocalHostFloatServerName() {
        return "";
    }

    public static String getOssLang() {
        return "";
    }

    public static String getPIDFile() {
        return "";
    }

    public static String getHelpConfRoot() {
        return "";
    }

}
