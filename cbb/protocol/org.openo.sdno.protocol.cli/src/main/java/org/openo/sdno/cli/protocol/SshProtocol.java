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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.cli.inf.AbstractCliProtocol;
import org.openo.sdno.cli.util.ShellFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import expect4j.Closure;
import expect4j.Expect4j;
import expect4j.ExpectState;
import expect4j.matches.EofMatch;
import expect4j.matches.Match;
import expect4j.matches.RegExpMatch;
import expect4j.matches.TimeoutMatch;

/**
 * Class of SSh CLI Protocol.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-08-18
 */
public class SshProtocol extends AbstractCliProtocol {

    private static final Logger LOGGER = LoggerFactory.getLogger(SshProtocol.class);

    private Session session;

    private ChannelShell channel;

    private Expect4j expect;

    private StringBuffer stringBuffer = new StringBuffer();

    public static final String[] linuxPromptRegEx = new String[] {"~]#", "~#", "#", ":~#", "/$", ">"};

    public static final String[] errorMsg = new String[] {"could not acquire the config lock "};

    private static Hashtable<String, String> sessionConfig;

    static {
        sessionConfig = new Hashtable<String, String>();
        sessionConfig.put("StrictHostKeyChecking", "no");
    }

    /**
     * Constructor.<br>
     * 
     * @param protocolParameter
     * @since SDNO 0.5
     */
    public SshProtocol(ProtocolParameter protocolParameter) {
        super(protocolParameter);
    }

    @Override
    protected void doConnect() throws ServiceException {

        String userName = this.protocolParameter.getUserName();
        String ipAddress = this.protocolParameter.getIpAddress();
        int port = this.protocolParameter.getPort();
        String passWord = this.protocolParameter.getPassword();

        JSch jsch = new JSch();
        try {

            // Get Jsch Session Object
            session = jsch.getSession(userName, ipAddress, port);

            // Set Session Password
            session.setPassword(passWord);

            // Set Session Configuration
            session.setConfig(sessionConfig);

            // Set User Info
            session.setUserInfo(new SshUserInfo(this.protocolParameter));

            // Connection to server
            session.connect(protocolTimeOut);

            // Open Channel
            channel = (ChannelShell)session.openChannel("shell");

            // Create expect Object
            expect = new Expect4j(channel.getInputStream(), channel.getOutputStream());

            // Channel connect
            channel.connect(protocolTimeOut);
        } catch(JSchException | IOException e) {
            LOGGER.error("Jsch Create Session Failed!!", e);
            throw new ServiceException("Jsch Create Session Failed!!", e);
        }
    }

    @Override
    protected void doDisconnect() throws ServiceException {

        if(channel != null) {
            channel.disconnect();
        }

        if(session != null) {
            session.disconnect();
        }
    }

    @Override
    protected String doExecuteCommand(List<String> commands) throws ServiceException {

        Closure closure = new Closure() {

            @Override
            public void run(ExpectState expectState) throws Exception {
                stringBuffer.append(expectState.getBuffer());
                expectState.exp_continue();
            }
        };

        String commandResult = null;

        List<Match> lstPattern = new ArrayList<Match>();
        String[] regEx = linuxPromptRegEx;
        if(regEx != null && regEx.length > 0) {
            synchronized(regEx) {
                for(String regexElement : regEx) {
                    try {
                        RegExpMatch mat = new RegExpMatch(regexElement, closure);
                        lstPattern.add(mat);
                    } catch(Exception e) {
                        LOGGER.error("New RegExpMatch Error!", e);
                    }
                }
                lstPattern.add(new EofMatch(new Closure() {

                    @Override
                    public void run(ExpectState state) {
                        // TODO
                    }
                }));
                lstPattern.add(new TimeoutMatch(1000, new Closure() {

                    @Override
                    public void run(ExpectState state) {
                        // TODO
                    }
                }));
            }
        }

        try {
            boolean isSuccess = true;

            for(String command : commands) {
                isSuccess = isSuccess(lstPattern, command);
                if(!isSuccess) {
                    isSuccess = isSuccess(lstPattern, command);
                }
            }

            checkResult(expect.expect(lstPattern));

            commandResult = stringBuffer.toString();

            LOGGER.info("Ssh Result:" + commandResult);
        } catch(Exception ex) {
            LOGGER.error("Command Execute Error!", ex);
            throw new ServiceException("Command Execute Error!!");
        }

        return commandResult;
    }

    @Override
    public String executeShellScript(String scriptFile, Map<String, String> replaceParamMap) throws ServiceException {
        List<String> cmdList = ShellFileReader.readShellCommandLines(scriptFile, replaceParamMap);
        return executeCommand(cmdList);
    }

    private boolean isSuccess(List<Match> objPattern, String strCommandPattern) {
        try {
            boolean isFailed = checkResult(expect.expect(objPattern));
            if(!isFailed) {
                expect.send(strCommandPattern);
                expect.send("\r");
                return true;
            }
            return false;
        } catch(Exception ex) {
            LOGGER.error("expect or send Error!", ex);
            return false;
        }
    }

    private boolean checkResult(int intRetVal) {
        if(intRetVal == -2) {
            return true;
        }
        return false;
    }
}
