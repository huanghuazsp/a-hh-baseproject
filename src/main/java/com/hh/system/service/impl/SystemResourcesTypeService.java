 package com.hh.system.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.SystemResourcesType;
import com.hh.system.util.Convert;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.LoginUserServiceInf;

@Service
public class SystemResourcesTypeService extends BaseService<SystemResourcesType> {

	@Autowired
	private LoginUserServiceInf userService;
	
	public List<SystemResourcesType> queryTreeList(SystemResourcesType object) {
		ParamInf paramInf = ParamFactory.getParamHb();
		if (object.getState()==1) {
			paramInf.is("state",1);
		}else{
			paramInf.is("vcreate", userService.findUserId());
		}
		List<SystemResourcesType> resourcesTypes = super.queryTreeList(object.getNode(),paramInf);
		if (object.getState()==0) {
			render(resourcesTypes);
		}
		return resourcesTypes;
	}
	
	private void render(List<SystemResourcesType> resourcesTypes) {
		if (resourcesTypes!=null) {
			for (SystemResourcesType systemResourcesType : resourcesTypes) {
				if (systemResourcesType.getState()==1) {
					systemResourcesType.setIconSkin("share");
				}
				render(systemResourcesType.getChildren());
			}
		}
	}

	public void doSetState(String ids,int state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", Convert.strToList(ids));
		map.put("state", state);
		dao.updateEntity("update " + SystemResourcesType.class.getName() + " o set o.state=:state where o.id in :id", map);
	}
}
 