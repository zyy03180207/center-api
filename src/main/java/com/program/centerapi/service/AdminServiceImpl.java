package com.program.centerapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.services.server.GenericService;
import com.db.support.DbException;
import com.db.support.GenericServiceImpl;

import microservice.online.entity.TbAdminUser;
import microservice.online.entity.TbUserRole;
import microservice.online.entity.TbUserRoleId;

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

	@Override
	public TbAdminUser addAdmin(TbAdminUser adminUser) throws DbException {
		// TODO Auto-generated method stub
		return genericDao.save(adminUser);
	}

	@Override
	public List<Object> findUserAdmin(int pageIndex, int pageSize) throws DbException {
		// TODO Auto-generated method stub
		String sql = "SELECT a.id as 'id',a.username as 'username',a.phone as 'phone',a.email as 'email',a.crtime as 'crtime',a.state as 'state',ur.rid as 'rid',ur.uid as 'uid',r.name as 'rname' FROM tb_admin_user a LEFT JOIN tb_user_role ur on a.id = ur.uid LEFT JOIN tb_role r on r.id = ur.rid";
		List<Object> adminUsers = queryDao.findByPageForSql(sql, null, pageIndex, pageSize);
		return adminUsers;
	}

	@Override
	public int findAdminSize() throws DbException {
		// TODO Auto-generated method stub
		int count = 0;
		String sql = "select count(0) from tb_admin_user";
		List<TbAdminUser> list = queryDao.findBySql(sql, null);
		if(list.size() > 0) {
			count = Integer.valueOf(list.get(0) + "");
		}
		return count;
	}

	@Override
	public void saveUserRole(TbUserRole tbUserRole) throws DbException {
		// TODO Auto-generated method stub
		genericDao.save(tbUserRole);
	}

	@Override
	public void delAdmin(int id) throws DbException {
		// TODO Auto-generated method stub
		genericDao.delete(TbAdminUser.class, id);
	}

	@Override
	public void osAdmin(TbAdminUser adminUser) throws DbException {
		// TODO Auto-generated method stub
		genericDao.update(adminUser);
	}
	
}
