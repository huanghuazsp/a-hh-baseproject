package com.hh.usersystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.bean.usersystem.UsGroup;

@Service
public class GroupService extends BaseService<UsGroup> {

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
	public UsGroup save(UsGroup entity) throws MessageException {
		UsGroup hhXtGroup = super.saveTree(entity);
		return hhXtGroup;
	}

	public PagingData<UsGroup> queryPagingData(UsGroup object,
			String groups, PageRange pageRange) {
		ParamInf hqlParamList = ParamFactory.getParamHb();
		if (!Check.isEmpty(object.getText())) {
			hqlParamList.like("text", object.getText());
		}
		if (!Check.isEmpty(groups)) {
			hqlParamList.in("id", Convert.strToList(groups));
		}
		// if (!Check.isEmpty(hhXtYh.getNxb())) {
		// hqlSearchCondition.getHqlParamList().add(
		// Restrictions.eq("nxb", hhXtYh.getNxb()));
		// }
		return dao.queryPagingData(UsGroup.class, hqlParamList, pageRange);
	}

}
