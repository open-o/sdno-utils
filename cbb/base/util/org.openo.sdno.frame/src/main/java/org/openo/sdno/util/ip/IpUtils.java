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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.ParametersException;
import org.openo.sdno.frame.IPv4Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tool class for IP address processing and calculating.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-28
 */
public class IpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtils.class);

    private static final String MAXIMUM_IP_ADDRESS = "255.255.255.255";

    private static final String MINIMUM_IP_ADDRESS = "0.0.0.0";

    private static final String REGEX =
            "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}/\\d{1,2}";

    private IpUtils() {

    }

    /**
     * Verify the IP address is valid or not.<br>
     * 
     * @param ip IP address
     * @return Returns true when valid, otherwise returns false.
     * @since SDNO 0.5
     */
    public static boolean isValidAddress(String ip) {
        String regx = "[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+";
        Pattern pat = Pattern.compile(regx);
        Matcher mat = pat.matcher(ip);
        if(!mat.matches()) {
            LOGGER.error("isValidAddress ip not match.");
            return false;
        }
        String[] split = ip.split("\\.");
        for(String str : split) {
            int value = Integer.parseInt(str);
            if((value < 0) || (value > 255)) {
                LOGGER.error("isValidAddress value in ip is not valid.");
                return false;
            }
        }
        return true;
    }

    /**
     * Verify the CIDR is valid or not.<br>
     * 
     * @param cidr CIDR
     * @return Returns true when valid, otherwise returns false.
     * @since SDNO 0.5
     */
    public static boolean isValidCidr(String cidr) {
        if(StringUtils.isEmpty(cidr)) {
            LOGGER.error("isValidCidr cidr is empty.");
            return false;
        }
        String[] split = cidr.split(String.valueOf('/'));
        if(split.length != 2) {
            LOGGER.error("isValidCidr split length is error");
            return false;
        }
        String ip = split[0];
        String maskstr = split[1];
        if(!isValidAddress(ip)) {
            LOGGER.error("isValidCidr ip is not valid");
            return false;
        }
        if(!NumberUtils.isDigits(maskstr)) {
            LOGGER.error("isValidCidr num is not digit");
            return false;
        }

        int mask = Integer.valueOf(maskstr);
        if((mask < 0) || (mask > 32)) {
            LOGGER.error("isValidCidr value of num is not valid");
            return false;
        }
        return true;
    }

    /**
     * Check if it is reserved IP, such as 0, 255.<br>
     * 
     * @param ipAddr IP address
     * @return Return true when it is reserved IP, otherwise return false.
     * @since SDNO 0.5
     */
    public static boolean isReservedIp(String ipAddr) {
        long ipvalue = ipToLong(ipAddr);
        long v = ipvalue & 255;
        return (v == 0) || (v == 255);
    }

    /**
     * Check if it is reserved IP, such as 0, 255.<br>
     * 
     * @param ipAddr Decimal IP address
     * @return Return true when it is reserved IP, otherwise return false.
     * @since SDNO 0.5
     */
    public static boolean isReservedIp(long ipAddr) {
        long v = ipAddr & 255;
        return (v == 0) || (v == 255);
    }

    /**
     * Calculate subnet mask.<br>
     * 
     * @param ipsubnet Start IP address
     * @param ipNum IP address number
     * @return Subnet mask
     * @since SDNO 0.5
     */
    public static int getUserNetworkMask(String ipsubnet, int ipNum) {
        int realnum = 0;
        int count = 0;
        long ipvalue = ipToLong(ipsubnet);
        while(realnum < ipNum) {
            if(!isReservedIp(ipvalue)) {
                realnum++;
            }
            ipvalue++;
            count++;
        }
        int userNetworkMask = 0;
        int number = 1;
        while(number < count) {
            number = number * 2;
            userNetworkMask++;
        }
        userNetworkMask = 32 - userNetworkMask;
        return userNetworkMask;
    }

    /**
     * Calculate subnet IP address.<br>
     * 
     * @param minIp Start IP address
     * @param masks Collection of subnet masks
     * @return Subnet IP address
     * @since SDNO 0.5
     */
    public static String getUserSubnet(String minIp, List<Integer> masks) {
        long usedIpNum = 0;

        for(int mask : masks) {
            int dmask = 32 - mask;
            double useripNum = Math.pow(2, dmask);
            usedIpNum += useripNum;
        }
        long ipnum = IPv4Util.ipToInt(minIp) + usedIpNum;
        return longToIp(ipnum);
    }

    /**
     * Calculate reversed IP mask.<br>
     * 
     * @param mask IP mask
     * @return Reversed IP mask
     * @since SDNO 0.5
     */
    public static String getReverseIpMask(int mask) {
        if(mask == 0) {
            return MAXIMUM_IP_ADDRESS;
        }
        int srcMask = 0xffffffff - (0xffffffff << (32 - mask));
        return IpUtils.intToIp(srcMask);
    }

    /**
     * Get IP address from CIDR.<br>
     * 
     * @param cidr Format address, such as 10.10.10.0/24
     * @return IP address
     * @since SDNO 0.5
     */
    public static String getIPFromCIDR(String cidr) {
        if((null == cidr) || "".equals(cidr)) {
            LOGGER.error("getIPFromCIDR cidr is empty.");
            return "";
        }
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(cidr);
        if(!matcher.matches()) {
            LOGGER.error("The cidr's format error.");
            throw new ParametersException("The cidr's format error.");
        }
        String[] splited = cidr.split("/");
        return splited[0];
    }

    /**
     * Get IP mask from CIDR.<br>
     * 
     * @param cidr Format address, such as 10.10.10.0/24
     * @return IP mask
     * @since SDNO 0.5
     */
    public static int getIPMaskFromCIDR(String cidr) {
        if((null == cidr) || "".equals(cidr)) {
            LOGGER.error("getIPMaskFromCIDR cidr is empty.");
            return -1;
        }
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(cidr);
        if(!matcher.matches()) {
            LOGGER.error("The cidr's format error.");
            throw new ParametersException("The cidr's format error.");
        }
        String[] splited = cidr.split("/");
        return Integer.valueOf(splited[splited.length - 1]);
    }

    /**
     * Convert decimal IP to string.<br>
     * 
     * @param ipNum Decimal IP address
     * @return String of IP address
     * @since SDNO 0.5
     */
    public static String longToIp(Long ipNum) {
        return ((ipNum >> 24) & 0xFF) + "." + ((ipNum >> 16) & 0xFF) + "." + ((ipNum >> 8) & 0xFF) + "."
                + (ipNum & 0xFF);
    }

    /**
     * Convert string IP to decimal IP.<br>
     * 
     * @param strip String of IP address
     * @return Decimal IP
     * @since SDNO 0.5
     */
    public static long ipToLong(String strip) {
        if(!isValidAddress(strip)) {
            LOGGER.error("ipToLong ip is not valid.");
            return -1;
        }
        long[] ip = new long[4];
        int position1 = strip.indexOf('.');
        int position2 = strip.indexOf(".", position1 + 1);
        int position3 = strip.indexOf(".", position2 + 1);
        ip[0] = Long.parseLong(strip.substring(0, position1));
        ip[1] = Long.parseLong(strip.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strip.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strip.substring(position3 + 1));
        return (ip[0] * 256 * 256 * 256) + (ip[1] * 256 * 256) + (ip[2] * 256) + ip[3];
    }

    /**
     * Convert string IP to decimal IP.<br>
     * 
     * @param ip String of IP address
     * @return Decimal IP
     * @since SDNO 0.5
     */
    public static long convertIp2Long(String ip) {
        if(!isValidAddress(ip)) {
            LOGGER.error("convertIp2Long ip is not valid.");
            return -1;
        }
        String[] split = ip.split("\\.");
        long part1 = Long.parseLong(split[0]);
        long part2 = Long.parseLong(split[1]);
        long part3 = Long.parseLong(split[2]);
        long part4 = Long.parseLong(split[3]);
        return part4 + (part3 * 256) + (part2 * 65536) + (part1 * 16777216);
    }

    /**
     * Convert decimal IP to string.<br>
     * 
     * @param ip Decimal IP address
     * @return String of IP address
     * @since SDNO 0.5
     */
    public static String convertLong2Ip(long ip) {
        if((ip < 0) || (ip > 4294967295L)) {
            LOGGER.error("convertIp2Long ip is not valid,out of limit.");
            return null;
        }
        long tmpIp = ip;
        long part1 = tmpIp / 16777216;
        tmpIp -= part1 * 16777216;
        long part2 = tmpIp / 65536;
        tmpIp -= part2 * 65536;
        long part3 = tmpIp / 256;
        tmpIp -= part3 * 256;
        long part4 = tmpIp;
        StringBuilder sb = new StringBuilder();
        sb.append(part1);
        sb.append('.');
        sb.append(part2);
        sb.append('.');
        sb.append(part3);
        sb.append('.');
        sb.append(part4);
        return sb.toString();
    }

    /**
     * Convert decimal IP to string.<br>
     * 
     * @param ipInt Decimal IP address
     * @return String of IP address
     * @since SDNO 0.5
     */
    public static String intToIp(int ipInt) {
        return new StringBuilder().append((ipInt >> 24) & 0xff).append('.').append((ipInt >> 16) & 0xff).append('.')
                .append((ipInt >> 8) & 0xff).append('.').append(ipInt & 0xff).toString();
    }

    /**
     * Convert the IP address into an array of bytes.<br>
     * 
     * @param ipAddr String of IP address
     * @return Byte array of IP address
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
        } catch(NumberFormatException e) {
            LOGGER.error("ipAddr is invalid IP", e);
            throw new IllegalArgumentException("ipAddr is invalid IP", e);
        }
    }

    /**
     * Convert byte array of IP address into decimal IP by bit operation.<br>
     * 
     * @param bytes Byte array of IP address
     * @return decimal IP
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
     * Convert string IP to decimal IP.<br>
     * 
     * @param ipAddr String of IP address
     * @return Decimal IP
     * @since SDNO 0.5
     */
    public static int ipToInt(String ipAddr) {
        try {
            return bytesToInt(ipToBytesByReg(ipAddr));
        } catch(IllegalArgumentException e) {
            LOGGER.error("ipAddr is invalid IP", e);
            throw new IllegalArgumentException("ipAddr is invalid IP", e);
        }
    }

    /**
     * Remove the spaces in the IP address.<br>
     * 
     * @param srcIp IP address before trimming
     * @return IP address after trimming
     * @since SDNO 0.5
     */
    public static String trimIpAddress(String srcIp) {
        if(StringUtils.isEmpty(srcIp)) {
            LOGGER.error("trimIpAddress srcIp is empty.");
            return null;
        }
        String ip = srcIp.replaceAll(" ", "");
        if(!isValidAddress(ip)) {
            LOGGER.error("trimIpAddress ip is not valid.");
            return null;
        }
        String[] split = ip.split("\\.");
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.parseInt(split[0]));
        sb.append('.');
        sb.append(Integer.parseInt(split[1]));
        sb.append('.');
        sb.append(Integer.parseInt(split[2]));
        sb.append('.');
        sb.append(Integer.parseInt(split[3]));
        return sb.toString();
    }

    /**
     * Convert IP mask to numbers of mask bits.<br>
     * 
     * @param mask IP mask
     * @return Numbers of mask bits
     * @since SDNO 0.5
     */
    public static int maskToPrefix(String mask) {
        if(!isValidAddress(mask)) {
            LOGGER.error("maskToPrefix ip is not valid.");
            return -1;
        }
        if(MINIMUM_IP_ADDRESS.equals(mask)) {
            return 0;
        }
        if(MAXIMUM_IP_ADDRESS.equals(mask)) {
            return 32;
        }
        long ipvalue = ipToLong(mask);
        for(int i = 0; i < 32; i++) {
            if(((ipvalue >> (31 - i)) & 1) == 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Convert numbers of mask bits to IP mask.<br>
     * 
     * @param prefix Numbers of mask bits
     * @return IP mask
     * @since SDNO 0.5
     */
    public static String prefixToMask(int prefix) {
        if((prefix < 0) || (prefix > 32)) {
            LOGGER.error("prefixToMask num is out of limit.");
            return null;
        }
        if(prefix == 0) {
            return MINIMUM_IP_ADDRESS;
        }
        if(prefix == 32) {
            return MAXIMUM_IP_ADDRESS;
        }
        char[] chars = new char[32];
        Arrays.fill(chars, '0');
        for(int i = 0; i < prefix; i++) {
            chars[i] = '1';
        }

        long ipvalue = Long.parseLong(String.valueOf(chars), 2);
        return convertLong2Ip(ipvalue);
    }

    /**
     * Calculate subnet IP address.<br>
     * 
     * @param ip IP address
     * @param mask Subnet mask
     * @return Subnet IP address
     * @since SDNO 0.5
     */
    public static String calculateIpSubnet(String ip, int mask) {
        if(!isValidAddress(ip)) {
            LOGGER.error("calculateIpSubnet ip is not valid.");
            return null;
        }
        if(mask <= 0) {
            return MINIMUM_IP_ADDRESS;
        }
        if(mask >= 32) {
            return ip;
        }
        long ipvalue = convertIp2Long(ip);
        ipvalue >>= (32 - mask);
        ipvalue <<= (32 - mask);
        return convertLong2Ip(ipvalue);
    }

    /**
     * Calculate subnet IP address.<br>
     * 
     * @param cidr Format address, such as 10.10.10.0/24
     * @return Subnet IP address
     * @since SDNO 0.5
     */
    public static String calculateIpSubnet(String cidr) throws ServiceException {
        if(!isValidCidr(cidr)) {
            LOGGER.error("Invalid CIDR.");
            throw new ServiceException("Invalid CIDR.");
        }
        String subnetIp = getIPFromCIDR(cidr);
        int subnetMask = getIPMaskFromCIDR(cidr);
        return calculateIpSubnet(subnetIp, subnetMask);
    }

}
