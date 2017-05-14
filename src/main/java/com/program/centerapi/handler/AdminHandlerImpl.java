package com.program.centerapi.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.rtf.RTFEditorKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.db.support.DbException;
import com.program.centerapi.param.BaseResult;
import com.program.centerapi.param.WxWebParam;
import com.program.centerapi.service.AdminService;

import microservice.online.entity.TbAdminUser;

/**
 * 
 * @author admin
 *
 */
@Service
public class AdminHandlerImpl implements AdminHandler {

	@Autowired(required = true)
	AdminService adminService;

	@Override
	public BaseResult login(WxWebParam param) {
		// TODO Auto-generated method stub
		BaseResult result = new BaseResult();
		result.setSucc(false);
		try {
			String username = param.getUsername();
			String password = param.getPassword();
			if (StringUtils.isEmpty(username)) {
				result.setMesg("用户名为空");
				return result;
			}
			if (StringUtils.isEmpty(password)) {
				result.setMesg("密码为空");
				return result;
			}
			List<TbAdminUser> adminUsers = adminService.findByUserName(username);
			if(adminUsers == null || adminUsers.size() <= 0) {
				result.setMesg("用户名或密码错误");
				return result;
			}
			TbAdminUser adminUser = adminUsers.get(0);
			if (adminUser == null) {
				result.setMesg("用户名或密码错误");
				return result;
			}
			String resPass = adminUser.getPassword();
			if (!resPass.equals(password)) {
				result.setMesg("用户名或密码错误");
				return result;
			}
			JSONObject object = new JSONObject();
			object.put("adminUser", adminUser);
			result.setJsonObject(object);
			result.setSucc(true);
			return result;
		} catch (Exception e) {
			result.setSucc(false);
			result.setMesg(e.getCause().getMessage());
			return result;
		}
	}

	@Override
	public BaseResult addAdmin(WxWebParam param) {
		// TODO Auto-generated method stub
		BaseResult result = new BaseResult();
		result.setSucc(false);
		try {
			String username = param.getUsername();
			String password = param.getPassword();
			String phone = param.getPhone();
			String email = param.getEmail();
			String state = param.getState();
			if(StringUtils.isEmpty(username)) {
				result.setMesg("用户名不能为空");
				return result;
			}
			if(StringUtils.isEmpty(password)) {
				result.setMesg("登陆密码不能为空");
				return result;
			}
			if(StringUtils.isEmpty(phone)) {
				result.setMesg("手机号不能为空");
				return result;
			}
			if(StringUtils.isEmpty(email)) {
				result.setMesg("邮箱不能为空");
				return result;
			}
			if(StringUtils.isEmpty(state)) {
				result.setMesg("未选择是否开通");
				return result;
			}
			TbAdminUser adminUser = new TbAdminUser();
			adminUser.setUsername(username);
			adminUser.setPassword(password);
			adminUser.setPhone(phone);
			adminUser.setEmail(email);
			adminUser.setState(state);
			adminUser.setCrtime(System.currentTimeMillis()/1000 + "");
			TbAdminUser user = adminService.addAdmin(adminUser);
			if(user != null) {
				JSONObject object = new JSONObject();
				object.put("admin", user);
				result.setJsonObject(object);
				result.setSucc(true);
				result.setMesg("添加成功");
			} else {
				result.setMesg("添加失败");
			}
			return result;
		} catch (Exception e) {
			result.setSucc(false);
			result.setMesg(e.getCause().getMessage());
			return result;
		}
	}

	@Override
	public BaseResult adminList(WxWebParam param) {
		// TODO Auto-generated method stub
		BaseResult result = new BaseResult();
		result.setSucc(false);
		try {
			String pageIndex = param.getPageIndex();
			String pageSize = param.getPageSize();
			if(StringUtils.isEmpty(pageIndex)) {
				result.setMesg("查询页数为空");
				return result;
			}
			if(StringUtils.isEmpty(pageSize)) {
				result.setMesg("查询个数为空");
				return result;
			}
			List<Object> adminUsers = adminService.findUserAdmin(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
			int count = adminService.findAdminSize();
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for(int i = 0; i < adminUsers.size(); i++) {
				Object[] map = (Object[])adminUsers.get(i);
				JSONObject object2 = new JSONObject();
				object2.put("id", map[0]);
				object2.put("username", map[1]);
				object2.put("phone", map[2]);
				object2.put("email", map[3]);
				object2.put("crtime", map[4]);
				object2.put("state", map[5]);
				object2.put("rname", map[8] == null ? "" : map[8]);
				jsonArray.add(object2);
			}
			object.put("count", count);
			object.put("list", jsonArray);
			result.setMesg("查询成功");
			result.setSucc(true);
			result.setJsonObject(object);
			return result;
		} catch (Exception e) {
			result.setSucc(false);
			result.setMesg(e.getCause().getMessage());
			return result;
		}
	}

}
