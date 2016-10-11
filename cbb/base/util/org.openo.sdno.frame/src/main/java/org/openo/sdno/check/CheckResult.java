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

package org.openo.sdno.check;

/**
 * <br>
 * <p>
 * Response data.
 * </p>
 * 
 * @author
 * @version SDNO 0.5 13-April-2016
 */
public class CheckResult {

    /**
     * The error code.
     */
    private int result;

    /**
     * Error message.
     */
    private String message;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param result error code
     * @param message error message
     */
    public CheckResult(int result, String message) {
        this.result = result;
        this.message = message;
    }

    /**
     * Returns the error code<br>
     * 
     * @return error code of the response.
     * @since SDNO 0.5
     */
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    /**
     * <br>
     * 
     * @return error message of the response.
     * @since SDNO 0.5
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the error information <br>
     * 
     * @return error code and error message of the response
     * @since SDNO 0.5
     */
    public String getErrorInfo() {
        return "Data check failed. field=" + message;
    }
}
