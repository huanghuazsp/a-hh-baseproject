package com.hh.usersystem.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.usersystem.bean.usersystem.UsSysGroup;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.bean.usersystem.UsGroup;
import com.hh.usersystem.service.impl.GroupService;
import com.hh.usersystem.service.impl.UserGroupService;
import com.hh.usersystem.service.impl.UserService;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;

@SuppressWarnings("serial")
public class ActionGroup extends BaseServiceAction<UsSysGroup> {

	private String groups;
	@Autowired
	private GroupService service;

	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private UserService userService;

	public BaseService<UsSysGroup> getService() {
		return service;
	}

	public Object queryPagingData() {
		return service.queryPagingData(object, groups,
				this.getPageRange());
	}

	public Object queryListAndUserGroup() {
		
		List<Object> returnObjects = new ArrayList<Object>();
		if ("root".equals(this.object.getNode())) {
			UsGroup userGroup = new UsGroup();
			userGroup.setText("自定义组");
			userGroup.setExpanded(1);
			userGroup.setIcon("/hhcommon/images/myimage/org/dept.png");
			userGroup.setType("zdy");
			List<UsGroup> userGroups =	userGroupService.queryAllTreeList();
			
			for (UsGroup userGroup2 : userGroups) {
				addUserGroup(userGroup2);
			}
			
			userGroup.setChildren(userGroups);
			returnObjects.add(userGroup);
		}
		List<UsSysGroup> groupList=	service.queryTreeList(this.object.getNode(),Convert.toBoolean(request.getParameter("isNoLeaf")));
		for (UsSysGroup hhXtGroup : groupList) {
			addXtGroup(hhXtGroup);
		}
		returnObjects.addAll(groupList);
		return returnObjects;
	}
	
	private void addXtGroup(UsSysGroup userGroup) {
		if (userGroup.getChildren() != null) {
			for (UsSysGroup userGroup2 : userGroup.getChildren()) {
				addXtGroup(userGroup2);
			}
		}
		if (Check.isNoEmpty(userGroup.getUsers())) {
			List<String> userIds = Convert.strToList(userGroup.getUsers());
			List<UsUser> hhXtYhs = userService.queryListByIds(userIds);
			for (UsUser hhXtYh : hhXtYhs) {
				UsSysGroup extTree = new UsSysGroup();
				extTree.setId( hhXtYh.getId());
				extTree.setText(hhXtYh.getText());
				extTree.setType("user");
				if (LoginUser.getLoginUserId().contains(hhXtYh.getId())) {
					extTree.setIcon(hhXtYh.getNxb() == 0 ? StaticProperties.HHXT_USERSYSTEM_NV
							: StaticProperties.HHXT_USERSYSTEM_NAN);
				} else {
					extTree.setIcon(StaticProperties.HHXT_NO_ON_LINE_USER_ICON);
				}
				extTree.setLeaf(1);
				if (userGroup.getChildren()==null) {
					userGroup.setChildren(new ArrayList<UsSysGroup>());
				}
				userGroup.getChildren().add(extTree);
			}
		}
	}

	private void addUserGroup(UsGroup userGroup) {
		if (userGroup.getChildren() != null) {
			for (UsGroup userGroup2 : userGroup.getChildren()) {
				addUserGroup(userGroup2);
			}
		}
		if (Check.isNoEmpty(userGroup.getUsers())) {
			List<String> userIds = Convert.strToList(userGroup.getUsers());
			List<UsUser> hhXtYhs = userService.queryListByIds(userIds);
			for (UsUser hhXtYh : hhXtYhs) {
				UsGroup extTree = new UsGroup();
				extTree.setId( hhXtYh.getId());
				extTree.setText(hhXtYh.getText());
				extTree.setType("user");
				if (LoginUser.getLoginUserId().contains(hhXtYh.getId())) {
					extTree.setIcon(hhXtYh.getNxb() == 0 ? StaticProperties.HHXT_USERSYSTEM_NV
							: StaticProperties.HHXT_USERSYSTEM_NAN);
				} else {
					extTree.setIcon(StaticProperties.HHXT_NO_ON_LINE_USER_ICON);
				}
				extTree.setLeaf(1);
				userGroup.getChildren().add(extTree);
			}
		}
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}
}
