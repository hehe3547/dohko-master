package com.dohko.core.app;

import com.dohko.core.base.DataMap;
import com.dohko.core.base.Holder;

/**
 * 用于替换之前的过滤器
 * Created by xiangbin on 2016/6/15.
 */
public interface Filter {

    String RESULT_SERVICE_NAME = "core_resultFilter";

    String doFilterName(String id, DataMap dataMap, Holder holder);
    /**
     * 过滤请求的数据
     * @param id 唯一ID
     * @param dataMap 请求的数据
     * @param holder 请求端的信息
     * @return
     */
    DataMap doReqFilter(String id, DataMap dataMap, Holder holder);

    DataMap doResFilter(String id, DataMap reqDs, DataMap resDs, Holder holder);
}
