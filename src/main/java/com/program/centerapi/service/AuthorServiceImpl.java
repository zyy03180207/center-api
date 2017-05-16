package com.program.centerapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.db.support.DbException;
import com.db.support.GenericServiceImpl;

import microservice.online.entity.TbSecqurity;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class AuthorServiceImpl extends GenericServiceImpl implements AuthorService {

	@Override
	public List<Object> findSecqurityByUser(String username) throws DbException {
		// TODO Auto-generated method stub
		String sql = "SELECT s.id AS 'id',s.action AS 'action',s.des AS 'des',s.icon AS 'icon',s.ismenu AS 'ismenu',s.menu_name AS 'menu_name',s.pid AS 'pid' FROM tb_admin_user u LEFT JOIN tb_user_role ur ON u.id = ur.uid LEFT JOIN tb_role r ON ur.rid = r.id LEFT JOIN tb_role_secqurity rs ON r.id = rs.rid LEFT JOIN tb_secqurity s ON s.id = rs.sid WHERE u.username = ?";
		Object[] params = {username};
		return queryDao.findBySql(sql, params);
	}

	@Override
	public List<TbSecqurity> findSecqurity() throws DbException {
		// TODO Auto-generated method stub
		String sql = "SELECT s.id AS 'id',s.action AS 'action',s.des AS 'des',s.icon AS 'icon',s.ismenu AS 'ismenu',s.menu_name AS 'menu_name',s.pid AS 'pid' FROM tb_admin_user u LEFT JOIN tb_user_role ur ON u.id = ur.uid LEFT JOIN tb_role r ON ur.rid = r.id LEFT JOIN tb_role_secqurity rs ON r.id = rs.rid LEFT JOIN tb_secqurity s ON s.id = rs.sid";
		return queryDao.findBySql(sql, TbSecqurity.class, null);
	}
	
	
}
