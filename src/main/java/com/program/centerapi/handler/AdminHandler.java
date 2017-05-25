package com.program.centerapi.handler;

import com.program.centerapi.param.BaseResult;
import com.program.centerapi.param.WxWebParam;

public interface AdminHandler {
	
	public BaseResult login(WxWebParam param);
	public BaseResult addAdmin(WxWebParam param);
	public BaseResult adminList(WxWebParam param);
	public BaseResult delAdmin(WxWebParam param);
	public BaseResult osAdmin(WxWebParam param);
}
