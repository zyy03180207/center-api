package com.program.centerapi.service;

import java.util.List;

import com.db.support.DbException;

import microservice.online.entity.TbRole;

public interface RoleService {

	public List<TbRole> findRole() throws DbException;
	
}
