package com.program.centerapi.handler;

import com.db.support.DbException;
import com.program.centerapi.param.BaseResult;
import com.program.centerapi.param.WxWebParam;

public interface AdminHandler {
	
	public BaseResult login(WxWebParam param);
	
}
