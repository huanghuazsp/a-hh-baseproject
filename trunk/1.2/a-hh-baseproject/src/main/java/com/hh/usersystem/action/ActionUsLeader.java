package com.hh.usersystem.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.usersystem.bean.usersystem.UsLeader;
import com.hh.usersystem.service.impl.UsLeaderService;

@SuppressWarnings("serial")
public class ActionUsLeader extends BaseServiceAction<UsLeader> {

	@Override
	public BaseService<UsLeader> getService() {
		return leaderService;
	}
	
	@Autowired
	private UsLeaderService leaderService;
	
	public void addLeaders() {
		leaderService.addLeaders(object);
	}
	
	public void deleteLeaders() {
		leaderService.deleteLeaders(object);
	}
	

}
