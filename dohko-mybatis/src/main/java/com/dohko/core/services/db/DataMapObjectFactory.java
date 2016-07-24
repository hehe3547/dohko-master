package com.dohko.core.services.db;

import com.dohko.core.base.DataList;
import com.dohko.core.base.DataMap;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xiangbin on 2016/7/15.
 */
public class DataMapObjectFactory extends DefaultObjectFactory {

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        if (type == DataMap.class || type == DataList.class) {
            return (T)new HashMap<String, Object>();
        }
        return super.create(type, constructorArgTypes, constructorArgs);
    }
}
