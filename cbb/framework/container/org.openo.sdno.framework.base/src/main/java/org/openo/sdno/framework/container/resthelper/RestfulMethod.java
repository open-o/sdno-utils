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
import org.openo.baseservice.roa.util.restclient.RestfulFactory;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;

/**
 * rest method enumration class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-5-24
 */
public enum RestfulMethod {
    GET {

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes) throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).get(uri, restParametes);
        }

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).get(uri, restParametes, restOptions);
        }
    },

    POST {

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes) throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).post(uri, restParametes);
        }

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).post(uri, restParametes, restOptions);
        }
    },

    PUT {

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes) throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).put(uri, restParametes);
        }

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).put(uri, restParametes, restOptions);
        }
    },

    DELETE {

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes) throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).delete(uri, restParametes);
        }

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).delete(uri, restParametes, restOptions);
        }
    },

    PATCH {

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes) throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).patch(uri, restParametes);
        }

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).patch(uri, restParametes, restOptions);
        }
    },

    HEAD {

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes) throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).head(uri, restParametes);
        }

        @Override
        public RestfulResponse method(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            return RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP).head(uri, restParametes, restOptions);
        }
    };

    /**
     * process the rest http request.<br/>
     * 
     * @param uri uri of the request.
     * @param restParametes parameters of the restful request
     * @return RestfulResponse from the object server.
     * @throws ServiceException if internal error happens.
     * @since SDNO 0.5
     */
    public abstract RestfulResponse method(String uri, RestfulParametes restParametes) throws ServiceException;

    /**
     * process the rest http request with options.<br/>
     * 
     * @param uri uri of the request.
     * @param restParametes parameters of the restful request
     * @param restOptions options of rest request.
     * @return RestfulResponse from the object server.
     * @throws ServiceException if internal error happens.
     * @since SDNO 0.5
     */
    public abstract RestfulResponse method(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
            throws ServiceException;
}
