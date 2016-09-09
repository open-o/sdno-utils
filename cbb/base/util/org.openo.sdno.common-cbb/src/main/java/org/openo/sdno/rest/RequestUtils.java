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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.sdno.framework.container.util.JsonUtil;

/**
 * Utility class to help construct a restful request. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-13
 */
public class RequestUtils {

    private RequestUtils() {
    }

    /**
     * construct a restful request fill in the ContentType field and the request body. <br>
     * 
     * @param sendObj object need to send.
     * @return RestfulParametes filled with message.
     * @throws ServiceException if transfer object to json string have exception.
     * @since SDNO 0.5
     */
    public static RestfulParametes constructRestParameters(Object sendObj) throws ServiceException {
        RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");
        if(sendObj != null) {
            String strJsonReq = JsonUtil.toJson(sendObj);
            restParametes.setRawData(strJsonReq);
        }
        return restParametes;
    }
}
