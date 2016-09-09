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

package org.openo.sdno.util.http.soap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

/**
 * SOAPMessageUtil test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-18
 */
public class SoapMessageUtilTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSendMessage1(@Mocked SOAPConnectionFactory any) throws SOAPException {

        new Expectations() {

            {
                SOAPConnectionFactory.newInstance();
                result = new TestSoapConnectionFactory();
            }

        };

        new MockUp<TestSoapConnectionFactory>() {

            @Mock
            public SOAPConnection createConnection() throws SOAPException {
                return new TestSoapConnectionClass();
            }
        };

        SOAPMessageUtil soapMessageUtil = SOAPMessageUtil.getInstance();
        byte[] bytes = {1, 1, 1};
        final SOAPMessage rspMsg = soapMessageUtil.createMessage(bytes);
        new MockUp<TestSoapConnectionClass>() {

            @Mock
            public SOAPMessage call(SOAPMessage request, Object to) throws SOAPException {

                return rspMsg;
            }
        };

        String msgUrl = "http://testUrl";

        final int timeout = 10;

        SOAPMessage resultMsg = soapMessageUtil.sendMessage(msgUrl, bytes, timeout);
        assertTrue(resultMsg.equals(rspMsg));
    }

    @Test
    public void testSendMessage2(@Mocked SOAPConnectionFactory any) throws SOAPException {

        new Expectations() {

            {
                SOAPConnectionFactory.newInstance();
                result = new TestSoapConnectionFactory();
            }

        };

        new MockUp<TestSoapConnectionFactory>() {

            @Mock
            public SOAPConnection createConnection() throws SOAPException {
                return null;
            }
        };

        byte[] bytes = {1, 1, 1};
        String msgUrl = "http://testUrl";
        final int timeout = 10;

        SOAPMessageUtil soapMessageUtil = SOAPMessageUtil.getInstance();

        assertEquals(null, soapMessageUtil.sendMessage(msgUrl, bytes, timeout));
    }

    @Test
    public void testSendMessage3(@Mocked SOAPConnectionFactory any) throws SOAPException {

        new Expectations() {

            {
                SOAPConnectionFactory.newInstance();
                result = new TestSoapConnectionFactory();
            }

        };

        new MockUp<TestSoapConnectionFactory>() {

            @Mock
            public SOAPConnection createConnection() throws SOAPException {
                throw new SOAPException();
            }
        };

        byte[] bytes = {1, 1, 1};
        String msgUrl = "http://testUrl";
        final int timeout = 10;

        SOAPMessageUtil soapMessageUtil = SOAPMessageUtil.getInstance();

        assertEquals(null, soapMessageUtil.sendMessage(msgUrl, bytes, timeout));
    }

    @Test
    public void testSendMessage4(@Mocked SOAPConnectionFactory any) throws SOAPException {

        new Expectations() {

            {
                SOAPConnectionFactory.newInstance();
                result = new TestSoapConnectionFactory();
            }

        };

        new MockUp<TestSoapConnectionFactory>() {

            @Mock
            public SOAPConnection createConnection() throws SOAPException {
                return new TestSoapConnectionClass();
            }
        };

        SOAPMessageUtil soapMessageUtil = SOAPMessageUtil.getInstance();
        byte[] bytes = {1, 1, 1};
        final SOAPMessage reqMsg = soapMessageUtil.createMessage(bytes);
        byte[] bytes2 = {2, 2, 2};
        final SOAPMessage rspMsg = soapMessageUtil.createMessage(bytes2);
        new MockUp<TestSoapConnectionClass>() {

            @Mock
            public SOAPMessage call(SOAPMessage request, Object to) throws SOAPException {

                return rspMsg;
            }
        };

        String msgUrl = "http://testUrl";

        final int timeout = 10;

        SOAPMessage resultMsg = soapMessageUtil.sendMessage(reqMsg, msgUrl, timeout);
        assertTrue(resultMsg.equals(rspMsg));
    }

    @Test
    public void testSendMessage5(@Mocked SOAPConnectionFactory any) throws SOAPException {

        new Expectations() {

            {
                SOAPConnectionFactory.newInstance();
                result = new TestSoapConnectionFactory();
            }

        };

        new MockUp<TestSoapConnectionFactory>() {

            @Mock
            public SOAPConnection createConnection() throws SOAPException {
                return null;
            }
        };

        byte[] bytes = {1, 1, 1};
        SOAPMessageUtil soapMessageUtil = SOAPMessageUtil.getInstance();
        final SOAPMessage reqMsg = soapMessageUtil.createMessage(bytes);
        String msgUrl = "http://testUrl";
        final int timeout = 10;

        assertEquals(null, soapMessageUtil.sendMessage(reqMsg, msgUrl, timeout));
    }

    @Test
    public void testSendMessage6(@Mocked SOAPConnectionFactory any) throws SOAPException {

        new Expectations() {

            {
                SOAPConnectionFactory.newInstance();
                result = new TestSoapConnectionFactory();
            }

        };

        new MockUp<TestSoapConnectionFactory>() {

            @Mock
            public SOAPConnection createConnection() throws SOAPException {
                throw new SOAPException();
            }
        };

        byte[] bytes = {1, 1, 1};
        SOAPMessageUtil soapMessageUtil = SOAPMessageUtil.getInstance();
        final SOAPMessage reqMsg = soapMessageUtil.createMessage(bytes);
        String msgUrl = "http://testUrl";
        final int timeout = 10;

        assertEquals(null, soapMessageUtil.sendMessage(reqMsg, msgUrl, timeout));
    }

    @Test
    public void testCreateMessage() {
        byte[] bytes = {1, 1, 1};
        SOAPMessageUtil soapMessageUtil = SOAPMessageUtil.getInstance();
        soapMessageUtil.createMessage(bytes);
    }

    @Test
    public void testChangeSoapMsgToStr() {
        byte[] bytes = {1, 1, 1};
        SOAPMessageUtil soapMessageUtil = SOAPMessageUtil.getInstance();
        SOAPMessage msg = soapMessageUtil.createMessage(bytes);
        soapMessageUtil.changeSoapMsgToStr(msg);
    }

}
