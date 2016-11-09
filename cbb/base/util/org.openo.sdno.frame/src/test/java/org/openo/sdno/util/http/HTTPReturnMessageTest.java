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

package org.openo.sdno.util.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.util.http.HTTPReturnMessage;

/**
 * HTTPReturnMessage test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-19
 */
public class HTTPReturnMessageTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testHashCode() {
        HTTPReturnMessage message = new HTTPReturnMessage();

        assertEquals(29791, message.hashCode());
    }

    @Test
    public void testEquals1() {
        HTTPReturnMessage message = new HTTPReturnMessage();

        assertTrue(message.equals(message));
    }

    @Test
    public void testEquals2() {
        HTTPReturnMessage message = new HTTPReturnMessage();

        assertFalse(message.equals(null));
    }

    @Test
    public void testEquals3() {
        HTTPReturnMessage message = new HTTPReturnMessage();
        int test = 0;
        assertFalse(message.equals(test));
    }

    @Test
    public void testEquals4() {
        HTTPReturnMessage message = new HTTPReturnMessage();
        HTTPReturnMessage message2 = new HTTPReturnMessage();
        assertTrue(message.equals(message2));
    }

    @Test
    public void testEquals5() {
        HTTPReturnMessage message = new HTTPReturnMessage();
        HTTPReturnMessage message2 = new HTTPReturnMessage();
        message2.setBody(null);
        assertFalse(message.equals(message2));
    }

    @Test
    public void testEquals6() {
        HTTPReturnMessage message = new HTTPReturnMessage();
        HTTPReturnMessage message2 = new HTTPReturnMessage();
        message.setBody(null);
        assertFalse(message.equals(message2));
    }

    @Test
    public void testEquals7() {
        HTTPReturnMessage message = new HTTPReturnMessage();
        HTTPReturnMessage message2 = new HTTPReturnMessage();
        message2.setStatus(1);
        assertFalse(message.equals(message2));
    }

    @Test
    public void testEquals8() {
        HTTPReturnMessage message = new HTTPReturnMessage();
        HTTPReturnMessage message2 = new HTTPReturnMessage();
        message.setToken(null);
        assertFalse(message.equals(message2));
    }

    @Test
    public void testEquals9() {
        HTTPReturnMessage message = new HTTPReturnMessage();
        HTTPReturnMessage message2 = new HTTPReturnMessage();
        message2.setToken(null);
        assertFalse(message.equals(message2));
    }
}
