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

package org.openo.sdno.framework.base.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task, a package of Runnable thread pool.<br/>
 * 
 * @author
 * @version SDNO 0.5 24-March-2016
 */
public abstract class Task implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);

    /**
     * Entry method for all the tasks.<br/>
     * 
     * @since SDNO 0.5
     */
    @Override
    public void run() {
        String taskName = this.getTaskName();
        String toStr = this.toString();
        LOGGER.info("doTask begin : taskName = " + taskName + " hashCode = " + toStr);
        doTask();
        LOGGER.info("doTask end : taskName =" + taskName + " hashCode = " + toStr);
    }

    /**
     * Actual business logic should be implemented in this, This will be called from the run.<br/>
     * 
     * @since SDNO 0.5
     */
    public abstract void doTask();

    /**
     * Returns the name of the current task.<br>
     * 
     * @return the task name
     * @since SDNO 0.5
     */
    public abstract String getTaskName();
}
