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
package org.openo.sdno.healthcheck.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.openo.sdno.healthcheck.service.HealthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Driver Health Check ROA Class.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version     SDNO 0.5  Feb 8, 2017
 */
@Service
@Path("/{driver}/healthcheck")
public class HealthCheckRoaResource {
    
    @Autowired
    private HealthChecker checker;
    
    /**
     * 
     * Get the driver status.<br/>
     * 
     * @return
     * @since  SDNO 0.5
     */
    @GET
    @Produces({"application/json"})
    public Map<String, String> getstatus() {
        
        Map<String, String> statusMap = new HashMap<>();
        
        checker.checkDbConnection(statusMap);
                
        checker.checkMsbConnection(statusMap);
        
        return statusMap;
    }
}
