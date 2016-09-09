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

package org.openo.sdno.util.ip;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import org.openo.sdno.exception.ParametersException;
import org.openo.sdno.util.ip.IpUtils;

public class IpUtilsTest {

    @Test
    public void test_isValidAddress() {
        String ip1 = "192.168.1.1";
        String ip2 = "19a.168.1.1";
        String ip3 = "192.168.1";
        String ip4 = "-1.168.1.1";
        String ip5 = "256.168.1.1";
        assertEquals(true, IpUtils.isValidAddress(ip1));
        assertEquals(false, IpUtils.isValidAddress(ip2));
        assertEquals(false, IpUtils.isValidAddress(ip3));
        assertEquals(false, IpUtils.isValidAddress(ip4));
        assertEquals(false, IpUtils.isValidAddress(ip5));
    }

    @Test
    public void test_isValidCidr() {
        String cidr1 = "192.168.150.111/24";
        String cidr2 = "";
        String cidr3 = "192.168.150.111/24/25";
        String cidr4 = "256.168.1.1/24";
        String cidr5 = "256.168.1.1/ac";
        String cidr6 = "256.168.1.1/33";
        String cidr7 = "256.168.1.1/-1";
        assertEquals(true, IpUtils.isValidCidr(cidr1));
        assertEquals(false, IpUtils.isValidCidr(cidr2));
        assertEquals(false, IpUtils.isValidCidr(cidr3));
        assertEquals(false, IpUtils.isValidCidr(cidr4));
        assertEquals(false, IpUtils.isValidCidr(cidr5));
        assertEquals(false, IpUtils.isValidCidr(cidr6));
        assertEquals(false, IpUtils.isValidCidr(cidr7));
    }

    @Test
    public void test_ipToLong() {
        String ip1 = "192.168.1.1";
        String ip2 = "256.168.1.1";
        assertEquals(3232235777l, IpUtils.ipToLong(ip1));
        assertEquals(-1, IpUtils.ipToLong(ip2));
    }

    @Test
    public void test_convertLong2Ip() {
        long ipNum1 = 3232235777l;
        long ipNum2 = 4294967296l;
        long ipNum3 = -1l;
        assertEquals("192.168.1.1", IpUtils.convertLong2Ip(ipNum1));
        assertEquals(null, IpUtils.convertLong2Ip(ipNum2));
        assertEquals(null, IpUtils.convertLong2Ip(ipNum3));
    }

    @Test
    public void test_maskToPrefix() {
        String mask1 = "256.1.1.1";
        String mask2 = "0.0.0.0";
        String mask3 = "255.255.255.255";
        String mask4 = "192.168.1.2";
        assertEquals(-1, IpUtils.maskToPrefix(mask1));
        assertEquals(0, IpUtils.maskToPrefix(mask2));
        assertEquals(32, IpUtils.maskToPrefix(mask3));
        assertEquals(2, IpUtils.maskToPrefix(mask4));
    }

    @Test
    public void test_prefixToMask() {
        int prefix1 = -1;
        int prefix2 = 33;
        int prefix3 = 0;
        int prefix4 = 32;
        int prefix5 = 23;
        assertEquals(null, IpUtils.prefixToMask(prefix1));
        assertEquals(null, IpUtils.prefixToMask(prefix2));
        assertEquals("0.0.0.0", IpUtils.prefixToMask(prefix3));
        assertEquals("255.255.255.255", IpUtils.prefixToMask(prefix4));
        assertEquals("255.255.254.0", IpUtils.prefixToMask(prefix5));
    }

    @Test
    public void test_getIPFromCIDR() {
        String cidr1 = null;
        String cidr2 = "";
        String cidr3 = "192.168.150.111/24";
        assertEquals("", IpUtils.getIPFromCIDR(cidr1));
        assertEquals("", IpUtils.getIPFromCIDR(cidr2));
        assertEquals("192.168.150.111", IpUtils.getIPFromCIDR(cidr3));
    }

    @Test(expected = ParametersException.class)
    public void test_getIPFromCIDRNegtive() {
        String cidr3 = "192.168.150.111/aa";
        IpUtils.getIPFromCIDR(cidr3);
    }

}
