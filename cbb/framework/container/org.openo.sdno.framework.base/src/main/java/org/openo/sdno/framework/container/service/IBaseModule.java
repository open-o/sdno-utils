/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.framework.container.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Module base class, the business of module all inherit this module.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-3-26
 */
public abstract class IBaseModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(IBaseModule.class);

    /**
     * Module startup.<br/>
     * 
     * @since SDNO 0.5
     */
    public final void start() {
        try {
            doStart();
            init();
        } catch(Exception e) {
            LOGGER.error("module start error", e);
        }
    }

    /**
     * Module stopping method.<br/>
     * 
     * @since SDNO 0.5
     */
    public final void stop() {
        try {
            doStop();
            destroy();
        } catch(Exception e) {
            LOGGER.error("module start stop", e);
        }
    }

    /**
     * Sub class module coverage of the start action to carry out the registration of resources,
     * etc.<br/>
     * 
     * @since SDNO 0.5
     */
    protected void doStart() {
        // to do
    }

    /**
     * Sub class module coverage of the stop action, to carry out cancel the registration of the
     * resources, etc.<br/>
     * 
     * @since SDNO 0.5
     */
    protected void doStop() {
        // to do
    }

    /**
     * Do initialization.<br/>
     * 
     * @since SDNO 0.5
     */
    protected abstract void init();

    /**
     * Do destroying.<br/>
     * 
     * @since SDNO 0.5
     */
    protected abstract void destroy();

}
