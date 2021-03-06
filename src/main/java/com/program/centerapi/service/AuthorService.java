package com.program.centerapi.service;

import java.util.List;

import com.db.support.DbException;

import microservice.online.entity.TbRoleSecqurity;
import microservice.online.entity.TbSecqurity;

public interface AuthorService {

	public List<TbSecqurity> findSecqurity() throws DbException;
	public List<Object> findSecqurityByUser(String username) throws DbException;
	public List<TbSecqurity> findByPage(int pageIndex, int pageSize) throws DbException;
	public int findSecquritySize() throws DbException;
	public List<TbSecqurity> findSecqurityByName(String title) throws DbException;
	public TbSecqurity saveSecqurity(TbSecqurity secqurity) throws DbException;
	public TbSecqurity findByTitle(String title) throws DbException;
	public void saveSecRole(TbRoleSecqurity roleSecqurity) throws DbException;
}
