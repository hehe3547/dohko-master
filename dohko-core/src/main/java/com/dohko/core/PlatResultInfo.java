package com.dohko.core;

import com.dohko.core.base.ResultInfo;

public interface PlatResultInfo {

	//平台错误编码，HSP1 新开关表示平台出错信息 HSP9 业务相当错误代码（已经进行业务执行代码时出错）
	
	ResultInfo SUCCESS = ResultInfo.Builder.create("000", "SUCCESS");
	
	ResultInfo MESSAGE_ERROR = ResultInfo.Builder.create("HSP10002", "FAIL");
	
	ResultInfo SYSTEM_ERROR = ResultInfo.Builder.create("HSP99999", "FAIL");
	
	ResultInfo CONFIG_APPSERVICE = ResultInfo.Builder.create("HSP10003", "没有配置appService");
	
	ResultInfo SERVICE_NOT_FOUND = ResultInfo.Builder.create("HSP10004", "服务不存在");
	
	ResultInfo SUPPORT_METHOD = ResultInfo.Builder.create("HSP10005", "不支持此方法");
	
	ResultInfo SUPPORT_RETURN_TYPE = ResultInfo.Builder.create("HSP10006", "不支持此返回类型");

	ResultInfo SERVICE_NOT_FOUND_REFERER = ResultInfo.Builder.create("HSP10007", "没有查到服务对应的提供者");

	ResultInfo SERVICE_NO_SECURITY = ResultInfo.Builder.create("HSP10008", "没有权限");

	ResultInfo MAPPER_NOT_FOUND = ResultInfo.Builder.create("HSP10009", "{0}类,方法{1},参数{2}不存在");

	ResultInfo VALIDATE_SERVICE_ERROR = ResultInfo.Builder.create("HSP10020", "请求参数错误:{0}");

	ResultInfo VALIDATE_FILTE_ERROR = ResultInfo.Builder.create("HSP10021", "请求参数错误:{0}");
	
	ResultInfo VALIDATE_REQUIRED = ResultInfo.Builder.create("HSP10023", "{0}不能为空");
	
	ResultInfo VALIDATE_INT = ResultInfo.Builder.create("HSP10024", "{0}必须是数字");
	
	ResultInfo VALIDATE_MIN = ResultInfo.Builder.create("HSP10025", "{0}的最小长度应为{1}");
	
	ResultInfo VALIDATE_MAX = ResultInfo.Builder.create("HSP10026", "{0}的最大长度应为{1}");
	
	
}
