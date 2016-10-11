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

package org.openo.sdno.result;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openo.sdno.exception.ErrorCode;

/**
 * Result test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-27
 */
public class ResultTest {

    @Test
    public void testResultInt() {
        Result result = new Result(1010);
        assertTrue(result.getErrcode() == 1010);
    }

    @Test
    public void testResultObject1() {

        Result<String> result = new Result(1010, "error");
        assertTrue(result.getErrcode() == 1010);
        assertTrue("error".equals(result.getResultObj()));
    }

    @Test
    public void testResultObject2() {

        Result<String> result = new Result(1010, "error");
        Result<String> result1 = new Result(result);
        assertTrue(result1.getErrcode() == 1010);
        assertTrue("error".equals(result1.getResultObj()));
    }

    @Test
    public void testResultSucess() {
        Result result = new Result();
        Result result1 = new Result(0);
        assertTrue(result1.getErrcode() == result.sucess().getErrcode());
    }

    @Test
    public void testResultSucess1() {
        Result result = new Result();
        Result result1 = new Result(0, "sucess");
        assertTrue(result1.getErrcode() == result.sucess("sucess").getErrcode());
        assertTrue(result1.getResultObj().equals(result.sucess("sucess").getResultObj()));
    }

    @Test
    public void testResultFailed() {
        Result result = new Result();
        Result result1 = new Result(ErrorCode.OPERATION_FAIL);
        assertTrue(result1.getErrcode() == result.failed().getErrcode());
    }

    @Test
    public void testResultFailed1() {
        Result result = new Result();
        Result result1 = new Result(ErrorCode.OPERATION_FAIL, "failed");
        assertTrue(result1.getErrcode() == result.failed("failed").getErrcode());
        assertTrue(result1.getResultObj().equals(result.sucess("failed").getResultObj()));
    }

    @Test
    public void testResultPartSucess() {
        Result result = new Result();
        Result result1 = new Result(ErrorCode.OPERATION_PART_SUCCESS);
        assertTrue(result1.getErrcode() == result.partSucess().getErrcode());
    }

    @Test
    public void testResultPartSucess1() {
        Result result = new Result();
        Result result1 = new Result(ErrorCode.OPERATION_PART_SUCCESS, "partsucess");
        assertTrue(result1.getErrcode() == result.partSucess("partsucess").getErrcode());
        assertTrue(result1.getResultObj().equals(result.partSucess("partsucess").getResultObj()));
    }

    @Test
    public void testIsSucessTrue() {
        Result result = new Result();
        result.setErrcode(0);
        assertTrue(result.isSucess());
    }

    @Test
    public void testIsSucessFalse() {
        Result result = new Result();
        result.setErrcode(1);
        assertFalse(result.isSucess());
    }

    @Test
    public void testIsFailedTrue() {
        Result result = new Result();
        result.setErrcode(1);
        assertTrue(result.isFailed());
    }

    @Test
    public void testIsFailedfalse() {
        Result result = new Result();
        result.setErrcode(0);
        assertFalse(result.isFailed());
    }

    @Test
    public void testIsValidTrue() {
        Result result = new Result(0);
        result.setResultObj("success");
        assertTrue(result.isValid());
    }

    @Test
    public void testIsValidfalse1() {
        Result result = new Result(0);
        assertFalse(result.isValid());
    }

    @Test
    public void testIsValidfalse2() {
        Result result = new Result(1);
        result.setResultObj("failed");
        assertFalse(result.isValid());
    }

    @Test
    public void testToString() {
        Result result = new Result(0);
        assertTrue("Result=[0]".equals(result.toString()));
    }
}
