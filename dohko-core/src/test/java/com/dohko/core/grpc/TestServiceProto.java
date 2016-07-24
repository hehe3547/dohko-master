package com.dohko.core.grpc;

import com.dohko.core.base.DataList;
import com.dohko.core.base.DataMap;
import com.dohko.core.grpc.util.RpcDataUtils;
import com.dohko.core.util.DataUtils;
import io.grpc.protobuf.lite.ProtoLiteUtils;
import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiangbin on 2016/6/17.
 */
public class TestServiceProto {

    public void testToRequest() {

    }

    @Test
    public void testToRpcDataset() throws IOException {
        DataMap dataMap = DataMap.Builder.create();
        for (int i = 0; i < 10; i++) {
            dataMap.put("a" + i, "value" + i);
        }
        DataList dataList = DataList.Builder.create();
        for (int i = 0; i < 10; i++) {
            DataMap r = DataMap.Builder.create();
            r.put("shopID", "shopID" + i);
            r.put("groupID", "groupID" + i);
            r.put("shopName", "shopName" + i);
            r.put("groupName", "groupName" + i);
            dataList.add(r);
        }
        dataMap.put("foodList", dataList);
        RpcDataMap rpcDataMap = RpcDataUtils.toRpcDataMap(dataMap);
        InputStream inputStream = ProtoLiteUtils.marshaller(rpcDataMap).stream(rpcDataMap);

        System.out.println(inputStream.available());
        System.out.println(DataUtils.dataMap2json(dataMap).getBytes().length);

        DataMap newDs = RpcDataUtils.toDataMap(rpcDataMap);
        Assert.assertEquals("value0", newDs.getString("a0"));
        Assert.assertEquals("value8", newDs.getString("a8"));

        Assert.assertEquals("groupName8", newDs.getDataList().get(8).getString("groupName"));
        Assert.assertEquals("groupID7",  newDs.getDataList().get(7).getString("groupID"));
    }
}
