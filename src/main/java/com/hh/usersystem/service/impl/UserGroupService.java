package com.hh.usersystem.service.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.MessageException;
import com.hh.usersystem.bean.usersystem.UserGroup;

@Service
public class UserGroupService extends BaseService<UserGroup> {


	@Override
	public UserGroup findObjectById(String id) {
		UserGroup hhXtGroup = super.findObjectById(id);
		return hhXtGroup;
	}

	@Override
	public List<String> deleteTreeByIds(String ids) {
		List<String> deleteIdList = super.deleteTreeByIds(ids);
		return deleteIdList;
	}

	@Override
	public UserGroup saveTree(UserGroup entity) throws MessageException {
		UserGroup hhXtGroup = super.saveTree(entity);
		return hhXtGroup;
	}


}
