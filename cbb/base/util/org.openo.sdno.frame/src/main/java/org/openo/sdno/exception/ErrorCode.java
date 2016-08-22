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

package org.openo.sdno.exception;

/**
 * Class to accommodate all the required errorcode constants<br/>
 * 
 * @author
 * @version SDNO 0.5 13-Apr-2016
 */
public class ErrorCode {

    public static final int SUCCESS = 0;

    /**
     * Encryption failed.
     */
    public static final int ENCRYPTION_FAILED = 10010001;

    /**
     * Integrity verification failed.
     */
    public static final int NETMATRIX_ERROR_INV_VERIFY_INTEGRITY = 10010002;

    /**
     * Query parameters legitimacy failed.
     */
    public static final int NETMATRIX_ERROR_INV_VERIFY_VALIDITY = 10010003;

    /**
     * Not unique exception.
     */
    public static final int NETMATRIX_ERROR_INV_VERIFY_UNIQUE = 10010004;

    /**
     * Resource type has not been registered,access to configuration property item has
     * failed.
     */
    public static final int RESOURCE_OBTAIN_ATTR_CONFIGURATION_FAILED = 10010008;

    /**
     * Properties of the resource has not been registered, access to configuration property item has
     * failed.
     */
    public static final int ATTR_OBTAIN_ATTR_CONFIGURATION_FAILED = 10010009;

    /**
     * The resource type has not been registered, failed accessing attribute validation rule.
     */
    public static final int RESOURCE_OBTAIN_ATTR_RULE_FAILED = 10010010;

    /**
     * The property type has not been registered, failed accessing attribute validation rule.
     */
    public static final int ATTR_OBTAIN_ATTR_RULE_FAILED = 10010011;

    /**
     * Resource does not exist.
     */
    public static final int RESOURCE_NOT_EXIST = 10010013;

    /**
     * Delete resource failure error.
     */
    public static final int DELETE_RESOURCE_ERROR = 10010014;

    /**
     * Invalid type or version.
     */
    public static final int INVALID_TYPE_OR_VERSIONI = 10010015;

    /**
     * Partially succeeded.
     */
    public static final int OPERATION_PART_SUCCESS = 1031001;

    /**
     * Successful operation.
     */
    public static final int OPERATION_SUCCESS = 0;

    /**
     * operation failed.
     */
    public static final int OPERATION_FAIL = 1031003;

    /**
     * Database operation failed.
     */
    public static final int COMM_ABNORMAL_ERROR = 1032001;

    /**
     * NE response error.
     */
    public static final int DB_OPERATION_ERROR = 1032002;

    /**
     * Erroneous NE response.
     */
    public static final int NE_RETURN_ERROR = 1032003;

    /**
     * Invalid parameter.
     */
    public static final int TE_ERR_INVALID_PARAMETER = 1032004;

    /**
     * Internal error.
     */
    public static final int INNER_ERROR = 1032005;

    /**
     * Resource is locked.
     */
    public static final int RESOURCE_BELOCKED_ERROR = 1032007;

    /**
     * Resources are occupied.
     */
    public static final int RESOURCE_BEUESD_ERROR = 1032008;

    /**
     * Internal service failed to respond.
     */
    public static final int INNER_SVC_RESPONSE_ERROR = 1032025;

    /**
     * No corresponding device plug-ins found.
     */
    public static final int ADAPTER_NO_DEVICE_PLUGIN = 1032026;

    /**
     * Query service failure.
     */
    public static final int QUERY_INV_SERVICE_FAILED = 1032027;

    /**
     * Get template file failed.
     */
    public static final int GET_TEMPLATE_FILE_FAILED = 1032028;

    /**
     * Convert file template failed.
     */
    public static final int TRANS_FILE_TO_TEMPLATE_FAILED = 1032029;

    /**
     * Template mapping failed.
     */
    public static final int TEMPLATE_MAPPING_FAILED = 1032030;

    /**
     * Data conversion failed.
     */
    public static final int RESPONSE_TO_TEMPLATE_PARAMS_FAILED = 1032031;

    /**
     * Script execution failed.
     */
    public static final int EXEC_SCRIPT_FAILED = 1032032;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    private ErrorCode() {

    }

    /**
     * Judge whether successful.<br/>
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
