package com.program.centerapi.api.wxapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.db.support.DbException;
import com.db.support.GenericDao;
import com.db.support.QueryDao;
import com.program.centerapi.Global;
import com.program.centerapi.handler.AdminHandler;
import com.program.centerapi.handler.FansHandler;
import com.program.centerapi.param.BaseResult;
import com.program.centerapi.param.WxWebParam;
import com.program.centerapi.service.AdminService;
import com.program.centerapi.service.FansService;

import microservice.api.ServiceApiAdapter;
import microservice.api.ServiceApiHelper;
import microservice.api.ServiceParam;
import microservice.api.ServiceResult;
import microservice.online.entity.TbAdminUser;
@Service
@Repository(value = "wxAipHandler")
public class WxApiHandler extends ServiceApiAdapter {

	@Autowired
	private AdminHandler adminHandler;

	@Autowired
	private FansHandler fansHandler;
	
	@Override
	public String execute(String json) {
		// TODO Auto-generated method stub
		ServiceResult result = null;
		try {
			ServiceParam param = ServiceApiHelper.parseParam(json);
			String cmmd = param.getCmmd();
			String hmac1 = param.getHmac();
			String hmac2 = md5(param.getData() + "");
			// 验证签名
			if (!hmac1.equals(hmac2)) {
				result = ServiceResult.createFailResult("接口签名验证失败");
				String retData = JSONObject.toJSONString(result);
				return retData;
			}
			WxWebParam webParam = null;
			// 接口分发
			switch (cmmd) {
			case "tb_login":
				webParam = JSONObject.parseObject(param.getData(), WxWebParam.class);
				result = login(webParam);
				break;
			case "tb_fanslist":
				webParam = JSONObject.parseObject(param.getData(), WxWebParam.class);
				result = fanslist(webParam);
				break;
			default:
				result = ServiceResult.createFailResult("接口暂不支持");
				break;
			}
			if (result.isSucc())
				result.setHmac(md5(result.getData() + Global.KEY));
			String retData = JSONObject.toJSONString(result);
			return retData;
		} catch (Exception e) {
			// TODO: handle exception
			result = ServiceResult.createFailResult("接口处理失败" + e.getCause().getMessage());
			String retData = JSONObject.toJSONString(result);
			return retData;
		}
	}
	/**
	 * 查询粉丝列表
	 * @param webParam
	 * @return
	 */
	private ServiceResult fanslist(WxWebParam webParam) {
		// TODO Auto-generated method stub
		ServiceResult result = new ServiceResult();
		BaseResult mr = fansHandler.findFansList(webParam);
		if(mr.isSucc()) {
			result.setSucc(true);
			result.setData(mr.getJsonArray().toJSONString());
			result.setMesg(mr.getMesg());
		} else {
			result.setMesg(mr.getMesg());
		}
		return result;
	}

	/**
	 * 登陆接口
	 * 
	 * @param webParam
	 * @return
	 */
	private ServiceResult login(WxWebParam webParam) {
		// TODO Auto-generated method stub
		ServiceResult result = new ServiceResult();
		BaseResult mr = adminHandler.login(webParam);
		if (mr.isSucc()) {
			result.setSucc(true);
			result.setData(mr.getJsonObject().toJSONString());
			result.setMesg(mr.getMesg());
		} else {
			result.setMesg(mr.getMesg());
		}
		return result;
	}

}
