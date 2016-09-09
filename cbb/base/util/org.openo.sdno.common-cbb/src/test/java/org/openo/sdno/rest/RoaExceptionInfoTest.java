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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;

public class RoaExceptionInfoTest {

    @Test
    public void testFormatMessageNull() {
        ServiceException serviceException = new ServiceException();
        serviceException.setId("testId");
        RoaExceptionInfo roaExceptionInfo = new RoaExceptionInfo();
        RoaExceptionInfo roaExceptionInfoResult = (RoaExceptionInfo)roaExceptionInfo.formatMessage(serviceException);
        assertEquals(roaExceptionInfoResult.getExceptionId(), "testId");
        assertEquals(roaExceptionInfoResult.getExceptionType(), "ROA_EXFRAME_EXCEPTION");
    }

    @Test
    public void testFormatMessageSuccess() {
        ExceptionArgs exceptionArgs = new ExceptionArgs();
        String[] test = {"test"};
        exceptionArgs.setAdviceArgs(test);
        ServiceException serviceException = new ServiceException();
        serviceException.setId("testId");
        serviceException.setExceptionArgs(exceptionArgs);
        RoaExceptionInfo roaExceptionInfo = new RoaExceptionInfo();
        RoaExceptionInfo roaExceptionInfoResult = (RoaExceptionInfo)roaExceptionInfo.formatMessage(serviceException);
        assertEquals(roaExceptionInfoResult.getExceptionId(), "testId");
        assertEquals(roaExceptionInfoResult.getExceptionType(), "ROA_EXFRAME_EXCEPTION");
        assertEquals(roaExceptionInfoResult.getAdviceArgs(), test);
    }

    @Test
    public void testFormatMessageID() {
        RoaExceptionInfo roaExceptionInfo = new RoaExceptionInfo();
        RoaExceptionInfo roaExceptionInfoResult = (RoaExceptionInfo)roaExceptionInfo.formatMessage("testId");
        assertEquals(roaExceptionInfoResult.getExceptionId(), "testId");
        assertEquals(roaExceptionInfoResult.getExceptionType(), "ROA_EXFRAME_EXCEPTION");
    }
}
