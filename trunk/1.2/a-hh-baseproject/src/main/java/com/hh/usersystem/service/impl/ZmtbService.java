package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.hibernate.util.dto.HQLParamList;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Convert;
import com.hh.system.util.PrimaryKey;
import com.hh.usersystem.bean.usersystem.HhXtCd;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.HhXtYhCdZmtb;
import com.opensymphony.xwork2.ActionContext;

@Service
public class ZmtbService extends BaseService<HhXtYhCdZmtb> {
	@Autowired
	private LoginUserUtilService loginUserService;
	@Autowired
	private MenuService menuService;

	public void updateOrder(String id1, String order1, String id2, String order2) {
		HhXtYhCdZmtb entity1 = dao.findEntity(HhXtYhCdZmtb.class,
				Restrictions.eq("cdId", id1));
		String order1_ = entity1.getOrder();
		HhXtYhCdZmtb entity2 = dao.findEntity(HhXtYhCdZmtb.class,
				Restrictions.eq("cdId", id2));
		String order2_ = entity2.getOrder();
		dao.updateEntity("update " + HhXtYhCdZmtb.class.getName()
				+ " o set o.order=? where o.cdId=?", new Object[] { order1_,
				id2 });
		dao.updateEntity("update " + HhXtYhCdZmtb.class.getName()
				+ " o set o.order=? where o.cdId=?", new Object[] { order2_,
				id1 });
	}

	public List<HhXtCd> queryZmtbByUserId(String userId) {
		List<HhXtYhCdZmtb> hhXtYhCdZmtbList = dao
				.queryList(HhXtYhCdZmtb.class, new HQLParamList()
						.addCondition(Restrictions.eq("yhId", userId)));
		List<HhXtCd> hhXtCds = new ArrayList<HhXtCd>();
		for (HhXtYhCdZmtb hhXtYhCdZmtb : hhXtYhCdZmtbList) {
			hhXtCds.add(hhXtYhCdZmtb.getHhXtCd());
		}
		return hhXtCds;
	}

	@Transactional
	public void orderIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		String userid = loginUserService.findLoginUserId();
		deleteByProperty("yhId", userid);
		for (int i = idList.size() - 1; i > -1; i--) {
			HhXtYhCdZmtb hhXtYhCdZmtb = new HhXtYhCdZmtb();
			HhXtCd hhXtCd = menuService.findObjectById2(idList.get(i));
			hhXtYhCdZmtb.setHhXtCd(hhXtCd);
			hhXtYhCdZmtb.setOrder(PrimaryKey.getPrimaryKeyTime()
					+ PrimaryKey.getPrimaryKeyUUID());
			hhXtYhCdZmtb.setYhId(userid);
			dao.createEntity(hhXtYhCdZmtb);
		}
		HhXtYh hhXtYh = loginUserService.findLoginUser();
		hhXtYh.setHhXtYhCdZmtbList(queryZmtbByUserId(hhXtYh.getId()));
		ActionContext.getContext().getSession().put("loginuser", hhXtYh);
	}

}