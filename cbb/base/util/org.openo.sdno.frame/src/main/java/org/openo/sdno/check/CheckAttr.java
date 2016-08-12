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

package org.openo.sdno.check;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Data validation for the attributes<br/>
 * 
 * @author
 * @version SDNO 0.5 13-Apr-2016
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CheckAttr {

    /**
     * Parity Type.
     */
    CheckType type() default CheckType.STRING;

    /**
     * Whether it is necessary.
     */
    boolean required() default false;

    /**
     * Minimum length, indicates the length of the string.
     */
    int min() default 0;

    /**
     * Maximum length, indicates the length of the string.
     */
    int max() default 0;

    /**
     * CRUD rules.
     */
    CRUDType[] crud() default {CRUDType.C, CRUDType.R, CRUDType.U};

    /**
     * Error Messages.
     */
    String error() default "";

    /**
     * Class definition for the enumeration type.
     */
    Class enumCls() default Class.class;

    /**
     * Custom interface.
     */
    Class userDefineChecker() default Class.class;

}
