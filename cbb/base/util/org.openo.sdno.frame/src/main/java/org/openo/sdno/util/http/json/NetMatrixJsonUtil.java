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

package org.openo.sdno.util.http.json;

import java.lang.reflect.Field;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON object parsing NetMatrixJsonUtil class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-12
 */
public final class NetMatrixJsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetMatrixJsonUtil.class);

    private NetMatrixJsonUtil() {

    }

    /**
     * Parse data objects defined by map into objects. Container member currently not supported.<br>
     * 
     * @param fieldMap Data objects defined by map. If they are the basic data types, need to be<br>
     *            stored in the corresponding data objects, otherwise there will be exception of<br>
     *            data type mismatch; if they are objects, then they need to be stored in a
     *            nested<br>
     *            Map.<br>
     * @param objType Object type
     * @return Objects which is parsed out
     * @since SDNO 0.5
     */
    public static <T> T parser(Map fieldMap, Class<T> objType) {
        Field[] fields = objType.getDeclaredFields();
        T objt;
        try {
            objt = objType.newInstance();
            for(Field field : fields) {
                if(fieldMap.containsKey(field.getName())) {
                    field.setAccessible(true);
                    Object filedObj = fieldMap.get(field.getName());

                    // If the current field is an object, we need to parse the corresponding sub
                    // map in the map, and then fill in the parsed child object.
                    if(filedObj instanceof Map) {
                        field.set(objt, parser((Map)filedObj, field.getType()));
                    } else {
                        // basic data types
                        field.set(objt, fieldMap.get(field.getName()));

                    }
                }
            }
            return objt;
        } catch(InstantiationException e) {
            LOGGER.warn("parser " + objType.getName() + " failed ,field is wrong or new instance failed.", e);

        } catch(IllegalAccessException e) {
            LOGGER.warn("parser " + objType.getName() + " failed,field is wrong or new instance failed.", e);

        }
        return null;

    }

}
