package com.dohko.app.blxq.service;

import com.dohko.app.blxq.BlxqResultInfo;
import com.dohko.app.blxq.mapper.ShopBaseInfoMapper;
import com.dohko.app.blxq.mapper.ShopCategoryMapper;
import com.dohko.app.blxq.mapper.ShopGoodMapper;
import com.dohko.core.app.Validator.Rule;
import com.dohko.core.base.DataList;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.services.annotation.AppService;
import com.dohko.core.services.annotation.Validate;
import com.dohko.core.services.support.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 店铺的物品查询
 * Created by xiangbin on 2016/7/17.
 */
@AppService("shop_queryShopGoodInfo")
@Validate(@Validate.Param(value = "shopID", rules = {Rule.REQUIRED, Rule.INT}))
public class ShopGoodInfoQuery extends BaseServiceSupport{
    @Autowired
    private ShopBaseInfoMapper baseInfoMapper;
    @Autowired
    private ShopCategoryMapper categoryMapper;
    @Autowired
    private ShopGoodMapper goodMapper;

    @Override
    public ResultInfo execute(DataMap dataMap) {
        String shopID = dataMap.getString("shopID");
        DataMap shopMap = baseInfoMapper.queryByID(shopID);
        if (shopMap == null) {
            return BlxqResultInfo.SHOPID_NOT_FOUND;
        }
        DataMap params = DataMap.Builder.create().put("shopID", shopID);
        List<DataMap> categoryList = categoryMapper.queryLst(params);
        List<DataMap> goodList = goodMapper.queryLst(params);
        shopMap.put("categoryList", DataList.Builder.create(categoryList));
        shopMap.put("goodList", DataList.Builder.create(goodList));
        return shopMap;
    }
}
