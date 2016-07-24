package com.dohko.core.app.bean;

import java.util.List;

/**
 * Created by xiangbin on 2016/6/26.
 */
public class RecordsBean extends ListBean {
    //记录数，只有在result=true有作用
    private int count;

    private String key;

    public RecordsBean(List<DataBean> dataBeanList) {
        super(dataBeanList);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
