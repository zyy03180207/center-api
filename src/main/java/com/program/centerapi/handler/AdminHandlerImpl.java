package com.program.centerapi.handler;

import java.util.List;

import javax.swing.text.rtf.RTFEditorKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

}
