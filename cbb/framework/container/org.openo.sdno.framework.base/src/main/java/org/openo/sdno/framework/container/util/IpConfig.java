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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openo.sdno.framework.container.resthelper.RestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to get IP address from configuration file.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 31, 2016
 */
public class IpConfig {

    private static final String IP_CONFIG = "ipconfig.properties";

    private static final String MAX_IP_ADDRESS = "maxIpAddress";

    private static final String MIN_IP_ADDRESS = "minIpAddress";

    private static final String MASK_IP_ADDRESS = "maskIpAddress";

    private static final String LOCAL_HOST = "localHost";

    private static Properties properties = new Properties();

    private static final Logger LOGGER = LoggerFactory.getLogger(IpConfig.class);

    private IpConfig() {
    }

    /**
     * Get max IP address.<br>
     * 
     * @return Max IP address
     * @since SDNO 0.5
     */
    public static String getMaxIpAddress() {
        loadProperties();
        return properties.getProperty(MAX_IP_ADDRESS, "");
    }

    /**
     * Get minimum IP address.<br>
     * 
     * @return Minimum IP address
     * @since SDNO 0.5
     */
    public static String getMinIpAddress() {
        loadProperties();
        return properties.getProperty(MIN_IP_ADDRESS, "");
    }

    /**
     * Get IP address with mask.<br>
     * 
     * @return IP address with mask
     * @since SDNO 0.5
     */
    public static String getMaskIpAddress() {
        loadProperties();
        return properties.getProperty(MASK_IP_ADDRESS, "");
    }

    /**
     * Get local host IP address.<br>
     * 
     * @return Local host IP address
     * @since SDNO 0.5
     */
    public static String getLocalHost() {
        loadProperties();
        return properties.getProperty(LOCAL_HOST, "");
    }

    private static void loadProperties() {
        try {
            InputStream fin = RestProcessor.class.getClassLoader().getResourceAsStream(IP_CONFIG);
            properties.load(fin);
        } catch(IOException e) {
            LOGGER.error("Load Property File failed!", e);
        }
    }
}
