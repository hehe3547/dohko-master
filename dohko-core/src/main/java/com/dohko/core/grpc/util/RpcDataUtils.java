package com.dohko.core.grpc.util;

import com.dohko.core.PlatDictInfo;
import com.dohko.core.base.*;
import com.dohko.core.base.Holder;
import com.dohko.core.grpc.*;
import com.dohko.core.grpc.RpcDataList;
import com.dohko.core.grpc.RpcDataMap;
import com.dohko.core.grpc.RpcListValue;
import com.dohko.core.grpc.RpcRequest;
import com.dohko.core.grpc.RpcResponse;
import com.dohko.core.grpc.RpcValue;

import java.util.*;

public class RpcDataUtils {

	public static Request toRequest(RpcRequest req) {
		String serviceName = req.getService();
		DataMap dataMap = toDataMap(req.getDataMap());
		Holder holder = toHolder(req.getHolder());
		return Request.Builder.create(serviceName, dataMap, holder);
	}

	public static RpcResponse toRpcResponse(Response response) {
		RpcResponse.Builder builder = RpcResponse.newBuilder();
		builder.setSuccess(response.success()).setReturnCode(response.getReturnCode()).setReturnMsg(response.getReturnMessage()).setTraceID(response.getTranceId());
		if (response.getDataMap() != null) {
			builder.setDataMap(toRpcDataMap(response.getDataMap()));
		}
		return builder.build();
	}

	public static RpcRequest toRpcRequest(Request req) {
		RpcRequest.Builder builder = RpcRequest.newBuilder().setService(req.getService());
		if (req.getDataMap() != null) {
			RpcDataMap rpcDataMap = toRpcDataMap(req.getDataMap());
			builder.setDataMap(rpcDataMap);
		}
		if (req.getHolder() != null) {
			builder.setHolder(toRpcHolder(req.getHolder()));
		}
		if (req.getTranceId() != null) {
			builder.setTraceID(req.getTranceId());
		}
		return builder.build();
	}

	public static Response toResponse(RpcResponse res) {
		Response response = Response.Builder.create(res.getTraceID(), res.getReturnCode(), res.getReturnMsg());
		if (res.getDataMap() != null) {
			response.setDataMap(toDataMap(res.getDataMap()));
		}
		return response;
	}

	private static RpcHolder toRpcHolder(Holder holder) {
		RpcHolder.Builder builder = RpcHolder.newBuilder();
		RpcDataMap rpcDataMap = toRpcDataMap(holder.getDataMap());
		builder.setDataMap(rpcDataMap);
		return builder.build();
	}


	private static Holder toHolder(RpcHolder holder) {
		DataMap dataMap = toDataMap(holder.getDataMap());
		return Holder.Builder.create(dataMap);
	}

	public static RpcDataMap toRpcDataMap(DataMap dataMap) {
		Set<String> keySet = dataMap.keySet();
		List<RpcValue> keys = new ArrayList<>();
		List<RpcValue> values = new ArrayList<>();
		for (String key : keySet) {
			keys.add(toRpcDataKey(key));
			RpcValue value = toRpcValue(dataMap, key);
			values.add(value);
		}
		return RpcDataMap.newBuilder().addAllKey(keys).addAllValue(values).build();
	}

	private static RpcDataList toRpcDataList(DataList dataList) {
		List<RpcValue> headers = new ArrayList<>();
		List<RpcListValue> listValues = new ArrayList<>();
		for (int i = 0; i < dataList.size(); i++) {
			DataMap dataMap = dataList.get(i);
			List<RpcValue> value = new ArrayList<>();
			for (String key : dataMap.keySet()) {
				if (i == 0) {
					headers.add(toRpcDataKey(key));
				}
				value.add(toRpcValue(dataMap, key));
			}
			listValues.add(RpcListValue.newBuilder().addAllValue(value).build());
		}
		return RpcDataList.newBuilder().addAllHeader(headers).addAllValue(listValues).build();
	}

	private static RpcValue toRpcDataKey(String key) {
		int index = PlatDictInfo.indexOf(key);
		if (index > -1) {
			return RpcValue.newBuilder().setIntValue(index).build();
		} else {
			return RpcValue.newBuilder().setStringValue(key).build();
		}
	}

	private static RpcValue toRpcValue(DataMap dataMap, String key) {
		DataMap.ValueType valueType = dataMap.getType(key);
		RpcValue.Builder builder = RpcValue.newBuilder();
		if (valueType == DataMap.ValueType.BIG_DECIMAL) {
			builder.setStringValue(dataMap.getBigDecimal(key).toString());
		} else if (valueType == DataMap.ValueType.BIG_INTEGER) {
			builder.setStringValue(dataMap.getBigInteger(key).toString());
		} else if (valueType == DataMap.ValueType.INT) {
			builder.setIntValue(dataMap.getIntValue(key));
		} else if (valueType == DataMap.ValueType.LONG) {
			builder.setLongValue(dataMap.getLongValue(key));
		} else if (valueType == DataMap.ValueType.DOUBLE) {
			builder.setDoubleValue(dataMap.getLongValue(key));
		} else if (valueType == DataMap.ValueType.FLOAT) {
			builder.setFloatValue(dataMap.getFloatValue(key));
		} else if (valueType == DataMap.ValueType.BOOL) {
			builder.setBoolValue(dataMap.getBooleanValue(key));
		} else if (valueType == DataMap.ValueType.STRING) {
			builder.setStringValue(dataMap.getString(key));
		} else if (valueType == DataMap.ValueType.DATA_MAP) {
			builder.setDataMap(toRpcDataMap(dataMap.getDataMap(key)));
		} else if (valueType == DataMap.ValueType.DATA_LIST) {
			builder.setDataList(toRpcDataList(dataMap.getDataList(key)));
		}
		return builder.build();
	}

	public static DataMap toDataMap(RpcDataMap rpcDataMap) {
		DataMap dataMap = DataMap.Builder.create();
		if (rpcDataMap == null) {
			return dataMap;
		}
		for (int i = 0; i < rpcDataMap.getKeyCount(); i++) {
			RpcValue rpcValueKey = rpcDataMap.getKey(i);
			String key = toDataKey(rpcValueKey);
			RpcValue rpcValue = rpcDataMap.getValue(i);
			putDataValue(dataMap, key, rpcValue);
		}
		return dataMap;
	}

	private static DataList toDataList(RpcDataList rpcDataList) {
		DataList dataList = DataList.Builder.create();
		for (int i = 0; i < rpcDataList.getValueCount(); i++) {
			RpcListValue listValue = rpcDataList.getValue(i);
			DataMap dataMap = DataMap.Builder.create();
			for (int j = 0; j < listValue.getValueCount(); j++) {
				RpcValue rpcValue = listValue.getValue(j);
				putDataValue(dataMap, toDataKey(rpcDataList.getHeader(j)), rpcValue);
			}
			dataList.add(dataMap);
		}
		return dataList;
	}

	private static String toDataKey(RpcValue rpcValueKey) {
		if (rpcValueKey.getKindCase() == RpcValue.KindCase.INT_VALUE) {
			return PlatDictInfo.value(rpcValueKey.getIntValue());
		} else {
			return rpcValueKey.getStringValue();
		}
	}
	private static void putDataValue(DataMap dataMap, String key, RpcValue rpcValue) {
		RpcValue.KindCase kindCase = rpcValue.getKindCase();
		if (kindCase == RpcValue.KindCase.INT_VALUE) {
			dataMap.put(key, rpcValue.getIntValue());
		} else if (kindCase == RpcValue.KindCase.BOOL_VALUE) {
			dataMap.put(key, rpcValue.getBoolValue());
		} else if (kindCase == RpcValue.KindCase.DATALIST) {
			dataMap.put(key, toDataList(rpcValue.getDataList()));
		} else if (kindCase == RpcValue.KindCase.DATAMAP) {
			dataMap.put(key, toDataMap(rpcValue.getDataMap()));
		} else if (kindCase == RpcValue.KindCase.DOUBLE_VALUE) {
			dataMap.put(key, rpcValue.getDoubleValue());
		} else if (kindCase == RpcValue.KindCase.FLOAT_VALUE) {
			dataMap.put(key, rpcValue.getFloatValue());
		} else if (kindCase == RpcValue.KindCase.LONG_VALUE) {
			dataMap.put(key, rpcValue.getLongValue());
		} else if (kindCase == RpcValue.KindCase.STRING_VALUE) {
			dataMap.put(key, rpcValue.getStringValue());
		}
	}
	


}
