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
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;

/**
 * CLI Protocol Interface.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-08-18
 */
public interface CliProtocol {

    /**
     * Execute CLI Commands.<br>
     * 
     * @param commands list of CLI command
     * @return command execution result
     * @throws ServiceException when execute command failed
     * @since SDNO 0.5
     */
    String executeCommand(List<String> commands) throws ServiceException;

    /**
     * Execute Shell Script.<br>
     * 
     * @param scriptFile script file
     * @param replaceParamMap replaced parameter map
     * @return command execution result
     * @throws ServiceException when execute shell script failed
     * @since SDNO 0.5
     */
    String executeShellScript(String scriptFile, Map<String, String> replaceParamMap) throws ServiceException;
}
