package com.dohko.app.blxq.mapper;

import com.dohko.core.base.DataMap;

import java.util.List;

public interface OrderMasterMapper {

	List<DataMap> queryLst(DataMap params);
	
	int queryCount(DataMap params);
	
	void add(DataMap params);
	
	void update(DataMap params);
	
	DataMap queryByOrderCode(String OrderCode);

	String genOrderKey(String dateTime);
	
}
