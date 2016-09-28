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

package org.openo.sdno.framework.container.service;

/**
 * <br/>
 * <p>
 * External abstract parent resource class, this class do not do any business logic, mostly used
 * only as an interface adapter and encapsulated in IService business for developing resource. Calls
 * are made to IService service interface from this class but there will be no direct business logic
 * in here.
 * </p>
 * 
 * @param policy
 * @param <T> generic parameter.
 * @author
 * @version SDNO 0.5 24-Mar-2016
 */
public abstract class IResource<T extends IService> {

    /**
     * Business package interface.
     */
    protected T service;

    /**
     * Setting service.<br/>
     * 
     * @param service the service that is to be set for this resource
     * @since SDNO 0.5
     */
    public void setService(T service) {
        this.service = service;
    }

    /**
     * Access to resources Resource Description uri description that is static uri.<br/>
     * 
     * @return Uri of the resource
     * @since SDNO 0.5
     */
    public abstract String getResUri();
}
