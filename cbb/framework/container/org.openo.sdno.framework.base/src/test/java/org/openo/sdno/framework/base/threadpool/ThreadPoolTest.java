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

package org.openo.sdno.framework.base.threadpool;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolTest.class);

    @Test
    public void testExecute() {
        ThreadPool obj = ThreadPool.getInstance();
        obj.execute(new TaskTemp());
    }

    @Test
    public void testExecuteException() {
        ThreadPool obj = ThreadPool.getInstance();
        obj.execute(null);
    }

    @Test
    public void testExecute1() {
        ThreadPool obj = ThreadPool.getInstance();
        for(int i = 0; i < 130; i++) {
            obj.execute(new TaskTemp());
        }
    }

    @Test
    public void testGetInstance() {
        ThreadPool obj = ThreadPool.getInstance();
        assert (null != obj);
    }

    class TaskTemp extends Task {

        @Override
        public void doTask() {
            try {
                Thread.sleep(3000);
            } catch(InterruptedException e) {
                LOGGER.info("sleep failed.");
            }

        }

        @Override
        public String getTaskName() {
            return null;
        }

    }
}
