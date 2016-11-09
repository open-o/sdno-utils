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

/**
 * Result of paged query.<br/>
 * 
 * @param <T> Generic object
 * @author
 * @version SDNO 0.5 2016-5-19
 */
public class PageQueryResult<T> {

    private int total;

    private int pageSize;

    private int totalPageNum;

    private int currentPage;

    private T objects;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     */
    public PageQueryResult() {
        super();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public T getObjects() {
        return objects;
    }

    public void setObjects(T objects) {
        this.objects = objects;
    }

}
