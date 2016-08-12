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

package org.openo.sdno.rest;

import org.apache.commons.httpclient.HttpStatus;
import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to help process restful response message and repackage the information.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-4-13
 */
public class ResponseUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);

    private ResponseUtils() {

    }

    /**
     * Deal with restful request response, if state is OK transfer the body to the given class, or
     * throw ServiceException. <br/>
     * 
     * @param response response result of restful request.
     * @param clazz class to transfer to.
     * @return result object of class T.
     * @throws ServiceException if response state is not ok.
     * @since SDNO 0.5
     */
    public static <T> T transferResponse(RestfulResponse response, Class<T> clazz) throws ServiceException {
        int httpStatus = response.getStatus();
        if((httpStatus >= HttpStatus.SC_OK) && (httpStatus < HttpStatus.SC_MULTIPLE_CHOICES)) {
            return JsonUtil.fromJson(response.getResponseContent(), clazz);
        } else {
            LOGGER.error("response failed, response content: " + response.getResponseContent());
            checkResonseAndThrowException(response);
            return null;
        }
    }

    /**
     * Transfer the restful request response, if status is ok then transfer it to a string, or throw
     * a exception. <br/>
     * 
     * @param response RestfulResponse need to be checked.
     * @return String transfered from RestfulResponse object.
     * @throws ServiceException if status is not ok.
     * @since SDNO 0.5
     */
    public static String transferResponse(RestfulResponse response) throws ServiceException {
        int httpStatus = response.getStatus();
        if((httpStatus >= HttpStatus.SC_OK) && (httpStatus < HttpStatus.SC_MULTIPLE_CHOICES)) {
            return response.getResponseContent();
        } else {
            LOGGER.error("transferResponse failed.");
            checkResonseAndThrowException(response);
            return null;
        }
    }

    /**
     * Check the response of restful request, if status is not ok then throw a exception, only for
     * internal interface. <br/>
     * 
     * @param response RestfulResponse need to be checked.
     * @throws ServiceException if status is not ok.
     * @since SDNO 0.5
     */
    public static void checkResonseAndThrowException(RestfulResponse response) throws ServiceException {
        if(!(response.getStatus() / 100 == 2)) {
            RoaExceptionInfo roaExceptionInfo =
                    JsonUtil.fromJson(response.getResponseContent(), RoaExceptionInfo.class);
            ServiceException serviceException = new ServiceException();
            serviceException.setHttpCode(response.getStatus());
            serviceException.setId(roaExceptionInfo.getExceptionId());
            serviceException.setExceptionArgs(
                    new ExceptionArgs(roaExceptionInfo.getDescArgs(), roaExceptionInfo.getReasonArgs(),
                            roaExceptionInfo.getDetailArgs(), roaExceptionInfo.getAdviceArgs()));

            LOGGER.error("checkResonseAndThrowException getStatus is not OK");
            throw serviceException;
        }
    }
}
