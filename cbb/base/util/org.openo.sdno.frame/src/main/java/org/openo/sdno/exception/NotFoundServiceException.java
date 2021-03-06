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

import org.openo.baseservice.remoteservice.exception.ServiceException;

/**
 * Resource not found exception.<br>
 * 
 * @author
 * @version SDNO 0.5 13-April-2016
 */
@SuppressWarnings("serial")
public class NotFoundServiceException extends ServiceException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public NotFoundServiceException() {
        super(String.valueOf(ErrorCode.INNER_ERROR), HttpCode.NOT_FOUND);
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param msg Exception arguments message
     */
    public NotFoundServiceException(String msg) {
        super(String.valueOf(ErrorCode.INNER_ERROR), msg);
        super.setHttpCode(HttpCode.NOT_FOUND);
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param ex Throwable object
     */
    public NotFoundServiceException(Throwable ex) {
        super(String.valueOf(ErrorCode.INNER_ERROR), ex);
        super.setHttpCode(HttpCode.NOT_FOUND);
    }
}
