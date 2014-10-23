package com.hh.usersystem.service.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.hibernate.util.dto.HQLParamList;
import com.hh.hibernate.util.dto.PagingData;
import com.hh.system.service.impl.BaseTreeService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.usersystem.bean.usersystem.HhXtGroup;
import com.hh.usersystem.bean.usersystem.HhXtYhGroup;

@Service
public class GroupService extends BaseTreeService<HhXtGroup> {

	@Autowired
	private IHibernateDAO<HhXtYhGroup> hhXtYhGroupdao;

	@Override
	public HhXtGroup findObjectById(String id) {
		HhXtGroup hhXtGroup = super.findObjectById(id);
		List<HhXtYhGroup> hhXtYhGroups = hhXtYhGroupdao.queryList(
				HhXtYhGroup.class, Restrictions.eq("groupId", id));
		StringBuffer stringBuffer = new StringBuffer();
		for (HhXtYhGroup hhXtYhGroup : hhXtYhGroups) {
			stringBuffer.append(hhXtYhGroup.getYhId() + ",");
		}
		String userStr = stringBuffer.toString();
		if (!"".equals(userStr)) {
			userStr = userStr.substring(0, userStr.length() - 1);
			hhXtGroup.setUsers(userStr);
		}
		return hhXtGroup;
	}

	@Override
	public List<String> deleteTreeByIds(String ids) {
		List<String> deleteIdList = super.deleteTreeByIds(ids);
		hhXtYhGroupdao.deleteEntity(HhXtYhGroup.class, "groupId", deleteIdList);
		return deleteIdList;
	}

	@Override
	public HhXtGroup save(HhXtGroup entity) throws MessageException {
		hhXtYhGroupdao.deleteEntity(HhXtYhGroup.class, "groupId",
				entity.getId());
		HhXtGroup hhXtGroup = super.save(entity);

		String userIds = entity.getUsers();
		if (Check.isNoEmpty(userIds)) {
			String[] userArr = userIds.split(",");
			for (String userid : userArr) {
				HhXtYhGroup hhXtYhGroup = new HhXtYhGroup();
				hhXtYhGroup.setYhId(userid);
				hhXtYhGroup.setGroupId(entity.getId());
				hhXtYhGroupdao.createEntity(hhXtYhGroup);
			}
		}
		return hhXtGroup;
	}

	public PagingData<HhXtGroup> queryPagingData(HhXtGroup object,
			String groups, PageRange pageRange) {
		HQLParamList hqlParamList = new HQLParamList();
		if (!Check.isEmpty(object.getText())) {
			hqlParamList.add(Restrictions.like("text", "%" + object.getText()
					+ "%"));
		}
		if (!Check.isEmpty(groups)) {
			hqlParamList.add(Restrictions.in("id", Convert.strToList(groups)));
		}
		// if (!Check.isEmpty(hhXtYh.getNxb())) {
		// hqlSearchCondition.getHqlParamList().add(
		// Restrictions.eq("nxb", hhXtYh.getNxb()));
		// }
		return dao.queryPagingData(HhXtGroup.class, hqlParamList, pageRange);
	}

}
