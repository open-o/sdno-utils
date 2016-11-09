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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.UserInfo;

/**
 * Default SSH User Info.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-08-18
 */
public class SshUserInfo implements UserInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(SshUserInfo.class);

    private ProtocolParameter protocolParameter;

    /**
     * Constructor.<br>
     * 
     * @param protocolParameter Protocol parameter
     * @since SDNO 0.5
     */
    public SshUserInfo(ProtocolParameter protocolParameter) {
        this.protocolParameter = protocolParameter;
    }

    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.protocolParameter.getPassword();
    }

    @Override
    public boolean promptPassword(String message) {
        return false;
    }

    @Override
    public boolean promptPassphrase(String message) {
        return false;
    }

    @Override
    public boolean promptYesNo(String message) {
        return false;
    }

    @Override
    public void showMessage(String message) {
        LOGGER.info("SSH Message:" + message);
    }
}
