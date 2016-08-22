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

package org.openo.sdno.util.http.soap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SOAP message util class. Provide SOAP message sending function.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-4-12
 */
public class SOAPMessageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SOAPMessageUtil.class);

    private static volatile SOAPMessageUtil uniqueInstance = null;

    private SOAPMessageUtil() {

    }

    /**
     * Get the only instance of the SOAPMessageUtil class.<br/>
     * 
     * @return The only instance of the SOAPMessageUtil class.
     * @since SDNO 0.5
     */
    public static SOAPMessageUtil getInstance() {
        if(null == uniqueInstance) {
            synchronized(SOAPMessageUtil.class) {
                if(null == uniqueInstance) {
                    uniqueInstance = new SOAPMessageUtil();
                }
            }
        }

        return uniqueInstance;
    }

    /**
     * Send SOAP message. Use input bytes create a SOAP message object, and send it to the input
     * URL.<br/>
     * 
     * @param msgUrl URL which identifies where the message should be sent.
     * @param bytes The message to be sent
     * @param timeout Timeout length
     * @return The SOAPMessage object that is the response to the message that was sent
     * @since SDNO 0.5
     */
    public SOAPMessage sendMessage(String msgUrl, byte[] bytes, final int timeout) {
        SOAPConnectionFactory soapConnFactory;
        SOAPConnection connection = null;
        try {
            // Need to set timeout length in case it is too long.
            URL url = new URL(new URL(msgUrl), "", new URLStreamHandler() {

                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    URL target = new URL(url.toString());
                    URLConnection urlconn = target.openConnection();
                    // Connection settings
                    urlconn.setConnectTimeout(timeout);
                    urlconn.setReadTimeout(timeout);
                    return urlconn;
                }
            });
            soapConnFactory = SOAPConnectionFactory.newInstance();
            connection = soapConnFactory.createConnection();
            if(connection == null) {
                LOGGER.error("createConnection fail!url=" + msgUrl);
                return null;
            }
            SOAPMessage sendMsg = createMessage(bytes);
            if(sendMsg != null) {
                LOGGER.info("Call java rpc[url=" + url.toString() + ",message=" + changeSoapMsgToStr(sendMsg) + "]");
                SOAPMessage reply = connection.call(sendMsg, url);
                LOGGER.info("Call java rpc response[" + changeSoapMsgToStr(sendMsg) + "]");
                return reply;
            } else {
                LOGGER.warn("createMessage return null.");
            }
        } catch(UnsupportedOperationException e) {
            LOGGER.warn("sendMessage fail!url=" + msgUrl, e);
        } catch(MalformedURLException e) {
            LOGGER.warn("sendMessage fail!url=" + msgUrl, e);
        } catch(SOAPException e) {
            LOGGER.warn("sendMessage fail!url=" + msgUrl, e);
        } catch(Exception e) {
            LOGGER.warn("sendMessage fail!url=" + msgUrl, e);
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch(SOAPException e) {
                    LOGGER.warn("connection.close failed!", e);
                }
            }
        }
        return null;
    }

    /**
     * Send input SOAP message object to the input URL.<br/>
     * 
     * @param message The message to be sent
     * @param urlPath URL which identifies where the message should be sent.
     * @param timeout Timeout length
     * @return The SOAPMessage object that is the response to the message that was sent
     * @since SDNO 0.5
     */
    public SOAPMessage sendMessage(SOAPMessage message, String urlPath, final int timeout) {
        SOAPConnectionFactory soapConnFactory;
        SOAPConnection connection = null;
        try {

            soapConnFactory = SOAPConnectionFactory.newInstance();
            connection = soapConnFactory.createConnection();
            if(connection == null) {
                LOGGER.error("createConnection fail!");
                return null;
            }
            // Need to set timeout length in case it is too long.
            URL url = new URL(new URL(urlPath), "", new URLStreamHandler() {

                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    URL target = new URL(url.toString());
                    URLConnection connection = target.openConnection();
                    // Connection settings
                    connection.setConnectTimeout(timeout);
                    connection.setReadTimeout(timeout);
                    return connection;
                }
            });
            LOGGER.info("Call java rpc[url=" + url.toString() + ",message=" + changeSoapMsgToStr(message) + "]");
            SOAPMessage reply = connection.call(message, url);
            LOGGER.info("Call java rpc response[" + changeSoapMsgToStr(message) + "]");
            return reply;
        } catch(UnsupportedOperationException e) {
            LOGGER.warn("sendMessage fail!", e);
        } catch(MalformedURLException e) {
            LOGGER.warn("sendMessage fail!", e);
        } catch(SOAPException e) {
            LOGGER.warn("sendMessage fail!", e);
        } catch(Exception e) {
            LOGGER.warn("sendMessage fail!", e);
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch(SOAPException e) {
                    LOGGER.warn("connection.close failed!", e);
                }
            }
        }
        return null;
    }

    /**
     * Construct SOAP message object by an byte array.<br/>
     * 
     * @param bytes Input bytes
     * @return SOAP message object which is created.
     * @since SDNO 0.5
     */
    public SOAPMessage createMessage(byte[] bytes) {
        SOAPMessage message = null;
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            MimeHeaders mimeHeaders = new MimeHeaders();
            MessageFactory messageFactory = MessageFactory.newInstance();
            message = messageFactory.createMessage(mimeHeaders, bais);
        } catch(IOException e) {
            LOGGER.warn("createMessage failed!", e);
        } catch(SOAPException e) {
            LOGGER.warn("createMessage failed!", e);
        } finally {
            if(bais != null) {
                try {
                    bais.close();
                } catch(IOException e) {
                    LOGGER.warn("createMessage failed!", e);
                }
            }
        }
        return message;
    }

    /**
     * Writes input SOAP message object to the output stream, then converts its contents into a
     * string.<br/>
     * 
     * @param message SOAP message object
     * @return String which is converted by input
     * @since SDNO 0.5
     */
    public String changeSoapMsgToStr(SOAPMessage message) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            message.writeTo(os);
            return os.toString();
        } catch(SOAPException e) {
            LOGGER.warn("changeSoapMsgToStr failed!", e);
        } catch(IOException e) {
            LOGGER.warn("changeSoapMsgToStr failed!", e);
        }
        return null;

    }

}
