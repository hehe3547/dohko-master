package com.dohko.core.services.support;


import com.dohko.core.app.Validator;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.services.annotation.Validate;
import com.dohko.core.util.LogUtils;
import org.junit.Test;

import com.dohko.core.base.DataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataValidatorTest {

	private Logger logger = LoggerFactory.getLogger(DataValidatorTest.class);

	@Test
	public void testValidator() {
		Validate validate = SimpleService.class.getAnnotation(Validate.class);
		ServiceValidator dataValidator = new ServiceValidator();
		DataMap dataMap = DataMap.Builder.create().put("orderID","656565").put("orderKey", "aaa");
		ResultInfo resultInfo = dataValidator.validate(validate, dataMap);
		LogUtils.info(resultInfo, logger);
	}

	@Validate({@Validate.Param(value="orderID", rules={Validator.Rule.INT, Validator.Rule.MIN, Validator.Rule.MAX, Validator.Rule.REQUIRED}, variable = {"3", "5"}),
			@Validate.Param(value="orderKey", rules=Validator.Rule.REQUIRED)})
	public class SimpleService extends BaseServiceSupport {

		private String hostname;
		private String datasource;
		@Override
		public ResultInfo execute(DataMap dataset) {
			logger.info("execute simple service");
			//ResultInfo.错误码
			//return PlatResultInfo.MESSAGE_ERROR;
			//Dataset 数据key-value, list
			//Dataset 数据key-value, list
			//Dataset resDs = Client.Remote.doReqRes("mxb_simpleService", dataset);
			//判断服务是否执行成功
			//if (!resDs.success()) {

			//}
			//继续执行其他的业务
			//执行其中操作
			System.out.print("hostname [" + hostname + "]");
			System.out.print("hostname [" + hostname + "]");
			//return Dataset.Builder.create();
			//DataMap key-value
			return DataMap.Builder.create().put("test", "value")
					.put("hostname", hostname).put("datasource", datasource);
			//返回值修改为dataset,datamap
		}
	}
}
