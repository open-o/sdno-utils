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

/**
 * RestfulProxy class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-3-26
 */
public class RestfulProxy {

    /**
     * Rest Request processor.
     */
    private static IProcessor processor;

    static {
        processor = new RestProcessor();
    }

    private RestfulProxy() {

    }

    /**
     * do get action.<br/>
     * 
     * @param uri uri of the http request.
     * @param restParametes parameters of rest request.
     * @return RestfulResponse from the server.
     * @throws ServiceException if internal error happens.
     * @since SDNO 0.5
     */
    public static RestfulResponse get(String uri, RestfulParametes restParametes) throws ServiceException {
        return processor.doAction(RestfulMethod.GET, uri, restParametes);
    }

    /**
     * do get action.<br/>
     * 
     * @param uri uri of the http request.
     * @param restParametes parameters of rest request.
     * @param restOptions options of the rest.
     * @return RestfulResponse from the server.
     * @throws ServiceException if internal error happens.
     * @since SDNO 0.5
     */
    public static RestfulResponse get(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
            throws ServiceException {
        return processor.doAction(RestfulMethod.GET, uri, restParametes, restOptions);
    }

    /**
     * do put action.<br/>
     * 
     * @param uri uri of the http request.
     * @param restParametes parameters of rest request.
     * @return RestfulResponse from the server.
     * @throws ServiceException if internal error happens.
     * @since SDNO 0.5
     */
    public static RestfulResponse put(String uri, RestfulParametes restParametes) throws ServiceException {
        return processor.doAction(RestfulMethod.PUT, uri, restParametes);
    }

    /**
     * do put action.<br/>
     * 
     * @param uri uri of the http request.
     * @param restParametes parameters of rest request.
     * @param restOptions options of the rest.
     * @return RestfulResponse from the server.
     * @throws ServiceException if internal error happens.
     * @since SDNO 0.5
     */
    public static RestfulResponse put(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
            throws ServiceException {
        return processor.doAction(RestfulMethod.PUT, uri, restParametes, restOptions);
    }

    /**
     * do post action.<br/>
     * 
     * @param uri uri of the http request.
     * @param restParametes parameters of rest request.
     * @return RestfulResponse from the server.
     * @throws ServiceException if internal error happens.
     * @since SDNO 0.5
     */
    public static RestfulResponse post(String uri, RestfulParametes restParametes) throws ServiceException {
        return processor.doAction(RestfulMethod.POST, uri, restParametes);
    }

    /**
     * do post action.<br/>
     * 
     * @param uri uri of the http request.
     * @param restParametes parameters of rest request.
     * @param restOptions options of the rest.
     * @return RestfulResponse from the server.
     * @throws ServiceException if internal error happens.
     * @since SDNO 0.5
     */
    public static RestfulResponse post(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
            throws ServiceException {
        return processor.doAction(RestfulMethod.POST, uri, restParametes, restOptions);
    }

    /**
     * do delete action.<br/>
     * 
     * @param uri uri of the http request.
     * @param restParametes parameters of rest request.
     * @return RestfulResponse from the server.
     * @throws ServiceException if internal error happens.
     * @since SDNO 0.5
     */
    public static RestfulResponse delete(String uri, RestfulParametes restParametes) throws ServiceException {
        return processor.doAction(RestfulMethod.DELETE, uri, restParametes);
    }

    /**
     * do post action.<br/>
     * 
     * @param uri uri of the http request.
     * @param restParametes parameters of rest request.
     * @param restOptions options of the rest.
     * @return RestfulResponse from the server.
     * @throws ServiceException if internal error happens.
     * @since SDNO 0.5
     */
    public static RestfulResponse delete(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
            throws ServiceException {
        return processor.doAction(RestfulMethod.DELETE, uri, restParametes, restOptions);
    }
}
