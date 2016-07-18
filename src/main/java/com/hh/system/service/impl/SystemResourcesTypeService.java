 package com.hh.system.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.SystemResourcesType;
import com.hh.system.util.Convert;
import com.hh.system.util.dto.ParamFactory;
import com.hh.usersystem.LoginUserServiceInf;

@Service
public class SystemResourcesTypeService extends BaseService<SystemResourcesType> {

	@Autowired
	private LoginUserServiceInf userService;
	@Override
	public List<SystemResourcesType> queryTreeList(String node) {
		return super.queryTreeList(node,ParamFactory.getParamHb().is("vcreate", userService.findUserId()));
	}
	
	public void doSetState(String ids,int state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", Convert.strToList(ids));
		map.put("state", state);
		dao.updateEntity("update " + SystemResourcesType.class.getName() + " o set o.state=:state where o.id in :id", map);
	}
}
 