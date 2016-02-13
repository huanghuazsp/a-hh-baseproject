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
import com.hh.usersystem.bean.usersystem.UsSysGroup;

@Service
public class GroupService extends BaseService<UsSysGroup> {

	@Override
	public UsSysGroup findObjectById(String id) {
		UsSysGroup hhXtGroup = super.findObjectById(id);
		return hhXtGroup;
	}

	@Override
	public List<String> deleteTreeByIds(String ids) {
		List<String> deleteIdList = super.deleteTreeByIds(ids);
		return deleteIdList;
	}

	@Override
	public UsSysGroup save(UsSysGroup entity) throws MessageException {
		UsSysGroup hhXtGroup = super.saveTree(entity);
		return hhXtGroup;
	}

	public PagingData<UsSysGroup> queryPagingData(UsSysGroup object,
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
		return dao.queryPagingData(UsSysGroup.class, hqlParamList, pageRange);
	}

}
