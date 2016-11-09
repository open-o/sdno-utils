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

package org.openo.sdno.cli.inf;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.cli.protocol.ProtocolParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Class of CliProtocol.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-08-18
 */
public abstract class AbstractCliProtocol implements CliProtocol {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCliProtocol.class);

    protected ProtocolParameter protocolParameter;

    // Default timeout for connect
    protected int protocolTimeOut = 30 * 1000;

    /**
     * Constructor.<br>
     * 
     * @param protocolParameter Protocol parameter
     * @since SDNO 0.5
     */
    public AbstractCliProtocol(ProtocolParameter protocolParameter) {
        this.protocolParameter = protocolParameter;
    }

    public void setProtocolTimeOut(int protocolTimeOut) {
        this.protocolTimeOut = protocolTimeOut;
    }

    @Override
    public final String executeCommand(List<String> commands) throws ServiceException {

        if(CollectionUtils.isEmpty(commands)) {
            LOGGER.error("Commands parameter is invalid!!");
            throw new ServiceException("Commands parameter is invalid!!");
        }

        String commandResult = null;

        synchronized(this) {
            boolean isExecuteSuccess = true;
            ServiceException serviceException = null;
            try {
                // Connect to Server
                this.connect();
                commandResult = doExecuteCommand(commands);
            } catch(ServiceException e) {
                LOGGER.error("Execute Commands Error!!");
                isExecuteSuccess = false;
                serviceException = e;
            }
            // Disconnect to Server
            this.disconnect();
            if(!isExecuteSuccess) {
                throw serviceException;
            }
        }

        return commandResult;
    }

    private void connect() throws ServiceException {
        validateProtocolParameter();
        doConnect();
    }

    private void disconnect() throws ServiceException {
        validateProtocolParameter();
        doDisconnect();
    }

    private final boolean validateProtocolParameter() throws ServiceException {
        if(null == protocolParameter) {
            LOGGER.error("No Cli Protocol Parameter!!");
            throw new ServiceException("CliProtocol Parameter Error!!");
        }

        if(StringUtils.isEmpty(protocolParameter.getIpAddress())) {
            LOGGER.error("Cli Protocol IpAddress is invalid!!");
            throw new ServiceException("Cli Protocol IpAddress is invalid!!");
        }

        if(0 == protocolParameter.getPort()) {
            LOGGER.error("Cli Protocol port is invalid!!");
            throw new ServiceException("Cli Protocol port is invalid!!");
        }

        if(StringUtils.isEmpty(protocolParameter.getUserName())) {
            LOGGER.error("Cli Protocol usrname is invalid!!");
            throw new ServiceException("Cli Protocol usrname is invalid!!");
        }

        if(StringUtils.isEmpty(protocolParameter.getPassword())) {
            LOGGER.error("Cli Protocol password is invalid!!");
            throw new ServiceException("Cli Protocol password is invalid!!");
        }

        return true;
    }

    /**
     * Connect to Server.<br>
     * 
     * @throws ServiceException when connect to server failed
     * @since SDNO 0.5
     */
    protected abstract void doConnect() throws ServiceException;

    /**
     * Disconnect to Server.<br>
     * 
     * @throws ServiceException when connect to server failed
     * @since SDNO 0.5
     */
    protected abstract void doDisconnect() throws ServiceException;

    /**
     * Execute command.<br>
     * 
     * @param commands commands need to execute
     * @throws ServiceException when execute command failed
     * @since SDNO 0.5
     */
    protected abstract String doExecuteCommand(List<String> commands) throws ServiceException;
}
