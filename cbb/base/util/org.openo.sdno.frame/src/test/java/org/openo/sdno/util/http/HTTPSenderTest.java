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
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.util.http.HTTPRequestMessage;
import org.openo.sdno.util.http.HTTPReturnMessage;
import org.openo.sdno.util.http.HTTPSender;

public class HTTPSenderTest {

    @Test
    public void testRestInvokeSNC() {
        try {
            HTTPRequestMessage authReq = new HTTPRequestMessage();
            authReq.setUrl("https://test");
            authReq.setAction("PUT");
            authReq.setBody("{\"user\": {\"key\": \"Huawei@123\", \"name\": \"netmatrix\"}}");

            HTTPRequestMessage request = new HTTPRequestMessage();
            request.setAction("POST");
            request.setUrl("https://test2");
            request.setBody("{    \"interface\":{        \"ifName\":\"LoopBack19\"    }}");

            HTTPReturnMessage msg = new HTTPSender().restInvoke(authReq, request);
            assertEquals("\"HttpSender::restInvokeSNC error! \"", msg.getBody());
        } catch(Exception e) {
            fail("NetMatrixJsonUtil parser failed." + e.getMessage());
        }
    }

    @Test
    public void testRestInvokeSNC2() {
        new MockUp<HTTPSender>() {

            @Mock
            protected HttpURLConnection sendMsg(HTTPRequestMessage requst, Map<String, String> authInfo,
                    HTTPReturnMessage response, boolean isAuth) throws IOException, NoSuchProviderException,
                    NoSuchAlgorithmException, ServiceException {
                URL url = new URL("https://test");
                response.setStatus(200);
                return new TestHttpURLConnection(url);
            }
        };

        try {
            HTTPRequestMessage authReq = new HTTPRequestMessage();
            authReq.setUrl("https://test");
            authReq.setAction("PUT");
            authReq.setBody("{\"user\": {\"key\": \"Huawei@123\", \"name\": \"netmatrix\"}}");

            HTTPRequestMessage request = new HTTPRequestMessage();
            request.setAction("POST");
            request.setUrl("https://test2");
            request.setBody("{    \"interface\":{        \"ifName\":\"LoopBack19\"    }}");

            HTTPReturnMessage msg = new HTTPSender().restInvoke(authReq, request);
            assertEquals("Test string in TestHttpURLConnection.getInputStream().", msg.getBody());
        } catch(Exception e) {
            fail("NetMatrixJsonUtil parser failed." + e.getMessage());
        }
    }

    @Test
    public void testRestInvokeSNC3() {
        new MockUp<HTTPSender>() {

            @Mock
            protected HttpURLConnection sendMsg(HTTPRequestMessage requst, Map<String, String> authInfo,
                    HTTPReturnMessage response, boolean isAuth) throws IOException, NoSuchProviderException,
                    NoSuchAlgorithmException, ServiceException {
                URL url = new URL("https://test");
                response.setStatus(400);
                return new TestHttpURLConnection(url);
            }
        };

        try {
            HTTPRequestMessage authReq = new HTTPRequestMessage();
            authReq.setUrl("https://test");
            authReq.setAction("PUT");
            authReq.setBody("{\"user\": {\"key\": \"Huawei@123\", \"name\": \"netmatrix\"}}");

            HTTPRequestMessage request = new HTTPRequestMessage();
            request.setAction("POST");
            request.setUrl("https://test2");
            request.setBody("{    \"interface\":{        \"ifName\":\"LoopBack19\"    }}");

            HTTPReturnMessage msg = new HTTPSender().restInvoke(authReq, request);
            assertEquals("\"HttpSender::restInvokeSNC error! \"", msg.getBody());
        } catch(Exception e) {
            fail("NetMatrixJsonUtil parser failed." + e.getMessage());
        }
    }

    @Test
    public void testRestInvoke() {
        try {
            String urlStr = "https://test";
            String actionInfo = "PUT";
            String body = "{\"user\": {\"password\": \"Huawei@123\", \"name\": \"netmatrix\"}}";
            Map<String, String> authInfo = new HashMap<String, String>();

            HTTPReturnMessage msg = new HTTPSender().restInvoke(urlStr, body, actionInfo, authInfo);
            assertEquals("\"HttpSender::restInvoke error! \"", msg.getBody());
        } catch(Exception e) {
            fail("NetMatrixJsonUtil parser failed." + e.getMessage());
        }
    }

    @Test
    public void testRestInvoke2() {

        new MockUp<HTTPSender>() {

            @Mock
            protected HttpURLConnection sendMsg(HTTPRequestMessage requst, Map<String, String> authInfo,
                    HTTPReturnMessage response, boolean isAuth) throws IOException, NoSuchProviderException,
                    NoSuchAlgorithmException, ServiceException {
                URL url = new URL("https://test");
                response.setStatus(200);
                return new TestHttpURLConnection(url);
            }
        };

        try {
            String urlStr = "https://test";
            String actionInfo = "PUT";
            String body = "{\"user\": {\"password\": \"Huawei@123\", \"name\": \"netmatrix\"}}";
            Map<String, String> authInfo = new HashMap<String, String>();

            HTTPReturnMessage msg = new HTTPSender().restInvoke(urlStr, body, actionInfo, authInfo);
            assertEquals("Test string in TestHttpURLConnection.getInputStream().", msg.getBody());
        } catch(Exception e) {
            fail("NetMatrixJsonUtil parser failed." + e.getMessage());
        }
    }

    @Test
    public void testRestInvoke3() {

        new MockUp<HTTPSender>() {

            @Mock
            protected HttpURLConnection sendMsg(HTTPRequestMessage requst, Map<String, String> authInfo,
                    HTTPReturnMessage response, boolean isAuth) throws IOException, NoSuchProviderException,
                    NoSuchAlgorithmException, ServiceException {
                URL url = new URL("https://test");
                response.setStatus(500);
                return new TestHttpURLConnection(url);
            }
        };

        try {
            String urlStr = "https://test";
            String actionInfo = "PUT";
            String body = "{\"user\": {\"password\": \"Huawei@123\", \"name\": \"netmatrix\"}}";
            Map<String, String> authInfo = new HashMap<String, String>();

            HTTPReturnMessage msg = new HTTPSender().restInvoke(urlStr, body, actionInfo, authInfo);
            assertEquals("Test string in TestHttpURLConnection.getErrorStream().", msg.getBody());
        } catch(Exception e) {
            fail("NetMatrixJsonUtil parser failed." + e.getMessage());
        }
    }

    @Test
    public void testGetV3Token() {

        try {
            String urlStr = "https://test";
            String actionInfo = "PUT";
            String body = "{\"user\": {\"password\": \"Huawei@123\", \"name\": \"netmatrix\"}}";

            String token = new HTTPSender().getV3Token(urlStr, body, actionInfo);
            assertEquals("", token);
        } catch(Exception e) {
            fail("NetMatrixJsonUtil parser failed." + e.getMessage());
        }
    }

    @Test
    public void testGetV3Token2() {
        new MockUp<HTTPSender>() {

            @Mock
            protected HttpURLConnection sendMsg(HTTPRequestMessage requst, Map<String, String> authInfo,
                    HTTPReturnMessage response, boolean isAuth) throws IOException, NoSuchProviderException,
                    NoSuchAlgorithmException, ServiceException {
                URL url = new URL("https://test");
                response.setStatus(200);
                return new TestHttpURLConnection(url);
            }
        };

        try {
            String urlStr = "https://test";
            String actionInfo = "PUT";
            String body = "{\"user\": {\"password\": \"Huawei@123\", \"name\": \"netmatrix\"}}";

            String token = new HTTPSender().getV3Token(urlStr, body, actionInfo);
            assertEquals("testGetHeaderField", token);
        } catch(Exception e) {
            fail("NetMatrixJsonUtil parser failed." + e.getMessage());
        }
    }
}
