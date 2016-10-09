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

package org.openo.sdno.framework.container.msb;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Microservice Bus Registration and UnRegistration.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-10-08
 */
public class MsbManage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsbManage.class);

    private static final String MSB_REGISTION_FILE = "generalconfig/msbRegistion.json";

    private static final String MSB_REGISTION_URL = "/openoapi/microservices/v1/services?createOrUpdate=false";

    private static final String MSB_UN_REGISTION_URL =
            "/openoapi/microservices/v1/services/{0}/version/{1}/nodes/{2}/{3}";

    private static final String NODES = "nodes";

    private static final String IP = "ip";

    private static final String PORT = "port";

    private static final String SERVICE_NAME = "serviceName";

    private static final String VERSION = "version";

    public static final int REPEAT_REG_TIME = 30 * 1000;

    private boolean bRegistionSuccess = false;

    private Map<?, ?> msbRegistionBodyMap = null;

    /**
     * Module startup. <br/>
     * 
     * @since SDNO 0.5
     */
    public final void start() {
        try {
            register();
        } catch(Exception e) {
            LOGGER.error("module start error", e);
        }
    }

    /**
     * Module stopping method. <br/>
     * 
     * @since SDNO 0.5
     */
    public final void stop() {
        try {
            unRegister();
        } catch(Exception e) {
            LOGGER.error("module start stop", e);
        }
    }

    private void register() {
        File file = new File(MSB_REGISTION_FILE);
        if(!file.exists()) {
            LOGGER.info("Stop registering as can't find msb registion file");
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            byte[] bytes = Files.readAllBytes(Paths.get(MSB_REGISTION_FILE));
            msbRegistionBodyMap = mapper.readValue(bytes, Map.class);
        } catch(IOException e) {
            LOGGER.error("Failed to get microservice bus registration body, " + e);
            return;
        }

        replaceLocalIp();

        RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");
        restParametes.setRawData(JsonUtil.toJson(msbRegistionBodyMap));

        LOGGER.error("Registering body: " + JsonUtil.toJson(msbRegistionBodyMap));

        RegisterThread registerThread = new RegisterThread(restParametes);
        Executors.newSingleThreadExecutor().submit(registerThread);
    }

    @SuppressWarnings("unchecked")
    private void unRegister() {
        if(!bRegistionSuccess) {
            LOGGER.info("Stop Unregister as has not registered success");
            return;
        }

        bRegistionSuccess = false;

        String serviceName = (String)msbRegistionBodyMap.get(SERVICE_NAME);
        String version = (String)msbRegistionBodyMap.get(VERSION);

        List<Map<String, String>> nodes = (List<Map<String, String>>)msbRegistionBodyMap.get(NODES);
        Map<String, String> node = nodes.get(0);
        String ip = node.get(IP);
        String port = node.get(PORT);

        String url = MessageFormat.format(MSB_UN_REGISTION_URL, serviceName, version, ip, port);

        LOGGER.info("Start Unregister to microservice bus, url: " + url);

        try {
            RestfulParametes restParametes = new RestfulParametes();
            restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

            RestfulResponse response = RestfulProxy.delete(url, restParametes);
            if(isSuccess(response.getStatus())) {
                LOGGER.info("Unregister successfully");
            } else {
                LOGGER.warn("Unregister unsuccessfully");
            }
        } catch(Exception e) {
            LOGGER.error("Unregister exception, " + e);
        }
    }

    @SuppressWarnings("unchecked")
    private void replaceLocalIp() {
        List<Map<String, String>> nodes = (List<Map<String, String>>)msbRegistionBodyMap.get(NODES);
        Map<String, String> node = nodes.get(0);
        if(StringUtils.isNotEmpty(node.get(IP))) {
            return;
        }

        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ipAddress = addr.getHostAddress();
            node.put(IP, ipAddress);

            LOGGER.info("Local ip: " + ipAddress);
        } catch(UnknownHostException e) {
            LOGGER.error("Unable to get IP address, " + e);
        }
    }

    private boolean isSuccess(int httpCode) {
        return httpCode / 100 == 2;
    }

    private class RegisterThread implements Runnable {

        // Thread lock Object
        private final Object lockObject = new Object();

        RestfulParametes restParametes;

        public RegisterThread(RestfulParametes restParametes) {
            this.restParametes = restParametes;
        }

        @Override
        public void run() {
            while(!bRegistionSuccess) {
                sendRequest(restParametes);
            }
        }

        private void sendRequest(RestfulParametes restParametes) {
            LOGGER.info("Start registering to microservice bus");

            try {
                RestfulResponse response = RestfulProxy.post(MSB_REGISTION_URL, restParametes);
                if(isSuccess(response.getStatus())) {
                    LOGGER.info("Register successfully");
                    bRegistionSuccess = true;
                    return;
                } else {
                    LOGGER.warn("Register unsuccessfully and will reattempt the connection after " + REPEAT_REG_TIME
                            + " seconds");
                }
            } catch(ServiceException e) {
                LOGGER.error("Register exception, " + e);
            }

            // if registration fails, wait and try again
            try {
                synchronized(lockObject) {
                    lockObject.wait(REPEAT_REG_TIME);
                }
            } catch(InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

}
