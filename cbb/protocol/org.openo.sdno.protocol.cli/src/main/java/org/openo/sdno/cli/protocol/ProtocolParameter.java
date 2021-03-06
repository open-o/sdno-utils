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

package org.openo.sdno.cli.protocol;

/**
 * Class of CLI Protocol Parameter.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-08-18
 */
public class ProtocolParameter {

    private String ipAddress;

    private int port;

    private String userName;

    private String password;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public ProtocolParameter() {
        // Empty Construction
    }

    /**
     * Constructor<br>
     * 
     * @param ipAddress Server IpAddress
     * @param port Server Protocol Port
     * @param userName User name
     * @param password User password
     * @since SDNO 0.5
     */
    public ProtocolParameter(String ipAddress, int port, String userName, String password) {
        this.setIpAddress(ipAddress);
        this.setPort(port);
        this.setUserName(userName);
        this.setPassword(password);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
