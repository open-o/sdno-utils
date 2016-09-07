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

package org.openo.sdno.frame;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openo.sdno.frame.IPv4Util;

public class IPv4UtilTest {

    @Test
    public void ipToBytesByRegTest() {

        // Test +ve: Normal case :10,18,-12,-26
        byte[] arrbytes = {10, 18, -12, -26};
        IPv4Util.ipToBytesByReg("10.18.244.230");

        assertArrayEquals(arrbytes, IPv4Util.ipToBytesByReg("10.18.244.230"));

        // Test -ve: Error case :10.18.244230
        try {
            IPv4Util.ipToBytesByReg("10.18.244230");
            assert (false);
        } catch(IllegalArgumentException e) {
            assert (true);

        }
    }

    @Test
    public void bytesToIpTest() {

        // Test +ve: Normal case :10,18,-12,-26
        byte[] arrbytes = {10, 18, -12, -26};
        String arrIpAddr = IPv4Util.bytesToIp(arrbytes);
        assertEquals(arrIpAddr, "10.18.244.230");
    }

    @Test
    public void bytesToIntTest() {

        // Test +ve: Normal case :10,18,-12,-26
        byte[] arrbytes = {10, 18, -12, -26};
        int iIpAddr = IPv4Util.bytesToInt(arrbytes);
        assertEquals(iIpAddr, 169014502);
    }

    @Test
    public void ipToIntTest() {

        // Test +ve: Normal case :10,18,-12,-26
        String strIpAddr = "10.18.244.230";
        int iIpAddr = IPv4Util.ipToInt(strIpAddr);
        assertEquals(iIpAddr, 169014502);

        // Test -ve: Normal case :10,18,-12,-26
        try {
            strIpAddr = "10.18.244";
            iIpAddr = IPv4Util.ipToInt(strIpAddr);
            assert (false);
        } catch(IllegalArgumentException e) {
            assert (true);
        }

    }

    @Test
    public void intToBytesTest() {

        // Test +ve: Normal case :10,18,-12,-26
        byte[] arrbytes = {10, 18, -12, -26};
        byte[] Tmpbytes = IPv4Util.intToBytes(169014502);
        assertArrayEquals(arrbytes, Tmpbytes);

        // Test -ve: Negative case :
        byte[] tmpbyte = {0, 0, 0, 25};
        byte[] tmparrbyte = IPv4Util.intToBytes(25);
        assertArrayEquals(tmparrbyte, tmpbyte);

    }

    @Test
    public void intToIpTest() {

        // Test +ve: Normal case :10,18,-12,-26
        String ipAddr = IPv4Util.intToIp(169014502);
        assertEquals("10.18.244.230", ipAddr);

        // Test -ve: Negative case :
        ipAddr = IPv4Util.intToIp(25);
        assertEquals("0.0.0.25", ipAddr);

    }

    @Test
    public void getIPIntScopeTest() {

        // Test case : +ve
        int arrint[] = IPv4Util.getIPIntScope("10.18.244.230/8");
        int tmp[] = {167772160, 184549375};
        assertArrayEquals(arrint, tmp);

        // Test: No Mask
        try {
            IPv4Util.getIPIntScope("10.18.244.230/");
            assert (false);
        } catch(IllegalArgumentException e) {
            assert (true);
        }

        // Test: Mask > 31
        try {
            IPv4Util.getIPIntScope("10.18.244.230/32");
            assert (false);
        } catch(IllegalArgumentException e) {
            assert (true);
        }

        // Test: Mask negative
        try {
            IPv4Util.getIPIntScope("10.18.244.230/-1");
            assert (false);
        } catch(IllegalArgumentException e) {
            assert (true);
        }

    }

    @Test
    public void getIPIntScopeTest2() {

        // Test case : +ve
        int arrint[] = IPv4Util.getIPIntScope("10.18.244.230", "255.0.0.0");
        int tmp[] = {167772160, 184549375};
        assertArrayEquals(arrint, tmp);

        // Test: No Mask
        try {
            IPv4Util.getIPIntScope("10.18.244.230", "");
            assert (true);
        } catch(IllegalArgumentException e) {
            assert (false);
        }

        // Test: Mask > 31
        try {
            IPv4Util.getIPIntScope("10.18.244.230", "0.0.0.0");
            assert (true);
        } catch(IllegalArgumentException e) {
            assert (false);
        }

        // Test: Mask negative
        try {
            IPv4Util.getIPIntScope("10.18.244.230", "255.255.255.255");
            assert (true);
        } catch(IllegalArgumentException e) {
            assert (false);
        }

    }

    @Test
    public void ipToLongTest() {
        // Test case : +ve
        long iplong = IPv4Util.ipToLong("10.18.244.230");
        assertEquals(iplong, 169014502);
    }

    @Test
    public void longToIPTest() {
        // Testcase: +ve
        String ipaddr = IPv4Util.longToIP((long)169014502);
        assertEquals(ipaddr, "10.18.244.230");

        // Test case: -ve
        ipaddr = IPv4Util.longToIP((long)-3);
        assertEquals(ipaddr, "255.255.255.253");
    }

    @Test
    public void getIPAddrScopeTest() {
        // Test case: +ve
        String strArray[] = IPv4Util.getIPAddrScope("10.18.244.230/8");
        String strTmpArray[] = {"10.0.0.0", "10.0.0.0"};
        assertArrayEquals(strTmpArray, strArray);

        // Test case: -ve
        try {
            IPv4Util.getIPAddrScope("10.18.244.230/");
        } catch(IllegalArgumentException e) {
            assert (true);
        }

        // Test case: -ve
        try {
            IPv4Util.getIPAddrScope("10.18.244.230/32");
        } catch(IllegalArgumentException e) {
            assert (true);
        }

        // Test case: -ve
        try {
            IPv4Util.getIPAddrScope("10.18.244.230/-1");
        } catch(IllegalArgumentException e) {
            assert (true);
        }

    }

    @Test
    public void getIPAddrScopeTest2() {
        String strArray[] = IPv4Util.getIPStrScope("10.18.244.230", "255.0.0.0");
        String strTmpArray[] = {"10.0.0.0", "10.0.0.0"};
        assertArrayEquals(strTmpArray, strArray);

        // Test case: -ve
        try {
            IPv4Util.getIPStrScope("10.18.244.230", "");
        } catch(IllegalArgumentException e) {
            assert (true);
        }

        // Test case: -ve
        try {
            IPv4Util.getIPStrScope("10.18.244.230", "255.255.255.255");
        } catch(IllegalArgumentException e) {
            assert (true);
        }

        // Test case: -ve
        try {
            IPv4Util.getIPStrScope("10.18.244.230", "0.0.0.0");
        } catch(IllegalArgumentException e) {
            assert (true);
        }

        // Test case: -ve
        try {
            IPv4Util.getIPStrScope("10.18.244.230", "299.299.-1.0");
        } catch(IllegalArgumentException e) {
            assert (true);
        }

    }

}
