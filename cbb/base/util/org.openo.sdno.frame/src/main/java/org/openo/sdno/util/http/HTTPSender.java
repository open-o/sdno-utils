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

package org.openo.sdno.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.ssl.SSLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP sender class. <br/>
 * <p>
 * Only supports HTTPS connection, through the string in URL to determine the HTTPS connection,
 * you<br/>
 * can specify the host address and the authentication of the certificate.If you do not pass in
 * the<br/>
 * relevant parameters (TrustManager, hostNameVerifier), the default method which does not do
 * the<br/>
 * inspection will be used.<br/>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 2016-4-13
 */
public class HTTPSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPSender.class);

    private String httpContentType = "application/json;charset=UTF-8";

    private String httpAccept = "application/json";

    private int connectTimeout = 30000;

    private int readTimeout = 30000;

    private SSLContext sslContext = null;

    protected static final String ACCESS_TOKEN = "X-ACCESS-TOKEN";

    static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    public HTTPSender() {
        sslContext = SSLUtil.getDefaultSSLContext();
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param sslContext SSL context
     */
    public HTTPSender(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    /**
     * Set defaultHostnameVerifier of HttpsURLConnection.<br/>
     * 
     * @param hostNameVerifier Host name verifier
     * @since SDNO 0.5
     */
    public void setHostnameVerifier(HostnameVerifier hostNameVerifier) {
        HttpsURLConnection.setDefaultHostnameVerifier(hostNameVerifier);
    }

    /**
     * @param httpContentType The httpContentType to set.
     */
    public void setHttpContentType(String httpContentType) {
        this.httpContentType = httpContentType;
    }

    /**
     * @param httpAccept The httpAccept to set.
     */
    public void setHttpAccept(String httpAccept) {
        this.httpAccept = httpAccept;
    }

    /**
     * @param connectTimeout The connectTimeout to set.
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * @param readTimeout The readTimeout to set.
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * Send message after authentication.<br/>
     * 1. Send authentication messages to Server. <br/>
     * 2. After the success of the certification, send message.<br/>
     * 3. After receiving the response message, close the HTTP connection.<br/>
     * 
     * @param authReq Authentication request
     * @param request Request to be sent after authentication
     * @return HTTP response message.
     * @since SDNO 0.5
     */
    public HTTPReturnMessage restInvoke(HTTPRequestMessage authReq, HTTPRequestMessage request) {
        HTTPReturnMessage authResponse = new HTTPReturnMessage();
        HTTPReturnMessage response = new HTTPReturnMessage();
        HttpURLConnection authConn = null;
        HttpURLConnection conn = null;
        BufferedReader br = null;

        try {
            // Analysis token_id, add the response
            authConn = sendMsg(authReq, null, authResponse, true);
            if(authResponse.getStatus() == HttpStatus.SC_OK) {
                Map<String, String> tokenMap = new HashMap<String, String>();
                tokenMap.put(ACCESS_TOKEN, authResponse.getToken());

                conn = sendMsg(request, tokenMap, response, false);
                LOGGER.info("HttpSender::restInvokeSNC status:" + response.getStatus());
                // success
                if((response.getStatus() < HttpStatus.SC_BAD_REQUEST) && (response.getStatus() >= HttpStatus.SC_OK)) {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    // fail
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    LOGGER.warn("HttpSender::restInvokeSNC send failed!");
                }
                processReturnMsg(response, br);
            } else {
                LOGGER.error("HttpSender::restInvokeSNC auth failed!");
                throw new ServiceException("restInvokeSNC auth failed! error code is :" + response.getStatus());
            }
        } catch(IOException | NoSuchProviderException | NoSuchAlgorithmException | ServiceException e) {
            // Error info from SNC device might be null，'new
            // InputStreamReader(conn.getErrorStream())'
            // will generate null point exception.
            if(response.getStatus() == 0) {
                response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            }
            response.setBody("\"HttpSender::restInvokeSNC error! \"");
            LOGGER.warn("HttpSender::restInvokeSNC error! ", e);
        } finally {
            close(authConn);
            close(conn);
            IOUtils.closeQuietly(br);
        }
        return response;
    }

    /**
     * Normal HTTP send.<br/>
     * 
     * @param urlStr URL
     * @param body message body
     * @param actionInfo Action info
     * @param authInfo Authentication info
     * @return HTTP response message.
     * @since SDNO 0.5
     */
    public HTTPReturnMessage restInvoke(String urlStr, String body, String actionInfo, Map<String, String> authInfo) {
        HTTPReturnMessage msg = new HTTPReturnMessage();
        HttpURLConnection conn = null;
        BufferedReader br = null;

        try {
            conn = sendMsg(new HTTPRequestMessage(urlStr, body, actionInfo), authInfo, msg, false);
            LOGGER.info("status:" + msg.getStatus());
            // success
            if((msg.getStatus() < HttpStatus.SC_BAD_REQUEST) && (msg.getStatus() >= HttpStatus.SC_OK)) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String xToken = conn.getHeaderField("X-subject-Token");
                msg.setToken(xToken);
            } else {
                // fail
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            processReturnMsg(msg, br);
        } catch(IOException | NoSuchProviderException | NoSuchAlgorithmException | ServiceException e) {
            // Error info from SNC device might be null，'new
            // InputStreamReader(conn.getErrorStream())'
            // will generate null point exception.
            msg.setBody("\"HttpSender::restInvoke error! \"");
            LOGGER.warn("HttpSender::restInvoke error! ", e);
        } finally {
            close(conn);
            IOUtils.closeQuietly(br);
        }

        return msg;
    }

    protected void processReturnMsg(HTTPReturnMessage msg, BufferedReader br) {
        LineIterator lineIter = new LineIterator(br);
        StringBuilder outputBuilder = new StringBuilder();
        while(lineIter.hasNext()) {
            outputBuilder.append(lineIter.next());
        }
        msg.setBody(Normalizer.normalize(outputBuilder.toString(), Normalizer.Form.NFKC));
    }

    /**
     * Send input message to input URL, and get X-subject-Token from response message's header.<br/>
     * 
     * @param urlStr URL
     * @param body Message body
     * @param actionInfo Action info
     * @return Token.
     * @since SDNO 0.5
     */
    public String getV3Token(String urlStr, String body, String actionInfo) {
        String token = "";
        HttpURLConnection conn = null;

        try {
            HTTPReturnMessage msg = new HTTPReturnMessage();
            conn = sendMsg(new HTTPRequestMessage(urlStr, body, actionInfo), null, msg, false);

            if((msg.getStatus() < HttpStatus.SC_BAD_REQUEST) && (msg.getStatus() >= HttpStatus.SC_OK)) {
                token = conn.getHeaderField("X-subject-Token");
            }
        } catch(IOException | NoSuchProviderException | NoSuchAlgorithmException | ServiceException e) {
            LOGGER.warn("OpenstackProxy::restInvoke error! ", e);
        } finally {
            close(conn);
        }

        return token;
    }

    protected void close(HttpURLConnection conn) {
        if(conn != null) {
            conn.disconnect();
        }
    }

    private HttpURLConnection openConnect(String actionStr, String urlStr)
            throws IOException, NoSuchAlgorithmException, NoSuchProviderException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setRequestMethod(actionStr);
        conn.setRequestProperty("Content-Type", httpContentType);
        if((conn instanceof HttpsURLConnection) && (sslContext != null)) {
            ((HttpsURLConnection)conn).setSSLSocketFactory(sslContext.getSocketFactory());
        }

        // Make judgments for security login.
        // Because now SNC login is JSON, and if data is XML, does not set httpAccept
        if(!httpAccept.isEmpty()) {
            conn.setRequestProperty("Accept", httpAccept);
        }
        return conn;
    }

    protected HttpURLConnection sendMsg(HTTPRequestMessage requst, Map<String, String> authInfo,
            HTTPReturnMessage response, boolean isAuth)
            throws IOException, NoSuchProviderException, NoSuchAlgorithmException, ServiceException {
        String url = requst.getUrl();

        HttpURLConnection conn = getHttpURLConnection(url, authInfo, requst.getAction());
        sendHttpMsg(requst.getBody(), requst.getAction(), conn);

        // 200,201,etc.
        response.setStatus(conn.getResponseCode());
        // If success, analysis body and get token_id.
        if(isAuth && (response.getStatus() < HttpStatus.SC_BAD_REQUEST) && (response.getStatus() >= HttpStatus.SC_OK)) {
            getTokenFromConn(response, conn);
        }

        return conn;
    }

    /**
     * Get token_id, SNC return sample： { "errcode": "sso.login.e0535", "errmsg": "", "data": {
     * "expiredDate":"2015-12-20 04:19:57", "token_id":"" } }
     */
    protected void getTokenFromConn(HTTPReturnMessage response, HttpURLConnection conn) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            processReturnMsg(response, br);
            if(response.getBody().isEmpty()) {
                LOGGER.error("HTTPSender::connecttion getInputStream is empty!");
                return;
            }
            Map<String, Object> contentMap = JsonUtil.fromJson(response.getBody(), Map.class);
            String data = JsonUtil.toJson(contentMap.get("data"));
            Map<String, String> dataMap = JsonUtil.fromJson(data, Map.class);
            response.setToken(dataMap.get("token_id"));
        } catch(IOException e) {
            LOGGER.warn("HTTPSender::get token from connecttion error! ", e);
        }
    }

    protected HttpURLConnection getHttpURLConnection(String urlStr, Map<String, String> authInfo, String action)
            throws IOException, NoSuchProviderException, NoSuchAlgorithmException {
        LOGGER.info("http connect url: " + urlStr);
        HttpURLConnection conn = openConnect(action, urlStr);

        if((authInfo != null) && !authInfo.isEmpty()) {
            for(Entry<String, String> entry : authInfo.entrySet()) {
                conn.setRequestProperty(entry.getKey(), authInfo.get(entry.getKey()));
            }
        }
        return conn;
    }

    protected void sendHttpMsg(String body, String action, HttpURLConnection conn) throws ServiceException {
        boolean isSend = checkRequestType(body, action);
        if(isSend) {
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes());
                os.flush();
            } catch(IOException e) {
                LOGGER.error("HttpSender::sendHttpMsg error! ", e);
                throw new ServiceException("HttpSender::sendHttpMsg error! ", e);
            }
        }
    }

    private boolean checkRequestType(String body, String actionTemp) {
        return (body != null) && !body.isEmpty() && !"get".equalsIgnoreCase(actionTemp);
    }
}
