package com.program.centerapi.service;

import java.util.List;

import com.db.support.DbException;

import microservice.online.entity.TbAdminUser;

public interface AdminService {

	public TbAdminUser getAdminUser(int id) throws DbException;
	public List<TbAdminUser> findByUserName(String username) throws DbException;
}
