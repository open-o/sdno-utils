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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;

import mockit.Mock;
import mockit.MockUp;

/**
 * RestfulProxy test class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-3-30
 */
public class RestfulProxyTest {

    /**
     * JSON format.
     */
    private static final String MEDIA_TYPE_JSON = "application/json;charset=UTF-8";

    /**
     * Type header.
     */
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGet1() throws ServiceException {
        final String uri = "/get";
        final RestfulParametes restParam = new RestfulParametes();
        restParam.putHttpContextHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_JSON);

        new MockUp<RestProcessor>() {

            @Mock
            public RestfulResponse doAction(RestfulMethod action, String uri, RestfulParametes restParametes)
                    throws ServiceException {
                assertEquals(action.name(), RestfulMethod.GET.name());
                return null;

            }

        };

        RestfulProxy.get(uri, restParam);

    }

    @Test
    public void testGet2() throws ServiceException {
        final String uri = "/get";
        final RestfulParametes restParam = new RestfulParametes();
        restParam.putHttpContextHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_JSON);
        RestfulOptions restfulOptions = new RestfulOptions();
        new MockUp<RestProcessor>() {

            @Mock
            public RestfulResponse doAction(RestfulMethod action, String uri, RestfulParametes restParametes,
                    RestfulOptions restOptions) throws ServiceException {
                assertEquals(action.name(), RestfulMethod.GET.name());
                return null;

            }

        };

        RestfulProxy.get(uri, restParam, restfulOptions);
    }

    @Test
    public void testPut1() throws ServiceException {
        final String uri = "/put";
        final RestfulParametes restParam = new RestfulParametes();
        restParam.putHttpContextHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_JSON);

        new MockUp<RestProcessor>() {

            @Mock
            public RestfulResponse doAction(RestfulMethod action, String uri, RestfulParametes restParametes)
                    throws ServiceException {
                assertEquals(action.name(), RestfulMethod.PUT.name());
                return null;

            }

        };

        RestfulProxy.put(uri, restParam);

    }

    @Test
    public void testPut2() throws ServiceException {
        final String uri = "/put";
        final RestfulParametes restParam = new RestfulParametes();
        restParam.putHttpContextHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_JSON);
        RestfulOptions restfulOptions = new RestfulOptions();
        new MockUp<RestProcessor>() {

            @Mock
            public RestfulResponse doAction(RestfulMethod action, String uri, RestfulParametes restParametes,
                    RestfulOptions restOptions) throws ServiceException {
                assertEquals(action.name(), RestfulMethod.PUT.name());
                return null;

            }

        };

        RestfulProxy.put(uri, restParam, restfulOptions);
    }

    @Test
    public void testPost1() throws ServiceException {
        final String uri = "/post";
        final RestfulParametes restParam = new RestfulParametes();
        restParam.putHttpContextHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_JSON);

        new MockUp<RestProcessor>() {

            @Mock
            public RestfulResponse doAction(RestfulMethod action, String uri, RestfulParametes restParametes)
                    throws ServiceException {
                assertEquals(action.name(), RestfulMethod.POST.name());
                return null;

            }

        };

        RestfulProxy.post(uri, restParam);

    }

    @Test
    public void testPost2() throws ServiceException {
        final String uri = "/post";
        final RestfulParametes restParam = new RestfulParametes();
        restParam.putHttpContextHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_JSON);
        RestfulOptions restfulOptions = new RestfulOptions();
        new MockUp<RestProcessor>() {

            @Mock
            public RestfulResponse doAction(RestfulMethod action, String uri, RestfulParametes restParametes,
                    RestfulOptions restOptions) throws ServiceException {
                assertEquals(action.name(), RestfulMethod.POST.name());
                return null;

            }

        };

        RestfulProxy.post(uri, restParam, restfulOptions);
    }

    @Test
    public void testDelete1() throws ServiceException {
        final String uri = "/delete";
        final RestfulParametes restParam = new RestfulParametes();
        restParam.putHttpContextHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_JSON);

        new MockUp<RestProcessor>() {

            @Mock
            public RestfulResponse doAction(RestfulMethod action, String uri, RestfulParametes restParametes)
                    throws ServiceException {
                assertEquals(action.name(), RestfulMethod.DELETE.name());
                return null;

            }

        };

        RestfulProxy.delete(uri, restParam);

    }

    @Test
    public void testDelete2() throws ServiceException {
        final String uri = "/delete";
        final RestfulParametes restParam = new RestfulParametes();
        restParam.putHttpContextHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_JSON);
        RestfulOptions restfulOptions = new RestfulOptions();
        new MockUp<RestProcessor>() {

            @Mock
            public RestfulResponse doAction(RestfulMethod action, String uri, RestfulParametes restParametes,
                    RestfulOptions restOptions) throws ServiceException {
                assertEquals(action.name(), RestfulMethod.DELETE.name());
                return null;
            }

        };

        RestfulProxy.delete(uri, restParam, restfulOptions);
    }
}
