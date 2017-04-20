package com.program.centerapi;

import microservice.api.ServiceApi;

import org.springframework.remoting.caucho.HessianProxyFactoryBean;

public class ApiHessianProxy extends HessianProxyFactoryBean{

	@Override
	public void afterPropertiesSet() {
		this.setServiceInterface(ServiceApi.class);
		this.setConnectTimeout(15*1000);
		this.setReadTimeout(30*1000);
		super.afterPropertiesSet();
	}
	
	
}
