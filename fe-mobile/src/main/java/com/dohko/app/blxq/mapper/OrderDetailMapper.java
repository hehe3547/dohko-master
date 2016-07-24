package com.dohko.app.blxq.mapper;

import java.util.List;
import java.util.Map;

import com.dohko.core.base.DataMap;

public interface OrderDetailMapper {

	List<DataMap> queryLst(DataMap params);
	
	int queryCount(DataMap params);
	
	void add(DataMap params);
	
	void update(DataMap params);
	
	DataMap queryByOrderCode(String OrderCode);
	
}
