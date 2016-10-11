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

package org.openo.sdno.ssl;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * EncryptionUtilTest test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-21
 */
public class EncryptionUtilTest {

    @Test
    public void encodeTest() {

        // Test -ve: Root key is invalid
        char acInput[] = {'m', 'u', 'r', 'a', 'l', 'i'};
        EncryptionUtil.encode(acInput);
        assert (true);

        // Test -ve:Input is empty
        char ac2Input[] = new char[256];
        EncryptionUtil.encode(ac2Input);
        assert (true);
    }

    @Test
    public void decodeTest() {

        // Test -ve:Input is empty
        char ac2Input[] = new char[256];
        EncryptionUtil.decode(ac2Input);
        assert (true);
    }

    @Test
    public void clearTest() {

        // Test+ve: valid char array
        char acInput[] = {'m', 'u', 'r', 'a', 'l', 'i'};
        EncryptionUtil.clear(acInput);
        char acExpected[] = new char[6];
        assertArrayEquals(acExpected, acInput);

        // Test -ve: input is null
        EncryptionUtil.clear(null);

        // Test -ve: array length is zero
        char acSingleInput[] = {};
        EncryptionUtil.clear(acSingleInput);
        assertArrayEquals(acSingleInput, new char[0]);

    }
}
