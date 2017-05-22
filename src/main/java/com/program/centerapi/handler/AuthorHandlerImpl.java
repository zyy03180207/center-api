package com.program.centerapi.handler;

import java.util.List;

import javax.sound.midi.Sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.program.centerapi.param.BaseResult;
import com.program.centerapi.param.WxWebParam;
import com.program.centerapi.service.AuthorService;

import microservice.online.entity.TbRoleSecqurity;
import microservice.online.entity.TbRoleSecqurityId;
import microservice.online.entity.TbSecqurity;

@Service
public class AuthorHandlerImpl implements AuthorHandler {

	@Autowired
	AuthorService authorService;
	
	@Override
	public BaseResult findSecqurity(WxWebParam param) {
		// TODO Auto-generated method stub
		BaseResult result = new BaseResult();
		result.setSucc(false);
		try {
			String username = param.getUsername();
			String pageIndex = param.getPageIndex();
			String pageSize = param.getPageSize();
			//查询所有的
			if(!StringUtils.isEmpty(username)) {
				List<Object> secqurities = authorService.findSecqurityByUser(username);
				JSONObject object = new JSONObject();
				JSONArray array = new JSONArray();
				for(int i = 0; i < secqurities.size(); i++) {
					Object[] objects = (Object[]) secqurities.get(i);
					JSONObject object2 = new JSONObject();
					object2.put("id", objects[0]);
					object2.put("action", objects[1]);
					object2.put("des", objects[2]);
					object2.put("icon", objects[3]);
					object2.put("ismenu", objects[4]);
					object2.put("menu_name", objects[5]);
					object2.put("pid", objects[6]);
					array.add(object2);
				}
				object.put("list", array);
				result.setJsonObject(object);
				result.setMesg("查询成功");
				result.setSucc(true);
			} else if(!StringUtils.isEmpty(pageIndex) && !StringUtils.isEmpty(pageSize)) {
				List<TbSecqurity> secqurities = authorService.findByPage(Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
				int count = authorService.findSecquritySize();
				JSONObject object = new JSONObject();
				JSONArray array = new JSONArray();
				array.addAll(secqurities);
				object.put("list", array);
				object.put("count", count);
				result.setJsonObject(object);
				result.setMesg("查询成功,分页");
				result.setSucc(true);
			} else {
				List<TbSecqurity> list = authorService.findSecqurity();
				JSONObject object = new JSONObject();
				JSONArray array = new JSONArray();
				array.addAll(list);
				object.put("list", array);
				object.put("count", 0);
				result.setJsonObject(object);
				result.setMesg("查询成功");
				result.setSucc(true);
			}
			return result;
		} catch (Exception e) {
			result.setMesg(e.getCause().getMessage());
			return result;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResult addSecqurity(WxWebParam param) {
		// TODO Auto-generated method stub
		BaseResult result = new BaseResult();
		result.setSucc(false);
		try {
			String action = param.getAction();
			String title = param.getTitle();
			String des = param.getDes();
			String icon = param.getIcon();
			String ismenu = param.getIsmenu();
			String pid = param.getPid();
			if(StringUtils.isEmpty(action)) {
				result.setMesg("连接地址不能为空");
				return result;
			}
			if(StringUtils.isEmpty(title)) {
				result.setMesg("权限名称不能为空");
				return result;
			}
			if(StringUtils.isEmpty(des)) {
				result.setMesg("权限描述不能为空");
				return result;
			}
			if(StringUtils.isEmpty(ismenu)) {
				result.setMesg("请选择是否是菜单");
				return result;
			}
			if(StringUtils.isEmpty(pid)) {
				result.setMesg("请选择上级菜单");
				return result;
			}
			TbSecqurity secqurity = new TbSecqurity();
			secqurity.setAction(action);
			secqurity.setDes(des);
			secqurity.setIcon(icon);
			secqurity.setIsmenu(ismenu);
			secqurity.setMenuName(title);
			secqurity.setPid(Integer.valueOf(pid));
			List<TbSecqurity> secqurities = authorService.findSecqurityByName(title);
			if(secqurities.size() > 0) {
				result.setMesg("权限菜单名称已存在");
				return result;
			}
			TbSecqurity tbSecqurity = authorService.saveSecqurity(secqurity);
			if(tbSecqurity != null) {
				TbSecqurity secqurity2 = authorService.findByTitle(tbSecqurity.getMenuName());
				TbRoleSecqurity roleSecqurity = new TbRoleSecqurity();
				TbRoleSecqurityId roleSecqurityId = new TbRoleSecqurityId();
				roleSecqurityId.setSid(secqurity2.getId());
				roleSecqurityId.setRid(1);
				roleSecqurity.setId(roleSecqurityId);
				authorService.saveSecRole(roleSecqurity);
				JSONObject object = new JSONObject();
				object.put("author", tbSecqurity);
				result.setJsonObject(object);
				result.setSucc(true);
				result.setMesg("权限添加成功");
			} else {
				result.setMesg("权限添加失败");
			}
			return result;
		} catch (Exception e) {
			result.setMesg(e.getCause().getMessage());
			return result;
		}
	}

}
