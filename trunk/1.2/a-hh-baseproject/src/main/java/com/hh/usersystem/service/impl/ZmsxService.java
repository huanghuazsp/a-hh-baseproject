package com.hh.usersystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.service.impl.BaseService;
import com.hh.usersystem.bean.usersystem.HHXtZmsx;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.opensymphony.xwork2.ActionContext;

@Service
public class ZmsxService extends BaseService<HHXtZmsx> {
	@Autowired
	private LoginUserUtilService loginUserService;

	public void updateZmbj(HHXtZmsx hhXtZmsx) {
		hhXtZmsx.setId(loginUserService.findLoginUser().getId());
		dao.updateEntity("update " + HHXtZmsx.class.getName()
				+ " o set o.vzmbj=:vzmbj where o.id=:id", hhXtZmsx);
	}

	public void updateDefaultOrg(String userId, String orgid) {
		dao.updateEntity("update " + HHXtZmsx.class.getName()
				+ " o set o.defaultOrgId=? where o.id=?", new Object[] { orgid,
				userId });
	}

	public void updateTheme(HHXtZmsx hhXtZmsx) {
		HhXtYh hhXtYh = loginUserService.findLoginUser();
		hhXtZmsx.setId(hhXtYh.getId());
		dao.updateEntity("update " + HHXtZmsx.class.getName()
				+ " o set o.theme=:theme where o.id=:id", hhXtZmsx);
		hhXtYh.getHhXtZmsx().setTheme(hhXtZmsx.getTheme());
		ActionContext.getContext().getSession().put("loginuser", hhXtYh);
	}

}
