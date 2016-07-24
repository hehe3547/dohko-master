package com.dohko.core.app.bean;

import java.util.Map;

public class DataSetBean {
	private PropsBean props;
	private Map<String, RecordsBean> records;

	public PropsBean getProps() {
		return props;
	}

	public void setProps(PropsBean props) {
		this.props = props;
	}

	public Map<String, RecordsBean> getRecords() {
		return records;
	}

	public void setRecords(Map<String, RecordsBean> records) {
		this.records = records;
	}
}
