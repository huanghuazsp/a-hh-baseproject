Ext.define('com.hh.usersystem.user.OrgSelect', {
			extend : 'com.hh.global.widget.ComboBoxMultiTree',
			alias : 'widget.widgetOrgSelect',
			name : 'orgIdsStr',
			fieldLabel : '岗位',
			tableName : 'US_ORGANIZATION',
			allowBlank : true,
			treeMinWidth : 500,
			multiSelect : true,
			columnWidth : 1,
			action : 'usersystem-Org-queryOrgListByPid',
			selectServiceType : '',
			beitemclick2 : function(view, record) {
				if (record.get("text")) {
					var indStr = '';
					var messageStr = '';
					if (this.selectServiceType == 'gw') {
						indStr = "<font color='red'>（岗位）</font>";
						messageStr = "您必须选择岗位！！";
					} else if (this.selectServiceType == 'jt') {
						indStr = "<font color='red'>（集团）</font>";
						messageStr = "您必须选择集团！！";
					} else if (this.selectServiceType == 'jg') {
						indStr = "<font color='red'>（机构）</font>";
						messageStr = "您必须选择机构！！";
					} else if (this.selectServiceType == 'bm') {
						indStr = "<font color='red'>（部门）</font>";
						messageStr = "您必须选择部门！！";
					} else {
						return true;
					}
					if (record.get("text").indexOf(indStr) > -1) {
						return true;
					} else {
						ExtFrame.msg(messageStr);
						return false;
					}
				} else {
					return true;
				}
			},
			beitemclick3 : function(view, record) {
				return true;
			},
			beitemclick : function(view, record) {
				if (this.beitemclick2(view, record)) {
					return this.beitemclick3(view, record);
				} else {
					return false;
				}
			},
			filterText : function(text) {
				if (text) {
					return text.replace("<font color='red'>（岗位）</font>", "")
							.replace("<font color='red'>（集团）</font>", "")
							.replace("<font color='red'>（部门）</font>", "")
							.replace("<font color='red'>（机构）</font>", "");
				}
			}
		});
Ext.define('com.hh.usersystem.user.UserSelect', {
	extend : 'com.hh.global.widget.ComboBoxMultiTree',
	alias : 'widget.widgetUserSelect',
	tableName : 'US_USER',
	treeMinWidth : 740,
	mainPanelHeight : 250,
	columnWidth : 1,
	action : 'usersystem-Org-queryOrgListByPid',
	createTreePanel : function() {
		this.createGrid();
		var items = [];
		if (this.isorg != true || this.isrole != true || this.isgroup != true) {
			items.push(this.createTree());
		} else {
			this.treeMinWidth = 467;
		}
		items.push(this.createSelectGrid());
		items.push(this.grid);
		var panel = Ext.create('Ext.panel.Panel', {
					layout : 'border',
					height : this.mainPanelHeight,
					floating : true,
					focusOnToFront : false,
					shadow : true,
					ownerCt : this.ownerCt,
					items : items,
					minWidth : this.treeMinWidth
				});
		return panel;
	},
	createSelectGridStore : function() {
		var page = this;
		return this.selectGridStore = ExtStore.getDataStore({
					url : 'usersystem-user-queryPagingData',
					fields : ['id', 'text'],
					extraParams : {
						roles : page.roles,
						orgs : page.orgs,
						groups : page.groups,
						users : page.users
					}
				});
	},
	getSelectGridListeners : function() {
		var page = this;
		return {
			itemclick : function(view, record) {
				if (page.select == 'leaf' && !record.raw.leaf) {
					return;
				}
				if (page.beitemclick(view, record)) {
					var store = page.grid.getStore();
					if (store.find("id", record.get("id")) < 0) {
						if (page.multiSelect == false) {
							page.grid.getStore().removeAll();
						}
						page.grid.getStore().add({
									id : record.get("id"),
									text : page.filterText(record.get("text"))
								});
					} else {
						ExtFrame.msg("您已经选中该项了！！");
					}
				}
			}
		};
	},
	getSelectGridTbar : function() {
		var page = this;
		var userNameField = Ext.create('Ext.form.field.Text', {
					labelWidth : 40,
					fieldLabel : '搜索',
					width : 170
				});
		return Ext.create('com.hh.base.BaseToolbar', {
			enableOverflow : true,
			items : [userNameField, '->', {
						iconCls : 'search',
						text : "搜索",
						handler : function() {
							Ext
									.apply(
											page.selectGrid.getStore().proxy.extraParams,
											{
												text : userNameField.getValue()
											});
							page.selectGrid.getStore().load();
						}
					}]
		});
	},
	createSelectGrid : function() {
		var page = this;
		return page.selectGrid = Ext.create('Ext.grid.Panel', {
					store : page.createSelectGridStore(),
					padding : '1',
					split : true,
					tbar : this.getSelectGridTbar(),
					columns : [{
								header : '名称',
								dataIndex : 'text',
								flex : 1
							}, {
								header : 'id',
								dataIndex : 'id',
								flex : 1,
								hidden : true
							}],
					width : 260,
					region : 'east',
					selModel : Ext.create('Ext.selection.CheckboxModel'),
					listeners : page.getSelectGridListeners(),
					bbar : Ext.create('Ext.PagingToolbar', {
								pageSize : static_var.pageSize,
								store : page.selectGridStore
							})
				});
	},
	getRoleGridTbar : function() {
		var page = this;
		var userNameField = Ext.create('Ext.form.field.Text', {
					labelWidth : 40,
					fieldLabel : '搜索',
					width : 170
				});
		return Ext.create('com.hh.base.BaseToolbar', {
					enableOverflow : true,
					items : [userNameField, '->', {
								iconCls : 'search',
								text : "搜索",
								handler : function() {
									Ext
											.apply(
													page.roleGridStore.proxy.extraParams,
													{
														text : userNameField
																.getValue()
													});
									page.roleGridStore.load();
								}
							}]
				});
	},
	getGroupGridTbar : function() {
		var page = this;
		var userNameField = Ext.create('Ext.form.field.Text', {
					labelWidth : 40,
					fieldLabel : '搜索',
					width : 170
				});
		return Ext.create('com.hh.base.BaseToolbar', {
					enableOverflow : true,
					items : [userNameField, '->', {
								iconCls : 'search',
								text : "搜索",
								handler : function() {
									Ext
											.apply(
													page.groupGridStore.proxy.extraParams,
													{
														text : userNameField
																.getValue()
													});
									page.groupGridStore.load();
								}
							}]
				});
	},
	createGroupGrid : function(){
		var page = this;
		page.groupGridStore = ExtStore.getDataStore({
					url : 'usersystem-Group-queryPagingData',
					fields : ['id', 'text'],
					extraParams : {
						groups : page.groups
					}
				});
		return Ext.create('Ext.grid.Panel', {
					hideHeaders : true,
					store : page.groupGridStore,
					border : false,
					padding : '1',
					tbar : page.getGroupGridTbar(),
					listeners : {
						itemclick : function(view, record) {
							Request.request('usersystem-user-queryUserByGroup',
									{
										groupId : record.get("id")
									}, function(result) {
										page.selectGrid.getStore().removeAll();
										page.selectGrid.getStore().add(result);
									});
						}
					},
					columns : [{
								header : '名称',
								dataIndex : 'text',
								flex : 1
							}, {
								header : 'id',
								dataIndex : 'id',
								flex : 1,
								hidden : true
							}],
					bbar : Ext.create('Ext.PagingToolbar', {
								pageSize : static_var.pageSize,
								store : page.groupGridStore
							})
				});
	},
	createRoleGrid : function() {
		var page = this;
		page.roleGridStore = ExtStore.getDataStore({
					url : 'usersystem-role-queryPagingData',
					fields : ['id', 'text'],
					extraParams : {
						roles : page.roles
					}
				});
		return Ext.create('Ext.grid.Panel', {
					hideHeaders : true,
					store : page.roleGridStore,
					border : false,
					padding : '1',
					tbar : page.getRoleGridTbar(),
					listeners : {
						itemclick : function(view, record) {
							Request.request('usersystem-user-queryUserByRole',
									{
										roleId : record.get("id")
									}, function(result) {
										page.selectGrid.getStore().removeAll();
										page.selectGrid.getStore().add(result);
									});
						}
					},
					columns : [{
								header : '名称',
								dataIndex : 'text',
								flex : 1
							}, {
								header : 'id',
								dataIndex : 'id',
								flex : 1,
								hidden : true
							}],
					bbar : Ext.create('Ext.PagingToolbar', {
								pageSize : static_var.pageSize,
								store : page.roleGridStore
							})
				});
	},
	createTree : function() {
		var page = this;
		var items = [];
		if (page.isorg != true) {
			var orgTreePanel = Ext.create("com.hh.global.SimpleTreePanel", {
						getTbarItems : function() {
							return [this.getToolbarItem("expandAll"),
									this.getToolbarItem("collapseAll")];
						},
						collapsible : false,
						query_action : 'usersystem-Org-queryOrgListByPid',
						extraParams : {
							orgs : page.orgs
						},
						getTreePanelListeners : function() {
							return {
								itemclick : function(view, record) {
									Request
											.request(
													'usersystem-user-queryUserByOrcCode',
													{
														code : record.raw.code_
													}, function(result) {
														page.selectGrid
																.getStore()
																.removeAll();
														page.selectGrid
																.getStore()
																.add(result);
													});
								}
							};
						}
					});
			items.push({
						title : '组织机构',
						border : false,
						iconCls : 'org',
						layout : 'fit',
						items : [orgTreePanel]
					});
		}

		if (page.isrole != true) {
			items.push({
						title : '角色',
						border : false,
						iconCls : 'job',
						layout : 'fit',
						listeners : {
							'expand' : function(panel) {
								if (panel.items.items.length == 0) {
									panel.add(page.createRoleGrid());
								}
							}
						}
					});
		}
		
		if (page.isgroup != true) {
			items.push({
						title : '用户组',
						border : false,
						iconCls : 'group',
						layout : 'fit',
						listeners : {
							'expand' : function(panel) {
								if (panel.items.items.length == 0) {
									panel.add(page.createGroupGrid());
								}
							}
						}
					});
		}

		var form = Ext.create('Ext.panel.Panel', {
					layout : {
						type : 'accordion',
						animate : true
					},
					items : items,
					region : 'center'
				});
		return this.tree = form;
	}
});