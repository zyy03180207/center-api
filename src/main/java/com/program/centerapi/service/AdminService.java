package com.program.centerapi.service;

import java.util.List;
import java.util.Map;

import com.db.support.DbException;

import microservice.online.entity.TbAdminUser;
import microservice.online.entity.TbUserRole;
import microservice.online.entity.TbUserRoleId;

public interface AdminService {

	public TbAdminUser getAdminUser(int id) throws DbException;
	public List<TbAdminUser> findByUserName(String username) throws DbException;
	public TbAdminUser addAdmin(TbAdminUser adminUser) throws DbException;
	public List<Object> findUserAdmin(int pageIndex, int pageSize) throws DbException;
	public int findAdminSize() throws DbException;
	public void saveUserRole(TbUserRole tbUserRole) throws DbException;
}
