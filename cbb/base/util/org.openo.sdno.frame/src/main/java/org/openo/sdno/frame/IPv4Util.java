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

package org.openo.sdno.frame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IPv4 address utility functions.<br>
 * 
 * @author
 * @version SDNO 0.5 13-Apr-2016
 */
public final class IPv4Util {

    private static final int INADDRSZ = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(IPv4Util.class);

    private IPv4Util() {

    }

    /**
     * Convert IP address to bytes using regular expression <br>
     * 
     * @param ipAddr - IP address as string
     * @return IP address in byte format
     * @since SDNO 0.5
     */
    public static byte[] ipToBytesByReg(String ipAddr) {
        byte[] ret = new byte[4];
        try {
            String[] ipArr = ipAddr.split("\\.");
            ret[0] = (byte)(Integer.parseInt(ipArr[0]) & 0xFF);
            ret[1] = (byte)(Integer.parseInt(ipArr[1]) & 0xFF);
            ret[2] = (byte)(Integer.parseInt(ipArr[2]) & 0xFF);
            ret[3] = (byte)(Integer.parseInt(ipArr[3]) & 0xFF);

            return ret;
        } catch(Exception e) {
            LOGGER.error("ipAddr is invalid IP", e);
            throw new IllegalArgumentException("ipAddr is invalid IP", e);
        }
    }

    /**
     * Convert bytes to IP Address<br>
     * 
     * @param bytes - IP Address as bytes
     * @return IPAddress - IP Address
     * @since SDNO 0.5
     */
    public static String bytesToIp(byte[] bytes) {
        return new StringBuffer().append(bytes[0] & 0xFF).append('.').append(bytes[1] & 0xFF).append('.')
                .append(bytes[2] & 0xFF).append('.').append(bytes[3] & 0xFF).toString();
    }

    /**
     * Convert bytes to integer <br>
     * 
     * @param bytes - IP address in bytes
     * @return int - IP address as integer
     * @since SDNO 0.5
     */
    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[3] & 0xFF;

        addr |= ((bytes[2] << 8) & 0xFF00);
        addr |= ((bytes[1] << 16) & 0xFF0000);
        addr |= ((bytes[0] << 24) & 0xFF000000);

        return addr;
    }

    /**
     * Convert IP address to integer <br>
     * 
     * @param ipAddr - IP address as string
     * @return int -IP address as integer
     * @since SDNO 0.5
     */
    public static int ipToInt(String ipAddr) {
        try {
            return bytesToInt(ipToBytesByReg(ipAddr));
        } catch(Exception e) {
            LOGGER.error("ipAddr is invalid IP", e);
            throw new IllegalArgumentException("ipAddr is invalid IP", e);
        }
    }

    /**
     * Convert integer to bytes<br>
     * 
     * @param ipInt - IP address as integer
     * @return byte - format of IP address
     * @since SDNO 0.5
     */
    public static byte[] intToBytes(int ipInt) {
        byte[] ipAddr = new byte[INADDRSZ];

        ipAddr[0] = (byte)((ipInt >>> 24) & 0xFF);
        ipAddr[1] = (byte)((ipInt >>> 16) & 0xFF);
        ipAddr[2] = (byte)((ipInt >>> 8) & 0xFF);
        ipAddr[3] = (byte)(ipInt & 0xFF);

        return ipAddr;
    }

    /**
     * Convert integer to IP address <br>
     * 
     * @param ipInt - IP Address as integer
     * @return String - IP address as string
     * @since SDNO 0.5
     */
    public static String intToIp(int ipInt) {
        return new StringBuilder().append((ipInt >> 24) & 0xff).append('.').append((ipInt >> 16) & 0xff).append('.')
                .append((ipInt >> 8) & 0xff).append('.').append(ipInt & 0xff).toString();
    }

    /**
     * Based on IP address and scope determine the netip and host scope <br>
     * 
     * @param ipAndMask - X.X.X.X/32
     * @return array of int arrint[0] = netip, arrint[1] = host scope
     * @since SDNO 0.5
     */
    public static int[] getIPIntScope(String ipAndMask) {
        String[] ipArr = ipAndMask.split("/");
        if(ipArr.length != 2) {
            LOGGER.error("invalid ipAndMask.");
            throw new IllegalArgumentException("invalid ipAndMask.");
        }

        int netMask = Integer.valueOf(ipArr[1].trim());
        if((netMask < 0) || (netMask > 31)) {
            LOGGER.error("invalid ipAndMask.");
            throw new IllegalArgumentException("invalid ipAndMask.");
        }

        int ipInt = IPv4Util.ipToInt(ipArr[0]);
        int netIp = ipInt & (0xFFFFFFFF << (32 - netMask));
        int hostScope = 0xFFFFFFFF >>> netMask;

        return new int[] {netIp, netIp + hostScope};
    }

    /**
     * Based on IP address and scope determine the netip and host scope <br>
     * <br>
     * 
     * @param ipAddr - X.X.X.X
     * @param mask - mask in terms of IP Address - 255.255.255.0
     * @return array of integer arrint[0] = netip, arrint[1] = host scope
     * @since SDNO 0.5
     */
    public static int[] getIPIntScope(String ipAddr, String mask) {
        int ipInt;
        int netMaskInt = 0;
        int ipcount = 0;
        try {
            ipInt = IPv4Util.ipToInt(ipAddr);
            if((null == mask) || "".equals(mask)) {
                LOGGER.error("getIPIntScope mask is empty.");
                return new int[] {ipInt, ipInt};
            }
            netMaskInt = IPv4Util.ipToInt(mask);
            // 255.255.255.255
            ipcount = IPv4Util.ipToInt("255.255." + "255.255") - netMaskInt;
            int netIp = ipInt & netMaskInt;
            int hostScope = netIp + ipcount;

            return new int[] {netIp, hostScope};
        } catch(Exception e) {
            LOGGER.error("invalid ip scope express.", e);
            throw new IllegalArgumentException("invalid ip scope express.", e);
        }
    }

    /**
     * Get scope of IP address<br>
     * 
     * @param ipAndMask - X.X.X.X/32
     * @return Array of String - arrString[0] - netIP arrString[1] - host scope
     * @since SDNO 0.5
     */
    public static String[] getIPAddrScope(String ipAndMask) {
        int[] ipIntArr = IPv4Util.getIPIntScope(ipAndMask);
        return new String[] {IPv4Util.intToIp(ipIntArr[0]), IPv4Util.intToIp(ipIntArr[0])};
    }

    /**
     * Get scope of IP address<br>
     * 
     * @param ipAddr - X.X.X.X
     * @param mask - like 255.255.255.0
     * @return Array of String - arrString[0] - netIP arrString[1] - host scope
     * @since SDNO 0.5
     */
    public static String[] getIPStrScope(String ipAddr, String mask) {
        int[] ipIntArr = IPv4Util.getIPIntScope(ipAddr, mask);
        return new String[] {IPv4Util.intToIp(ipIntArr[0]), IPv4Util.intToIp(ipIntArr[0])};
    }

    /**
     * Convert IP address to long<br>
     * 
     * @param strip - IP address
     * @return long value of IP address
     * @since SDNO 0.5
     */
    public static long ipToLong(String strip) {
        long[] ip = new long[4];
        int position1 = strip.indexOf('.');
        int position2 = strip.indexOf('.', position1 + 1);
        int position3 = strip.indexOf('.', position2 + 1);

        ip[0] = Long.parseLong(strip.substring(0, position1));
        ip[1] = Long.parseLong(strip.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strip.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strip.substring(position3 + 1));

        return (ip[0] * 256 * 256 * 256) + (ip[1] * 256 * 256) + (ip[2] * 256) + ip[3];
    }

    /**
     * Convert long to IP address <br>
     * 
     * @param ipNum - long value of IP address
     * @return - String - IP address
     * @since SDNO 0.5
     */
    public static String longToIP(Long ipNum) {
        return ((ipNum >> 24) & 0xFF) + "." + ((ipNum >> 16) & 0xFF) + "." + ((ipNum >> 8) & 0xFF) + "."
                + (ipNum & 0xFF);
    }
}
