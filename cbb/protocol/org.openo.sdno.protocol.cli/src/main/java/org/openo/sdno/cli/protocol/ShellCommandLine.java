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
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.sdno.cli.util.Consts;

/**
 * Class of Shell Command Line.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-08-18
 */
public class ShellCommandLine {

    private String command;

    private List<String> params;

    /**
     * Build Command Line Content.<br>
     * 
     * @return Command Line Content
     * @since SDNO 0.5
     */
    public String buildCommandLineContent() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.command);
        stringBuffer.append(' ');
        if(null != params) {
            for(String param : params) {
                stringBuffer.append(param);
                stringBuffer.append(' ');
            }
        }
        return stringBuffer.toString();
    }

    /**
     * Build Command Line.<br>
     * 
     * @param lineConent Command Line Content
     * @return ShellCommandLine Object
     * @since SDNO 0.5
     */
    public static ShellCommandLine buildCommandLine(String lineConent) {
        if(StringUtils.isEmpty(lineConent)) {
            return null;
        }

        String[] commandParams = lineConent.split(" ");
        if(null == commandParams || 0 == commandParams.length) {
            return null;
        }

        ShellCommandLine cmdLine = new ShellCommandLine();
        cmdLine.setCommand(commandParams[0]);

        if(commandParams.length > 1) {
            List<String> paramList = new ArrayList<String>();
            for(int paramIndex = 1; paramIndex < commandParams.length; paramIndex++) {
                paramList.add(commandParams[paramIndex]);
            }
            cmdLine.setParams(paramList);
        }

        return cmdLine;
    }

    /**
     * Replace Command Parameter.<br>
     * 
     * @param paramName parameter name
     * @param paramValue parameter value
     * @since SDNO 0.5
     */
    public void replaceCommandParam(String paramName, String paramValue) {

        if(CollectionUtils.isEmpty(this.params)) {
            return;
        }

        String keyWordConent = Consts.SHELL_VARIABLE_KEYWORD + "{" + paramName + "}";

        for(int paramIndex = 0; paramIndex < this.params.size(); paramIndex++) {
            String curParam = this.params.get(paramIndex);
            if(curParam.contains(keyWordConent)) {
                this.params.set(paramIndex, curParam.replace(keyWordConent, paramValue));
            }
        }
    }

    public boolean isCommentLine() {
        return this.command.startsWith(Consts.SHELL_COMMENT_LINE);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

}
