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

package org.openo.sdno.result;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.openo.sdno.exception.ErrorCode;


/**
 * Generic inventory response.<br>
 * 
 * @param <T> Data
 * @author
 * @version SDNO 0.5 13-Apr-2016
 */
public class InvRsp<T> {

    @JsonProperty
    protected int row;

    @JsonProperty
    protected T data;

    @JsonProperty
    protected ErrorCodeMsg msg = new ErrorCodeMsg();

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public InvRsp() {
        /* Empty constructor - no need to do anything */
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param data The inventory response data
     * @param row The data row
     */
    public InvRsp(T data, int row) {
        this.row = row;
        this.data = data;
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param data The inventory response data
     * @param row The data row
     * @param msg Error code message
     */
    public InvRsp(T data, int row, ErrorCodeMsg msg) {
        this.row = row;
        this.data = data;
        this.msg = msg;
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param errCode Error code
     */
    public InvRsp(int errCode) {
        this.row = 0;
        this.data = null;
        this.msg = new ErrorCodeMsg(errCode);
    }

    @JsonIgnore
    public T getData() {
        return data;
    }

    @JsonIgnore
    public int getRow() {
        return row;
    }

    public boolean isSucess() {
        if((this.getMsg() == null) || (this.getMsg().getTatalErrorCode() == null)) {
            return true;
        }
        return ErrorCode.isSucess(this.getMsg().getTatalErrorCode().getErrorCode());
    }

    @JsonIgnore
    public ErrorCodeMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "InvResp row = " + row;
    }
}
