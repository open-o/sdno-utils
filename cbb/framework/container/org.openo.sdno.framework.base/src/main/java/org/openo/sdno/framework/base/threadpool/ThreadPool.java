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

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The thread pool to achieve the functions of concurrent tasks.<br/>
 * 
 * @author
 * @version SDNO 0.5 24-March-2016
 */
public final class ThreadPool {

    /**
     * Core number of threads (memory resident).
     */
    private static final int CORE_THREAD_COUNT = 8;

    /**
     * The maximum number of threads.
     */
    private static final int MAX_THREAD_COUNT = 128;

    /**
     * Non-core thread idle survival time (5 minutes).
     */
    private static final long IDLE_ALIVE_TIME = 300;

    /**
     * The only instance.
     */
    private static final ThreadPool INSTANCE = new ThreadPool();

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPool.class);

    private ThreadPoolExecutor executor;

    /**
     * Private constructor to avoid the direct instantiation.
     */
    private ThreadPool() {
        /**
         * When the task is beyond the maximum size of the pool, directly performs tasks in the
         * current thread.
         */
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                LOGGER.info(String.format("rejected Thread %s : ", r));

                // If the thread pool resources are exhausted, the event is dispatched directly in
                // the current thread
                try {
                    r.run();
                } catch(Exception e) {
                    LOGGER.error("Run Runnable exception, Runnable:" + r, e);
                }
            }
        };

        // Using synchronous queue, ensure that the task can be executed quickly
        executor = new ThreadPoolExecutor(CORE_THREAD_COUNT, MAX_THREAD_COUNT, IDLE_ALIVE_TIME, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), handler);

        // Add a shut down hook to close all the pooled thread.
        Runtime.getRuntime().addShutdownHook(new Thread("Thread pool shut down hook") {

            @Override
            public void run() {
                executor.shutdown();
            }
        });
    }

    /**
     * @return ThreadPool instance of the ThreadPool.
     */
    public static ThreadPool getInstance() {
        return INSTANCE;
    }

    /**
     * Add an event to be executed in the queue.
     * 
     * @param task Runnable
     */
    public void execute(Task task) {
        try {
            // If executor have already shutdown, the event is directly run in the current thread
            // itself, which generally occurs when the client exits. However In most of the cases
            // (in the process running in the foreground), the thread pool schedules all events.
            if(executor.isShutdown()) {
                task.run();
            } else {
                executor.execute(task);
            }
        } catch(Exception e) {
            LOGGER.error("Run task exception ,task is:" + task, e);
        }
    }
}
