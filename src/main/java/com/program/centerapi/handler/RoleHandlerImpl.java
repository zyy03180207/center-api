package com.program.centerapi.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResult addRole(WxWebParam param) {
		// TODO Auto-generated method stub
		BaseResult result = new BaseResult();
		result.setSucc(false);
		try {
			String name = param.getName();
			String introduce = param.getIntroduce();
			String state = param.getState();
			if(StringUtils.isEmpty(name)) {
				result.setMesg("角色名不能为空");
				return result;
			}
			if(StringUtils.isEmpty(introduce)) {
				result.setMesg("角色描述不能为空");
				return result;
			}
			if(StringUtils.isEmpty(state)) {
				result.setMesg("角色状态不能为空");
				return result;
			}
			TbRole role = roleService.findRoleByName(name);
			if(role != null) {
				result.setMesg("角色名称已存在");
				return result;
			}
			TbRole tbRole = new TbRole();
			tbRole.setName(name);
			tbRole.setIntroduce(introduce);
			tbRole.setState(state);
			TbRole tbRole2 = roleService.saveRole(tbRole);
			JSONObject object = new JSONObject();
			object.put("role", tbRole2);
			result.setJsonObject(object);
			result.setSucc(true);
			result.setMesg("添加成功");
			return result;
		} catch (Exception e) {
			result.setMesg(e.getCause().getMessage());
			return result;
		}
	}

}
