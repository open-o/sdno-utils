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

/**
 * Error Object containing error code and description and object information.<br/>
 * 
 * @author
 * @version SDNO 0.5 13-Apr-2016
 */
public class ErrorCodeInfo {

    private int errorCode;

    private String descrption;

    private String objectId;

    private String objectName;

    private Object object;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param errorCode Error code
     */
    public ErrorCodeInfo(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param errorCode Error code
     * @param obj Object
     */
    public ErrorCodeInfo(int errorCode, Object obj) {
        super();
        this.errorCode = errorCode;
        this.object = obj;
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param errorCode Error code
     * @param descrption Description
     * @param objectName Object name
     */
    public ErrorCodeInfo(int errorCode, String descrption, String objectName) {
        super();
        this.errorCode = errorCode;
        this.descrption = descrption;
        this.objectName = objectName;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
