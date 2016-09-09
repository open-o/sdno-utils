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

import java.util.Map;

import org.openo.sdno.exception.HttpCode;


/**
 * HTTP response message class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-13
 */
public class HTTPReturnMessage {

    private int status = 0;

    private String body = "";

    private String token = "";

    private Map<String, String> respHeaders;

    /**
     * @return Returns the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token to set.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return Returns the status.
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(int status) {
        this.status = status;
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
     * @param respHeaders The respHeaders to set.
     */
    public void setRespHeaders(Map<String, String> respHeaders) {
        this.respHeaders = respHeaders;
    }

    /**
     * @return Returns the respHeaders.
     */
    public Map<String, String> getRespHeaders() {
        return respHeaders;
    }

    /**
     * Return status and body info in string.<br>
     * 
     * @return Info of status and body.
     * @since SDNO 0.5
     */
    @Override
    public String toString() {
        return "HTTPReturnMessage [status=" + status + ", body=" + body + "]";
    }

    /**
     * Check message status is RESPOND_OK or not.<br>
     * 
     * @return Boolean, message status is RESPOND_OK or not.
     * @since SDNO 0.5
     */
    public boolean isSuccess() {
        return status == HttpCode.RESPOND_OK;
    }

    /**
     * Return a hash code based on this object's attributes.<br>
     * 
     * @return Hash code.
     * @since SDNO 0.5
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + status;
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        return result;
    }

    /**
     * To judge whether this object is equal to the input object.<br>
     * 
     * @param obj An object to compare
     * @return Boolean, equal or not.
     * @since SDNO 0.5
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        HTTPReturnMessage other = (HTTPReturnMessage)obj;
        if(body == null) {
            if(other.body != null) {
                return false;
            }
        } else if(!body.equals(other.body)) {
            return false;
        }
        if(status != other.status) {
            return false;
        }
        if(token == null) {
            if(other.token != null) {
                return false;
            }
        } else if(!token.equals(other.token)) {
            return false;
        }
        return true;
    }

}
