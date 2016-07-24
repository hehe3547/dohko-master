package com.dohko.core.app;

import com.dohko.core.base.DataMap;
import com.dohko.core.base.Holder;

/**
 * 用于替换之前的事件处理
 * Created by xiangbin on 2016/6/20.
 */
public interface Event {

    void doReqEvent(String service, DataMap dataMap, Holder holder);

    void doResEvent(String service, DataMap reqData, DataMap resDat, Holder holder);
}
