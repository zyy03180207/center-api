package com.program.centerapi.service;

import java.util.List;

import com.db.support.DbException;

import microservice.online.entity.TbFans;

public interface FansService {
	
	public List<TbFans> findFansList() throws DbException;
}
