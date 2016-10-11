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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * IRoaModule test class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-3-30
 */
public class IRoaModuleTest extends IRoaModule {

    @Test
    public void testStart() {
        start();
        assertTrue(true);
    }

    @Test
    public void testStop() {
        stop();
        assertTrue(true);
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void destroy() {
        // TODO Auto-generated method stub

    }

}
