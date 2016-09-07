/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.util.ip;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.openo.baseservice.remoteservice.exception.ServiceException;
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
    public void test_getUserNetworkMask() {
        String ipsubnet = "255.0.0.0";
        int ipnum1 = 6;
        int ipnum2 = -1;
        assertEquals(29, IpUtils.getUserNetworkMask(ipsubnet, ipnum1));
        assertEquals(32, IpUtils.getUserNetworkMask(ipsubnet, ipnum2));
    }

    @Test
    public void test_isReservedIp_String() {
        String ipAddr1 = "255.255.255.255";
        String ipAddr2 = "253.123.1.1";
        String ipAddr3 = "0.0.0.0";
        assertEquals(true, IpUtils.isReservedIp(ipAddr1));
        assertEquals(false, IpUtils.isReservedIp(ipAddr2));
        assertEquals(true, IpUtils.isReservedIp(ipAddr3));
    }

    @Test
    public void test_isReservedIp_Long() {
        long ipAddr1 = 4294967295L;
        long ipAddr2 = 424967295L;
        long ipAddr3 = 0L;
        assertEquals(true, IpUtils.isReservedIp(ipAddr1));
        assertEquals(false, IpUtils.isReservedIp(ipAddr2));
        assertEquals(true, IpUtils.isReservedIp(ipAddr3));
    }

    @Test
    public void test_getUserSubnet() {
        String ip = "192.168.1.1";
        List<Integer> masks = new ArrayList<Integer>();
        masks.add(255);
        masks.add(224);
        masks.add(0);
        masks.add(1);

        assertEquals("64.168.1.1", IpUtils.getUserSubnet(ip, masks));
    }

    @Test
    public void test_longToIp() {
        long ipNum = 3232235777l;
        assertEquals("192.168.1.1", IpUtils.longToIp(ipNum));
    }

    @Test
    public void test_ipToLong() {
        String ip1 = "192.168.1.1";
        String ip2 = "256.168.1.1";
        assertEquals(3232235777l, IpUtils.ipToLong(ip1));
        assertEquals(-1, IpUtils.ipToLong(ip2));
    }

    @Test
    public void test_convertIp2Long() {
        String ip1 = "192.168.1.1";
        String ip2 = "256.168.1.-1";
        assertEquals(3232235777l, IpUtils.convertIp2Long(ip1));
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
    public void test_intToIp() {
        int ipint = 19216811;
        assertEquals("1.37.57.171", IpUtils.intToIp(ipint));
    }

    @Test
    public void test_ipToBytesByReg() {
        String ipAddr = "192.168.1.1";
        byte[] ret = IpUtils.ipToBytesByReg(ipAddr);
        byte[] cal = {-64, -88, 1, 1};
        assertArrayEquals(cal, ret);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_ipToBytesByRegNegtive() {
        String ipAddr = "192.168.a.1";
        IpUtils.ipToBytesByReg(ipAddr);
    }

    @Test
    public void test_bytesToInt() {
        byte[] ret = {-64, -88, 1, 1};
        assertEquals(-1062731519, IpUtils.bytesToInt(ret));
    }

    @Test
    public void test_ipToInt() {
        String ipAddr1 = "192.168.1.1";
        int ret1 = IpUtils.ipToInt(ipAddr1);
        assertEquals(-1062731519, ret1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_ipToIntNegtive() {
        String ipAddr1 = "192.168.a.1";
        IpUtils.ipToInt(ipAddr1);
    }

    @Test
    public void test_trimIpAddress() {
        String srcIp1 = "";
        String srcIp2 = "256 . 168 .   1   . 1";
        String srcIp3 = "192 . 168 .   1   . 1";
        assertEquals(null, IpUtils.trimIpAddress(srcIp1));
        assertEquals(null, IpUtils.trimIpAddress(srcIp2));
        assertEquals("192.168.1.1", IpUtils.trimIpAddress(srcIp3));
    }

    @Test
    public void test_getReverseIpMask() {
        int mask1 = 0;
        int mask2 = 23;
        assertEquals("255.255.255.255", IpUtils.getReverseIpMask(mask1));
        assertEquals("0.0.1.255", IpUtils.getReverseIpMask(mask2));
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
    public void test_calculateIpSubnet_ip_mask() {
        String ip1 = "19a.168.1.1";
        String ip2 = "10.167.158.1";
        int mask1 = -1;
        int mask2 = 33;
        int mask3 = 23;
        assertEquals(null, IpUtils.calculateIpSubnet(ip1, mask3));
        assertEquals("0.0.0.0", IpUtils.calculateIpSubnet(ip2, mask1));
        assertEquals(ip2, IpUtils.calculateIpSubnet(ip2, mask2));
        assertEquals("10.167.158.0", IpUtils.calculateIpSubnet(ip2, mask3));
    }

    @Test
    public void test_calculateIpSubnet() throws ServiceException {
        String cidr2 = "192.168.150.111/24";
        String subnet2 = IpUtils.calculateIpSubnet(cidr2);
        assertEquals("192.168.150.0", subnet2);
    }

    @Test(expected = ServiceException.class)
    public void test_calculateIpSubnetNegtive() throws ServiceException {
        String cidr1 = "192.168.150.111/aa";
        IpUtils.calculateIpSubnet(cidr1);
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

    @Test
    public void test_getIPMaskFromCIDR() {
        String cidr1 = null;
        String cidr2 = "";
        String cidr3 = "192.168.150.111/24";
        assertEquals(-1, IpUtils.getIPMaskFromCIDR(cidr1));
        assertEquals(-1, IpUtils.getIPMaskFromCIDR(cidr2));
        assertEquals(24, IpUtils.getIPMaskFromCIDR(cidr3));
    }

    @Test(expected = ParametersException.class)
    public void test_getIPMaskFromCIDRNegtive() {
        String cidr3 = "192.168.150.111/aa";
        IpUtils.getIPMaskFromCIDR(cidr3);
    }

}
