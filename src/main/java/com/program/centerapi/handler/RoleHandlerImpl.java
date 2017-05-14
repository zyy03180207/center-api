package com.program.centerapi.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.program.centerapi.param.BaseResult;
import com.program.centerapi.param.WxWebParam;
import com.program.centerapi.service.RoleService;

import microservice.online.entity.TbRole;

@Service
public class RoleHandlerImpl implements RoleHandler {

	@Autowired
	RoleService roleService;
	
	@Override
	public BaseResult findRoleList(WxWebParam param) {
		// TODO Auto-generated method stub
		BaseResult result = new BaseResult();
		result.setSucc(false);
		try {
			String pageIndex = param.getPageIndex();
			String pageSize = param.getPageSize();
			List<TbRole> roles = null;
			int count = 0;
			JSONObject object = new JSONObject();
			JSONArray array = new JSONArray();
			if(StringUtils.isEmpty(pageIndex) && StringUtils.isEmpty(pageSize)) {
				roles = roleService.findRole();
			} else {
				roles = roleService.findRolePage(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
				count = roleService.findRoleSize();
				object.put("count", count);
			}
			array.addAll(roles);
			object.put("list", array);
			result.setJsonObject(object);
			result.setSucc(true);
			result.setMesg("查询成功");
			return result;
		} catch (Exception e) {
			result.setMesg(e.getCause().getMessage());
			return result;
		}
	}

}
