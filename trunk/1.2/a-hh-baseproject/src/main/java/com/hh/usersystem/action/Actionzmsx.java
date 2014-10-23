package com.hh.usersystem.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseAction;
import com.hh.usersystem.bean.usersystem.HHXtZmsx;
import com.hh.usersystem.service.impl.ZmsxService;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class Actionzmsx extends BaseAction implements ModelDriven<HHXtZmsx> {
	private HHXtZmsx hhXtZmsx = new HHXtZmsx();
	@Autowired
	private ZmsxService zmsxService;

	public void updateZmbj() {
		zmsxService.updateZmbj(hhXtZmsx);
	}
	
	public void updateTheme() {
		zmsxService.updateTheme(hhXtZmsx);
	}

	public HHXtZmsx getModel() {
		return hhXtZmsx;
	}
}
