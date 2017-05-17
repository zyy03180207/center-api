package com.program.centerapi.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.program.centerapi.param.BaseResult;
import com.program.centerapi.param.WxWebParam;
import com.program.centerapi.service.AuthorService;

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

}
