package com.program.centerapi.service;

import com.db.support.DbException;

import microservice.online.entity.TbAdminUser;

public interface AdminService {

	public TbAdminUser getAdminUser(int id) throws DbException;
	
}
