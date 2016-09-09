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

package org.openo.sdno.frame;

import java.util.Map;

/**
 * Information class of service parameters.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-28
 */
public class ServiceParasInfo {

    /**
     * Controller uuid.
     */
    private String cltuuid;

    /**
     * Operate resource.
     */
    private String resource;

    /**
     * Service body.
     */
    private String serviceBody;

    private Map<String, String[]> queryMap;

    private String classPath;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param cltuuid Controller uuid to set
     * @param resource resource to set
     * @param serviceBody serviceBody to set
     * @param queryMap queryMap to set
     * @param classPath classPath to set
     */
    public ServiceParasInfo(String cltuuid, String resource, String serviceBody, Map<String, String[]> queryMap,
            String classPath) {
        super();
        this.cltuuid = cltuuid;
        this.resource = resource;
        this.serviceBody = serviceBody;
        this.queryMap = queryMap;
        this.classPath = classPath;
    }

    public String getCltuuid() {
        return cltuuid;
    }

    public void setCltuuid(String cltuuid) {
        this.cltuuid = cltuuid;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getServiceBody() {
        return serviceBody;
    }

    public void setServiceBody(String serviceBody) {
        this.serviceBody = serviceBody;
    }

    public Map<String, String[]> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(Map<String, String[]> queryMap) {
        this.queryMap = queryMap;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }
}
