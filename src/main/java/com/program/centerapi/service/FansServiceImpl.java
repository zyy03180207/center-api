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
	public List<TbFans> findFansList() throws DbException {
		String sql = "SELECT * FROM tb_fans";
		List<TbFans> tbFans = queryDao.findBySqlListMap(sql, new Object[]{});
		return tbFans;
	}


}
