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

import org.openo.sdno.exception.ErrorCode;

/**
 * Generic result object.<br/>
 * 
 * @param <T> Result object
 * @author
 * @version SDNO 0.5 13-Apr-2016
 */
public class Result<T> {

    protected int errcode = ErrorCode.OPERATION_SUCCESS;

    protected T resultObj;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    public Result() {
        /* Empty constructor */
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param operationResult Operation result
     */
    public Result(int operationResult) {
        this.errcode = operationResult;
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param other Result object
     */
    public Result(Result<T> other) {
        this.errcode = other.getErrcode();
        this.resultObj = other.getResultObj();
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param operationResult Operation result
     * @param object Result object
     */
    public Result(int operationResult, T object) {
        this.errcode = operationResult;
        this.resultObj = object;
    }

    /**
     * Success result.<br/>
     * 
     * @return Success result
     * @since SDNO 0.5
     */
    public static Result sucess() {
        return new Result(ErrorCode.OPERATION_SUCCESS);
    }

    /**
     * Success result.<br/>
     * 
     * @param obj Object
     * @return Success result
     * @since SDNO 0.5
     */
    public static Result sucess(Object obj) {
        return new Result(ErrorCode.OPERATION_SUCCESS, obj);
    }

    /**
     * Failed result.<br/>
     * 
     * @return Failed result
     * @since SDNO 0.5
     */
    public static Result failed() {
        return new Result(ErrorCode.OPERATION_FAIL);
    }

    /**
     * Failed result.<br/>
     * 
     * @param obj Object
     * @return Failed result
     * @since SDNO 0.5
     */
    public static Result failed(Object obj) {
        return new Result(ErrorCode.OPERATION_FAIL, obj);
    }

    /**
     * Partly success result.<br/>
     * 
     * @return Partly success result
     * @since SDNO 0.5
     */
    public static Result partSucess() {
        return new Result(ErrorCode.OPERATION_PART_SUCCESS);
    }

    /**
     * Partly success result.<br/>
     * 
     * @param obj Object
     * @return Partly success result
     * @since SDNO 0.5
     */
    public static Result partSucess(Object obj) {
        return new Result(ErrorCode.OPERATION_PART_SUCCESS, obj);
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public T getResultObj() {
        return resultObj;
    }

    public void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }

    public boolean isSucess() {
        return errcode == ErrorCode.OPERATION_SUCCESS;
    }

    public boolean isFailed() {
        return errcode != ErrorCode.OPERATION_SUCCESS;
    }

    public boolean isValid() {
        return isSucess() && (this.getResultObj() != null);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Result=[").append(this.errcode);
        sb.append(']');
        return sb.toString();
    }
}
