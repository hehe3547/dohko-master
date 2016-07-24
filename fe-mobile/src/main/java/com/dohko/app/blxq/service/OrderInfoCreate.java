package com.dohko.app.blxq.service;

import com.dohko.app.blxq.mapper.OrderDetailMapper;
import com.dohko.app.blxq.mapper.OrderMasterMapper;
import com.dohko.app.blxq.mapper.ShopBaseInfoMapper;
import com.dohko.app.blxq.mapper.ShopGoodMapper;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.services.annotation.AppService;
import com.dohko.core.services.support.BaseServiceSupport;
import com.dohko.core.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by xiangbin on 2016/7/17.
 */
@AppService(value = "order_createOrderInfo", transaction = true)
public class OrderInfoCreate extends BaseServiceSupport {

    @Autowired
    private ShopGoodMapper goodMapper;
    @Autowired
    private ShopBaseInfoMapper baseInfoMapper;
    @Autowired
    private OrderMasterMapper masterMapper;
    @Autowired
    private OrderDetailMapper detailMapper;

    @Override
    public ResultInfo execute(DataMap dataMap) {
        String shopID = dataMap.getString("shopID");
        DataMap shopInfo = baseInfoMapper.queryByID(shopID);
        List<DataMap> detailDataList = dataMap.getDataList("detailList").toList();
        DataMap orderMaster = DataMap.Builder.create();
        String orderKey = masterMapper.genOrderKey(DateUtils.getCurrentDate());
        List<DataMap> googList = goodMapper.queryLst(DataMap.Builder.create().put("shopID", shopID));
        orderMaster.put("orderKey", orderKey)
                .put("shopCode", shopInfo.getString("shopCode"))
                .put("shopName", shopInfo.getString("shopName"))
                .put("receiverName", shopInfo.getString("receiverName", ""))
                .put("payType", "1");
        BigDecimal orderTotal = BigDecimal.ZERO;
        BigDecimal goodTotal = BigDecimal.ZERO;
        for (DataMap detailDataMap : detailDataList) {
            String goodCode = detailDataMap.getString("goodCode");
            DataMap goodData = findGood(goodCode, googList);
            if (goodData == null) {
                error("not fond goodCode [" + goodCode + "]");
                continue;
            }
            BigDecimal price = goodData.getBigDecimal("price");
            BigDecimal prePrice = goodData.getBigDecimal("prePrice");
            orderTotal = orderTotal.add(price.multiply(detailDataMap.getBigDecimal("number")));
            goodTotal = goodTotal.add(prePrice.multiply(detailDataMap.getBigDecimal("number")));
            detailDataMap.put("orderKey", orderKey)
                    .put("goodName", goodData.getString("goodName")).put("price", goodData.getString("price"))
                    .put("prePrice",goodData.getString("prePrice"));
            detailMapper.add(detailDataMap);
        }

        orderMaster.put("orderTotal", orderTotal).put("goodTotal", goodTotal);
        masterMapper.add(orderMaster);
        return dataMap.put("orderTotal", orderKey);
    }


    private DataMap findGood(String goodCode, List<DataMap> goodList) {
        for (DataMap dataMap : goodList) {
            if (goodCode.equals(dataMap.getString("goodCode"))) {
                return dataMap;
            }
        }
        return null;
    }

}
