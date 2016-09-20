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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;

import mockit.Mock;
import mockit.MockUp;

public class SshProtocolTest {

    @Test
    public void testExcuteCommand() throws ServiceException {

        new MockUp<SshProtocol>() {

            @Mock
            public void executeCommand(List<String> commands) {
                return;
            }
        };

        ProtocolParameter parameter = new ProtocolParameter();
        parameter.setIpAddress("10.229.41.100");
        parameter.setPort(22);
        parameter.setUserName("root");
        parameter.setPassword("Test_12345");

        SshProtocol protocol = new SshProtocol(parameter);
        List<String> commands = new ArrayList<String>();
        commands.add("ls");
        commands.add("ls -a");
        commands.add("time");
        protocol.executeCommand(commands);
    }

    @Test
    public void testSSHShellSuccess() throws ServiceException {

        new MockUp<SshProtocol>() {

            @Mock
            public void executeCommand(List<String> commands) {
                return;
            }
        };

        ProtocolParameter parameter = new ProtocolParameter();
        parameter.setIpAddress("10.229.41.100");
        parameter.setPort(22);
        parameter.setUserName("root");
        parameter.setPassword("Test_12345");

        SshProtocol protocol = new SshProtocol(parameter);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("info", "test3");
        protocol.executeShellScript("src/test/resources/CommandLine1.shell", paramMap);
    }

}
