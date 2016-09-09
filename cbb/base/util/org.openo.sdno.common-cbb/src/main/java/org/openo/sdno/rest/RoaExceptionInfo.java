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

package org.openo.sdno.rest;

import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;

/**
 * ROA exception information.<br>
 * 
 * @author
 * @version SDNO 0.5 23-Jul-2016
 */
public class RoaExceptionInfo {

    private static final String ROA_EXCEPTION = "ROA_EXFRAME_EXCEPTION";

    String exceptionId;

    String exceptionType;

    private String[] descArgs = null;

    private String[] reasonArgs = null;

    private String[] detailArgs = null;

    private String[] adviceArgs = null;

    public String getExceptionId() {
        return this.exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }

    public String getExceptionType() {
        return this.exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String[] getDescArgs() {
        return descArgs;
    }

    public void setDescArgs(String[] descArgs) {
        this.descArgs = descArgs;
    }

    public String[] getReasonArgs() {
        return reasonArgs;
    }

    public void setReasonArgs(String[] reasonArgs) {
        this.reasonArgs = reasonArgs;
    }

    public String[] getDetailArgs() {
        return detailArgs;
    }

    public void setDetailArgs(String[] detailArgs) {
        this.detailArgs = detailArgs;
    }

    public String[] getAdviceArgs() {
        return adviceArgs;
    }

    public void setAdviceArgs(String[] adviceArgs) {
        this.adviceArgs = adviceArgs;
    }

    /**
     * Format the detail exception information.<br>
     * 
     * @param e The ServiceException object
     * @return The RoaExceptionInfo object contains all the detail exception information
     * @since SDNO 0.5
     */
    public static Object formatMessage(ServiceException e) {
        RoaExceptionInfo roaExceptionInfo = new RoaExceptionInfo();
        roaExceptionInfo.setExceptionId(e.getId());
        roaExceptionInfo.setExceptionType(RoaExceptionInfo.ROA_EXCEPTION);

        ExceptionArgs exceptionArgs = e.getExceptionArgs();
        if(exceptionArgs != null) {
            roaExceptionInfo.setDescArgs(exceptionArgs.getDescArgs());
            roaExceptionInfo.setDetailArgs(exceptionArgs.getDetailArgs());
            roaExceptionInfo.setReasonArgs(exceptionArgs.getReasonArgs());
            roaExceptionInfo.setAdviceArgs(exceptionArgs.getAdviceArgs());
        }
        return roaExceptionInfo;
    }

    /**
     * Format the detail exception information.<br>
     * 
     * @param exceptionId The exception ID
     * @return The RoaExceptionInfo object contains all the detail exception information
     * @since SDNO 0.5
     */
    public static Object formatMessage(String exceptionId) {
        RoaExceptionInfo roaExceptionInfo = new RoaExceptionInfo();
        roaExceptionInfo.setExceptionId(exceptionId);
        roaExceptionInfo.setExceptionType(RoaExceptionInfo.ROA_EXCEPTION);
        return roaExceptionInfo;
    }
}
