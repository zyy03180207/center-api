package com.program.centerapi.service;

import java.util.List;

import com.db.support.DbException;

import microservice.online.entity.TbSecqurity;

public interface AuthorService {

	public List<TbSecqurity> findSecqurity() throws DbException;
	public List<Object> findSecqurityByUser(String username) throws DbException;
	public List<TbSecqurity> findByPage(int pageIndex, int pageSize) throws DbException;
	public int findSecquritySize() throws DbException;
}
