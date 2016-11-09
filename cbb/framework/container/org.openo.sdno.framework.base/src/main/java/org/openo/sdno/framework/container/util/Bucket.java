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

import java.util.ArrayList;
import java.util.List;

/**
 * Basic description information of Bucket.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-5-18
 */
public class Bucket {

    private String name;

    private String owner;

    private String authorization;

    private List<String> allowread = new ArrayList<String>();

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param name Bucket name
     * @param owner Bucket owner
     * @param authorization Authorization info
     * @param allowread Allowed reader list
     */
    public Bucket(String name, String owner, String authorization, List<String> allowread) {
        super();
        this.setName(name);
        this.owner = owner;
        this.authorization = authorization;
        this.allowread = allowread;
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param name Bucket name
     * @param owner Bucket owner
     * @param authorization Authorization info
     */
    public Bucket(String name, String owner, String authorization) {
        super();
        this.setName(name);
        this.owner = owner;
        this.authorization = authorization;
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    public Bucket() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public List<String> getAllowread() {
        return allowread;
    }

    public void setAllowread(List<String> allowread) {
        this.allowread = allowread;
    }

}
