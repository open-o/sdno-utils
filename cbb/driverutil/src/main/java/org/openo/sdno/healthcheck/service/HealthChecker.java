/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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
package org.openo.sdno.healthcheck.service;

import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Health Checker Class.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version     SDNO 0.5  Feb 8, 2017
 */
public abstract class HealthChecker {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthChecker.class);

    private static final String MSB_URL_SERVICES = "/api/microservices/v1/services";

    /**
     * 
     * Check DB Connection.<br/>
     * 
     * @param statusMap
     * @since  SDNO 0.5
     */
    public abstract void checkDbConnection(Map<String, String> statusMap);

    /**
     * 
     * Check MSB Connection method.<br/>
     * 
     * @param statusMap
     * @since  SDNO 0.5
     */
    public void checkMsbConnection(Map<String, String> statusMap) {
        RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");
        restParametes.setRawData(JsonUtil.toJson(""));

        try {
            RestfulProxy.get(MSB_URL_SERVICES, restParametes);
            statusMap.put("MSB", "UP");
        } catch (ServiceException e) {
            LOGGER.error("MSB is not reachable", e);
            statusMap.put("MSB", "DOWN");
        }

    }
}
