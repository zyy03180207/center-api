package com.program.centerapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.services.server.GenericService;
import com.db.support.DbException;
import com.db.support.GenericServiceImpl;

import microservice.online.entity.TbAdminUser;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class AdminServiceImpl extends GenericServiceImpl implements AdminService {

	public TbAdminUser getAdminUser(int id) throws DbException {
		// TODO Auto-generated method stub
		return genericDao.findById(TbAdminUser.class, id);
	}

	@Override
	public List<TbAdminUser> findByUserName(String username) throws DbException {
		// TODO Auto-generated method stub
		return genericDao.findByHql(" from TbAdminUser where username = ? ", new Object[] { username });
	}

}
