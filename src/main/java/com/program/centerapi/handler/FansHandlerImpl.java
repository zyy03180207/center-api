package com.program.centerapi.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.db.support.DbException;
import com.program.centerapi.param.BaseResult;
import com.program.centerapi.param.WxWebParam;
import com.program.centerapi.service.FansService;

import microservice.online.entity.TbFans;
@Service
public class FansHandlerImpl implements FansHandler {

	@Autowired
	private FansService fansService;

	@Override
	public BaseResult findFansList(WxWebParam param) {
		BaseResult result = new BaseResult();
		result.setSucc(false);
		try {
			List<TbFans> tbFans = fansService.findFansListPage(Integer.valueOf(param.getPageIndex()), Integer.valueOf(param.getPageSize()));
			int count = fansService.findFansSize();
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(tbFans);
			object.put("list", jsonArray);
			object.put("count", count);
			result.setJsonObject(object);
			result.setMesg("查询成功");
			result.setSucc(true);
			return result;
		} catch (DbException e) {
			result.setMesg(e.getCause().getMessage());
			return result;
		}
	}

}
