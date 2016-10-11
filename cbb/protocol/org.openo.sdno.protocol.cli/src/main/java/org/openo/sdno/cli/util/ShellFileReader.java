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

package org.openo.sdno.cli.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.cli.protocol.ShellCommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shell File Reader Class<br>
 * 
 * @author
 * @version SDNO 0.5 2016-08-18
 */
public class ShellFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShellFileReader.class);

    private ShellFileReader() {

    }

    /**
     * Read Shell Command Line from Script File.<br>
     * 
     * @param shellFile Script File
     * @param paramMap Parameter map need to replace
     * @return list of commands
     * @throws ServiceException when read command lines failed
     * @since SDNO 0.5
     */
    public static List<String> readShellCommandLines(String shellFile, Map<String, String> paramMap)
            throws ServiceException {
        if(StringUtils.isEmpty(shellFile)) {
            LOGGER.error("shellFile parameter is wrong");
            throw new ServiceException("shellFile parameter is wrong");
        }

        List<String> cmdContentList = new ArrayList<String>();

        FileReader reader = null;
        BufferedReader br = null;
        try {
            reader = new FileReader(shellFile);
            br = new BufferedReader(reader);

            String lineConent = null;
            while((lineConent = br.readLine()) != null) {

                // ignore empty line
                if(StringUtils.isBlank(lineConent)) {
                    continue;
                }

                // ignore comment line
                ShellCommandLine cmdLine = ShellCommandLine.buildCommandLine(lineConent);
                if(cmdLine.isCommentLine()) {
                    continue;
                }

                // replace parameter
                if(null != paramMap && !paramMap.isEmpty()) {
                    for(Map.Entry<String, String> paramEntry : paramMap.entrySet()) {
                        cmdLine.replaceCommandParam(paramEntry.getKey(), paramEntry.getValue());
                    }
                }

                cmdContentList.add(cmdLine.buildCommandLineContent());
            }

            br.close();
            reader.close();
        } catch(IOException e) {
            LOGGER.error("Readline failed!!", e);
        } finally {
            try {
                if(null != br) {
                    br.close();
                }
                if(null != reader) {
                    reader.close();
                }
            } catch(IOException e) {
                LOGGER.error("Close Reader failed!!", e);
            }
        }

        return cmdContentList;
    }

}
