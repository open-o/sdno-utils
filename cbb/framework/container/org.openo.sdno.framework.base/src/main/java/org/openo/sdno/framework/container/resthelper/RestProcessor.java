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

package org.openo.sdno.framework.container.resthelper;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.IpConfig;

/**
 * Restful processor class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-3-26
 */
public class RestProcessor implements IProcessor {

    /**
     * Do the action and realization the processor function.<br>
     * 
     * @param action Restful method
     * @param uri the objective Url
     * @param restParametes Restful parameter
     * @return Restful response
     * @throws ServiceException when get token failed or action realization method failed.
     * @since SDNO 0.5
     */
    @Override
    public RestfulResponse doAction(RestfulMethod action, String uri, RestfulParametes restParametes)
            throws ServiceException {
        RestfulOptions restOptions = new RestfulOptions();
        restOptions.setHost(IpConfig.getLocalHost());
        restOptions.setPort(IpConfig.getRestPort());
        return action.method(uri, restParametes, restOptions);
    }

    /**
     * Do the action and realization the processor function.<br>
     * 
     * @param action Restful method
     * @param uri the objective Url
     * @param restParametes Restful parameter
     * @param restOptions Restful options
     * @return Restful response
     * @throws ServiceException when get token failed or action realization method failed.
     * @since SDNO 0.5
     */
    @Override
    public RestfulResponse doAction(RestfulMethod action, String uri, RestfulParametes restParametes,
            RestfulOptions restOptions) throws ServiceException {
        restOptions.setHost(IpConfig.getLocalHost());
        restOptions.setPort(IpConfig.getRestPort());
        return action.method(uri, restParametes, restOptions);
    }

}
