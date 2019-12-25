/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package x7.repository.dao;

import x7.core.bean.Criteria;
import x7.core.web.Page;

import java.util.List;
import java.util.concurrent.Callable;

public class PageBuilder {

    public static <T> Page<T>  build(Criteria criteria, List<T> list, Callable<Long> callable) {

        long count = 0;
        int rows = criteria.getRows();
        int page =criteria.getPage();
        if (!criteria.isScroll()) {
            int size = list.size();
            if (page == 0) {
                count = size;
            } else if (size > 0) {
                try {
                    count = callable.call();
                }catch (Exception e){

                }
            }
        }

        Page<T> pagination = new Page<>();
        pagination.setClz(criteria.getClz());
        pagination.setPage(page == 0 ? 1 : page);
        pagination.setRows(rows == 0 ? Integer.MAX_VALUE : rows);
        pagination.setSortList(criteria.getSortList());
        pagination.setScroll(criteria.isScroll());
        pagination.setList(list);
        pagination.setTotalRows(count);

        return pagination;
    }
}
