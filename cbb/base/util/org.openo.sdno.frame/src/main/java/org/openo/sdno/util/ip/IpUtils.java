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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openo.sdno.framework.container.util.IpConfig;
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
            return null;
        }
        String[] splited = cidr.split("/");
        return splited[0];
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
        if(IpConfig.getMinIpAddress().equals(mask)) {
            return 0;
        }
        if(IpConfig.getMaxIpAddress().equals(mask)) {
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
            return IpConfig.getMinIpAddress();
        }
        if(prefix == 32) {
            return IpConfig.getMaxIpAddress();
        }
        char[] chars = new char[32];
        Arrays.fill(chars, '0');
        for(int i = 0; i < prefix; i++) {
            chars[i] = '1';
        }

        long ipvalue = Long.parseLong(String.valueOf(chars), 2);
        return convertLong2Ip(ipvalue);
    }

}
