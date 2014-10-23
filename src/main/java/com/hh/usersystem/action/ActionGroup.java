package com.hh.usersystem.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Convert;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.usersystem.bean.usersystem.HhXtGroup;
import com.hh.usersystem.bean.usersystem.UserGroup;
import com.hh.usersystem.service.impl.GroupService;
import com.hh.usersystem.service.impl.UserGroupService;

@SuppressWarnings("serial")
public class ActionGroup extends BaseServiceAction<HhXtGroup> {

	private String groups;
	@Autowired
	private GroupService service;

	@Autowired
	private UserGroupService userGroupService;

	public BaseService<HhXtGroup> getService() {
		return service;
	}

	public void queryPagingData() {
		this.returnResult(service.queryPagingData(object, groups,
				this.getPageRange()));
	}

	public void queryListAndUserGroup() {
		
		List<Object> returnObjects = new ArrayList<Object>();
		if ("root".equals(this.object.getNode())) {
			UserGroup userGroup = new UserGroup();
			userGroup.setText("自定义组");
			userGroup.setExpanded(1);
			userGroup.setIcon("/hhcommon/images/myimage/org/dept.png");
			
			List<UserGroup> userGroups =	userGroupService.queryAllTreeList();
			userGroup.setChildren(userGroups);
			returnObjects.add(userGroup);
		}
		List<HhXtGroup> groupList=	service.queryTreeList(this.object,Convert.toBoolean(request.getParameter("isNoLeaf")));
		returnObjects.addAll(groupList);
		this.returnResult(returnObjects);
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}
}
