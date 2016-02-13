package com.hh.usersystem.service.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.MessageException;
import com.hh.usersystem.bean.usersystem.UsGroup;

@Service
public class UserGroupService extends BaseService<UsGroup> {


	@Override
	public UsGroup findObjectById(String id) {
		UsGroup hhXtGroup = super.findObjectById(id);
		return hhXtGroup;
	}

	@Override
	public List<String> deleteTreeByIds(String ids) {
		List<String> deleteIdList = super.deleteTreeByIds(ids);
		return deleteIdList;
	}

	@Override
	public UsGroup saveTree(UsGroup entity) throws MessageException {
		UsGroup hhXtGroup = super.saveTree(entity);
		return hhXtGroup;
	}


}
