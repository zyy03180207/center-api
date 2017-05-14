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

	@Override
	public List<TbRole> findRolePage(int pageIndex, int pageSize) throws DbException {
		// TODO Auto-generated method stub
		String hql = " from TbRole ";
		List<TbRole> roles = queryDao.findByPage(hql, null, pageIndex, pageSize);
		return roles;
	}

	@Override
	public int findRoleSize() throws DbException {
		// TODO Auto-generated method stub
		int count = 0;
		String sql = "select count(0) from tb_role";
		List<Object> objects = queryDao.findBySql(sql, null);
		if(objects.size() > 0) {
			count = Integer.valueOf(objects.get(0) + "");
		}
		return count;
	}

}
