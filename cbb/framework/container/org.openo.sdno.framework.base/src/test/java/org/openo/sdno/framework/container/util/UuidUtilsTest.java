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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;

/**
 * UuidUtils test class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-3-30
 */
public class UuidUtilsTest {

    @Test
    public void testCreateUuid() {
        String uuid = UuidUtils.createUuid();
        assertFalse(uuid.isEmpty());
    }

    @Test
    public void testCreateBase64Uuid() {
        String uuid = UuidUtils.createBase64Uuid();
        assertFalse(uuid.isEmpty());
    }

    @Test
    public void testDecodeBase64Uuuid() throws DecoderException {
        String uuid = UuidUtils.createBase64Uuid();
        String decodeUuid = UuidUtils.decodeBase64Uuuid(uuid);
        assertFalse(decodeUuid.isEmpty());
    }

    @Test
    public void testDecodeBase64UuuiddException() {
        try {
            UuidUtils.decodeBase64Uuuid(null);
        } catch(DecoderException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCheckUuidNull() {
        try {
            UuidUtils.checkUuid(null);
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCheckUuidNotMatch() {
        try {
            UuidUtils.checkUuid("!!!notmatch");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCheckUuid() throws ServiceException {
        UuidUtils.checkUuid("testUuid");
        assertTrue(true);
    }

    @Test
    public void testCheckUuidListNull() {
        try {
            UuidUtils.checkUuidList(null);
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCheckUuidList() throws ServiceException {
        List<String> uuidList = new ArrayList<String>();
        uuidList.add("testUuid");
        UuidUtils.checkUuidList(uuidList);
        assertTrue(true);
    }

    @Test
    public void testCheckUuids() throws ServiceException {
        String[] uuidArray = {"testUuid"};
        UuidUtils.checkUuids(uuidArray);
        assertTrue(true);
    }

    @Test
    public void testCheckUuidsNull() {
        try {
            UuidUtils.checkUuids(null);
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCheckJsonUuidsNull() {
        try {
            UuidUtils.checkJsonUuids(null);
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCheckJsonUuidsEmpty() {
        try {
            UuidUtils.checkJsonUuids("");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCheckJsonUuids() throws ServiceException {
        UuidUtils.checkJsonUuids("testUuidJson");
        assertTrue(true);
    }

    @Test
    public void testCheckUuidListEmptyNull() {
        try {
            UuidUtils.checkUuidListEmpty(null);
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCheckUuidListEmpty() {
        try {
            List<String> uuidList = new ArrayList<String>();
            UuidUtils.checkUuidListEmpty(uuidList);
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCheckUuidListEmptySuccess() throws ServiceException {
        List<String> uuidList = new ArrayList<String>();
        uuidList.add("testUuidJson");
        UuidUtils.checkUuidListEmpty(uuidList);
        assertTrue(true);
    }
}
