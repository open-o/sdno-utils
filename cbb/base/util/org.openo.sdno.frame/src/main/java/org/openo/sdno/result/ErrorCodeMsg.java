/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.result;

import java.util.ArrayList;
import java.util.List;

import org.openo.sdno.exception.ErrorCode;


/**
 * ErrorCode message with list of error code information and a global error info.<br/>
 * 
 * @author
 * @version SDNO 0.5 13-Apr-2016
 */
public class ErrorCodeMsg {

    private ErrorCodeInfo tatalErrorCode = new ErrorCodeInfo(ErrorCode.OPERATION_SUCCESS);

    private List<ErrorCodeInfo> smallErrorCodeList;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    public ErrorCodeMsg() {
        super();
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param retCode Error code
     */
    public ErrorCodeMsg(int retCode) {
        tatalErrorCode = new ErrorCodeInfo(retCode);
        smallErrorCodeList = new ArrayList<ErrorCodeInfo>();
    }

    public ErrorCodeInfo getTatalErrorCode() {
        return tatalErrorCode;
    }

    public void setTatalErrorCode(ErrorCodeInfo tatalErrorCode) {
        this.tatalErrorCode = tatalErrorCode;
    }

    public List<ErrorCodeInfo> getSmallErrorCodeList() {
        return smallErrorCodeList;
    }

    public void setSmallErrorCodeList(List<ErrorCodeInfo> smallErrorCodeList) {
        this.smallErrorCodeList = smallErrorCodeList;
    }

}
