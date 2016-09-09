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

public class InvRspTest {

    @Test
    public void testIsSuccessTrue1() {
        ErrorCodeMsg errorCodeMsg = new ErrorCodeMsg(0);
        InvRsp invRsp = new InvRsp("test", 1, errorCodeMsg);
        assertTrue(invRsp.isSucess());
    }

    @Test
    public void testIsSuccessTrue2() {
        InvRsp invRsp = new InvRsp();
        assertTrue(invRsp.isSucess());
    }

    @Test
    public void testIsSuccessFalse() {
        ErrorCodeMsg errorCodeMsg = new ErrorCodeMsg(1);
        InvRsp invRsp = new InvRsp("test", 1, errorCodeMsg);
        assertFalse(invRsp.isSucess());
    }

}
