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

import java.util.Locale;

/**
 * HTTP request message class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-13
 */
public class HTTPRequestMessage {

    /**
     * Device IP.
     */
    private String url;

    /**
     * HTTP content.
     */
    private String body = "";

    /**
     * HTTP operation type.
     */
    private String action;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public HTTPRequestMessage() {
        // Constructor.
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param url URL
     * @param body Message body
     * @param action Operation type, like GET, POST, PUT, DEL and so on.
     */
    public HTTPRequestMessage(String url, String body, String action) {
        this.url = url;
        this.body = body;
        this.action = action.toUpperCase(Locale.getDefault());
    }

    /**
     * @return Returns the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return Returns the body.
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body The body to set.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return Returns the action.
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action The action to set.
     */
    public void setAction(String action) {
        this.action = action.toUpperCase(Locale.getDefault());
    }

}
