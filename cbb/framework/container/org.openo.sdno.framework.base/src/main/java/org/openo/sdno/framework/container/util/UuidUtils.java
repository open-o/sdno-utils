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

package org.openo.sdno.framework.container.util;

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;

import org.openo.baseservice.remoteservice.exception.ServiceException;

/**
 * Utility class over UUID.<br/>
 * 
 * @author
 * @version SDNO 0.5 26-Mar-2016
 */
public class UuidUtils {

    /**
     * Error code for parameter validation failure.
     */
    private static final String ERRORCODE_PARA_ERR = "1032004";

    /**
     * Error code for a bad request.
     */
    public static final int BAD_REQUEST = 400;

    /**
     * Parameter error General error code, used primarily for background and interface error.
     */
    public static final String INVALID_INPUT_PARAM = "1000006";

    private UuidUtils() {
    }

    /**
     * Create an unique UUID.<br/>
     * 
     * @return UUID
     * @since SDNO 0.5
     */
    public static String createUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Create BASE64 compressed id.<br/>
     * 
     * @return BASE64 compressed id
     * @since SDNO 0.5
     */
    public static String createBase64Uuid() {
        UUID uuid = UUID.randomUUID();
        byte[] most = byteArrayFromLong(uuid.getMostSignificantBits());
        byte[] least = byteArrayFromLong(uuid.getLeastSignificantBits());

        byte[] uuidBytes = new byte[16];
        System.arraycopy(most, 0, uuidBytes, 0, 8);
        System.arraycopy(least, 0, uuidBytes, 8, 8);

        return Base64.encodeBase64URLSafeString(uuidBytes);
    }

    /**
     * Base64 decoded uuid. format: a12e7652-4b11-4c28-8158-82f4082b3e2b.<br/>
     * 
     * @param base64uuid UUID to be decoded
     * @return UUID UUTID decoded
     * @throws DecoderException if the uuid bytes are null
     * @since SDNO 0.5
     */
    public static String decodeBase64Uuuid(String base64uuid) throws DecoderException {
        byte[] uuidBytes = new byte[16];
        uuidBytes = Base64.decodeBase64(base64uuid);

        if(uuidBytes == null) {
            throw new DecoderException("decodeBase64 failed.");
        }

        return getUuidForm(uuidBytes);
    }

    private static byte[] byteArrayFromLong(long l) {
        byte[] out = new byte[8];
        longIntoByteArray(l, 0, out);

        return out;
    }

    private static void longIntoByteArray(long l, int offset, byte[] bytes) {
        bytes[offset + 0] = (byte)((l >>> 56) & 0xFF);
        bytes[offset + 1] = (byte)((l >>> 48) & 0xFF);
        bytes[offset + 2] = (byte)((l >>> 40) & 0xFF);
        bytes[offset + 3] = (byte)((l >>> 32) & 0xFF);
        bytes[offset + 4] = (byte)((l >>> 24) & 0xFF);
        bytes[offset + 5] = (byte)((l >>> 16) & 0xFF);
        bytes[offset + 6] = (byte)((l >>> 8) & 0xFF);
        bytes[offset + 7] = (byte)((l >>> 0) & 0xFF);
    }

    /**
     * Marked UUID generated string (36 identifiable characters) from the data in the form:
     * a12e7652-4b11-4c28-8158-82f4082b3e2b.<br/>
     * 
     * @param data input byte
     * @return UUID String
     * @since SDNO 0.5
     */
    private static String getUuidForm(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16;

        for(int i = 0; i < 8; i++) {
            msb = (msb << 8) | (data[i] & 0xff);
        }

        for(int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }

        UUID id = new UUID(msb, lsb);

        return id.toString();
    }

    /**
     * Check whether the UUID is valid.<br/>
     * 
     * @param uuid input
     * @throws ServiceException if the input uuid is invalid
     * @since SDNO 0.5
     */
    public static void checkUuid(String uuid) throws ServiceException {
        if(uuid == null) {
            ServiceException e = new ServiceException(ERRORCODE_PARA_ERR, "UUID can not be empty");
            e.setHttpCode(BAD_REQUEST);
            throw e;
        }

        if(!uuid.matches("[a-zA-Z0-9\\-\\_]{1,36}")) {
            ServiceException e = new ServiceException(ERRORCODE_PARA_ERR, "UUID is not support: " + uuid);
            e.setHttpCode(BAD_REQUEST);
            throw e;
        }
    }

    /**
     * Check whether the list of UUID(s) are valid.<br/>
     * 
     * @param uuidList input
     * @throws ServiceException if the input is invalid
     * @since SDNO 0.5
     */
    public static void checkUuidList(List<String> uuidList) throws ServiceException {
        if(uuidList == null) {
            ServiceException e = new ServiceException(ERRORCODE_PARA_ERR, "UUID list can not be empty");
            e.setHttpCode(BAD_REQUEST);
            throw e;
        }

        for(String uuid : uuidList) {
            checkUuid(uuid);
        }
    }

    /**
     * Check whether the array of UUID(s) are valid.<br/>
     * 
     * @param uuids input array of uuids
     * @throws ServiceException if the input is invalid
     * @since SDNO 0.5
     */
    public static void checkUuids(String[] uuids) throws ServiceException {
        if(uuids == null) {
            ServiceException e = new ServiceException(ERRORCODE_PARA_ERR, "UUID array can not be empty");
            e.setHttpCode(BAD_REQUEST);
            throw e;
        }

        for(String uuid : uuids) {
            checkUuid(uuid);
        }
    }

    /**
     * Check whether the array of UUID is valid.<br/>
     * 
     * @param uuids input
     * @throws ServiceException if the input is invalid
     * @since SDNO 0.5
     */
    public static void checkJsonUuids(String uuids) throws ServiceException {
        if((uuids == null) || "".equals(uuids.trim())) {
            ServiceException e = new ServiceException(INVALID_INPUT_PARAM, "UUID array can not be empty");
            e.setHttpCode(BAD_REQUEST);
            throw e;
        }
    }

    /**
     * Check whether the list of UUID(s) are valid.<br/>
     * 
     * @param uuidList list of uuids
     * @throws ServiceException if the input is invalid
     * @since SDNO 0.5
     */
    public static void checkUuidListEmpty(List<String> uuidList) throws ServiceException {
        if((null == uuidList) || uuidList.isEmpty()) {
            ServiceException e = new ServiceException(INVALID_INPUT_PARAM, "UUID array can not be empty");
            e.setHttpCode(BAD_REQUEST);
            throw e;
        }
    }
}
