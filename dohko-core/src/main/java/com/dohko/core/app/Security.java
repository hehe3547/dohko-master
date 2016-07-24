package com.dohko.core.app;

import com.dohko.core.base.DataMap;
import com.dohko.core.base.Holder;
import com.dohko.core.base.ResultInfo;

/**
 * Created by xiangbin on 2016/6/25.
 */
public interface Security {

    ResultInfo validate(String id, DataMap dataMap, Holder holder);
}
