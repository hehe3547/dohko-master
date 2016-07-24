package com.dohko.app.blxq.mapper;

import com.dohko.core.base.DataMap;

import java.util.List;

public interface ShopGoodMapper {

	List<DataMap> queryLst(DataMap params);
	
	int queryCount(DataMap params);
	
	void add(DataMap params);
	
	void update(DataMap params);
	
	DataMap queryByShopCode(String ShopCode);
	
}
