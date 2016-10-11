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

import java.util.ArrayList;
import java.util.List;

import org.openo.sdno.framework.base.threadpool.Task;
import org.openo.sdno.framework.base.threadpool.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ROA released the module needs to be inherited, only the external publication Roa services, do not
 * allow Rest Service.<br/>
 * 
 * @author
 * @version SDNO 0.5 24-March-2016
 */
public abstract class IRoaModule extends IBaseModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(IRoaModule.class);

    private String webContextPath = "";

    private String target = "";

    /**
     * War root path or package path for the webApp subsystem to load.
     */
    private String webResourcePath = "";

    /**
     * ROA list of resources by spring configuration IOC dependency injection.
     */
    private List<IResource<?>> roaResList = new ArrayList<IResource<?>>();

    /**
     * @param webResourcePath The webResourcePath to set.
     */
    public void setWebResourcePath(String webResourcePath) {
        this.webResourcePath = webResourcePath;
    }

    /**
     * @param webContextPath The webContextPath to set.
     */
    public void setWebContextPath(String webContextPath) {
        this.webContextPath = webContextPath;
    }

    /**
     * @param target The target to set.
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @param roaResList The roaResList to set.
     */
    public final void setRoaResList(List<IResource<?>> roaResList) {
        this.roaResList = roaResList;
    }

    /**
     * Called when the module starts.<br/>
     * 
     * @since SDNO 0.5
     */
    @Override
    protected void doStart() {
        registerRoaResource();
        ThreadPool.getInstance().execute(new RegisterWebAppTask());
    }

    /**
     * Called when the module stops.<br/>
     * 
     * @since SDNO 0.5
     */
    @Override
    protected void doStop() {
        unRegisterRoaResource();
        ThreadPool.getInstance().execute(new UnRegisterWebAppTask());
    }

    /**
     * To register ROA resources.<br/>
     * 
     * @since SDNO 0.5
     */
    protected final void registerRoaResource() {
        for(IResource<?> res : roaResList) {
            if(res != null) {
                res.getClass().getName();
                LOGGER.info("ROA.addRestfulService");
            }
        }
    }

    /**
     * To unregister ROA resources.<br/>
     * 
     * @since SDNO 0.5
     */
    protected final void unRegisterRoaResource() {
        for(IResource<?> res : roaResList) {
            if(res != null) {
                LOGGER.info("ROA.removeRestfulService");
            }
        }
    }

    /**
     * Register webApp thread pool task.<br/>
     * 
     * @author
     * @version SDNO 0.5 24-Mar-2016
     */
    private class RegisterWebAppTask extends Task {

        /**
         * Cover method, for task execution business logic.<br/>
         * 
         * @since SDNO 0.5
         */
        @Override
        public void doTask() {
            // deleted
        }

        /**
         * returns the task name for the current task.<br/>
         * 
         * @return Register web APP task
         * @since SDNO 0.5
         */
        @Override
        public String getTaskName() {
            return "RegisterWebAppTask";
        }
    }

    /**
     * To un-register WebApp thread pool task.<br/>
     * 
     * @author
     * @version SDNO 0.5 24-Mar-2016
     */
    private class UnRegisterWebAppTask extends Task {

        /**
         * Entry point of this task.<br/>
         * 
         * @since SDNO 0.5
         */
        @Override
        public void doTask() {
            // deleted
        }

        /**
         * Returns the task name for the current task.<br/>
         * 
         * @return Unregister web APP task
         * @since SDNO 0.5
         */
        @Override
        public String getTaskName() {
            return "UnRegisterWebAppTask";
        }
    }
}
