package com.program.centerapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.db.support.DbException;
import com.db.support.GenericServiceImpl;

import microservice.online.entity.TbRole;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class RoleServiceImpl extends GenericServiceImpl implements RoleService {

	@Override
	public List<TbRole> findRole() throws DbException {
		// TODO Auto-generated method stub
		String hql = " from TbRole ";
		List<TbRole> roles = queryDao.findByHql(hql, null);
		return roles;
	}

}
