/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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
package org.openo.sdno.healthcheck.rest;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.healthcheck.service.DefaultHealthChecker;

import mockit.Mock;
import mockit.MockUp;

public class HealthCheckRoaResourceTest {

	@Test
	public void testGetstatus_UP() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		new MockUp<RestfulProxy>() {
			@Mock
			public RestfulResponse get(String uri, RestfulParametes restParametes) throws ServiceException {
				return null;
			}
		};
		HealthCheckRoaResource roa = new HealthCheckRoaResource();
		
		Field checkerField = roa.getClass().getDeclaredField("checker");
		checkerField.setAccessible(true);
		DefaultHealthChecker checker = new DefaultHealthChecker();
		checkerField.set(roa, checker);
		
		Map<String, String> result = roa.getstatus();
		assertEquals("UP", result.get("MSB"));
		
	}
	
	@Test
	public void testGetstatus_down() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		new MockUp<RestfulProxy>() {
			@Mock
			public RestfulResponse get(String uri, RestfulParametes restParametes) throws ServiceException {
				throw new ServiceException();
			}
		};
		HealthCheckRoaResource roa = new HealthCheckRoaResource();
		
		Field checkerField = roa.getClass().getDeclaredField("checker");
		checkerField.setAccessible(true);
		DefaultHealthChecker checker = new DefaultHealthChecker();
		checkerField.set(roa, checker);
		
		Map<String, String> result = roa.getstatus();
		assertEquals("DOWN", result.get("MSB"));
		
	}

}
