package com.program.centerapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.db.support.DbException;
import com.db.support.GenericServiceImpl;

import microservice.online.entity.TbFans;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class FansServiceImpl extends GenericServiceImpl implements FansService {

	@Override
	public List<TbFans> findFansListPage(int pageIndex, int pageSize) throws DbException {
		String sql = "SELECT * FROM tb_fans limit " + ((pageIndex-1) * pageSize) + "," + pageSize;
		List<TbFans> tbFans = queryDao.findBySqlListMap(sql, new Object[]{});
		return tbFans;
	}

	@Override
	public int findFansSize() throws DbException {
		int count = 0;
		String sql = "SELECT COUNT(0) FROM tb_fans";
		List<Object> objects = queryDao.findBySql(sql, null);
		if(objects.size() > 0) {
			count = Integer.valueOf(objects.get(0) + "");
		}
		return count;
	}

	

}
