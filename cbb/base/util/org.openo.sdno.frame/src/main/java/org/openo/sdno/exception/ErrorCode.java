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

package org.openo.sdno.exception;

/**
 * Class to accommodate all the required errorcode constants<br>
 * 
 * @author
 * @version SDNO 0.5 13-Apr-2016
 */
public class ErrorCode {

    /**
     * Successful.
     */
    public static final int SUCCESS = 0;

    /**
     * Successful operation.
     */
    public static final int OPERATION_SUCCESS = 0;

    /**
     * Partially succeeded.
     */
    public static final int OPERATION_PART_SUCCESS = 1031001;

    /**
     * operation failed.
     */
    public static final int OPERATION_FAIL = 1031003;

    /**
     * NE response error.
     */
    public static final int DB_OPERATION_ERROR = 1032002;

    /**
     * Internal error.
     */
    public static final int INNER_ERROR = 1032005;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    private ErrorCode() {

    }

    /**
     * Judge whether successful.<br>
     * 
     * @param code The sign
     * @return true when code equals SUCCESS or OPERATION_SUCCESS
     *         false when code doesn't equal SUCCESS and OPERATION_SUCCESS
     * @since SDNO 0.5
     */
    public static boolean isSucess(int code) {
        return (code == SUCCESS) || (code == OPERATION_SUCCESS);
    }
}
