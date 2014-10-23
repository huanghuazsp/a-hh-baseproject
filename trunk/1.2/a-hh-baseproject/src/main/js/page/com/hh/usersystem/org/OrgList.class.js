Ext.define('com.hh.usersystem.org.OrgList', {
	extend : 'com.hh.base.BaseServicePanel',
	action : 'usersystem-Org-',
	width : 850,
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
		this.init();
	},
	init : function() {
		var page = this;

		var tree4 = Ext.create('com.hh.global.SimpleTreePanel', {
			region : 'center',
			collapsible : false,
			width : 200,
			getTreePanelListeners : function() {
				return {
					itemclick : function(view, record) {

					}
				};
			},
			extraParams : {
				'lx_' : 3
			},
			title : '岗位管理',
			query_action : 'usersystem-Org-queryOrgListByPidAndLx',
			delete_action : 'usersystem-Org-deleteByIds',
			editPage : 'com.hh.usersystem.org.OrgEdit',
			lx_ : 3,
			getBbar : function() {
				return Ext.create('com.hh.base.BaseToolbar', {
					items : [ {
						iconCls : 'table_refresh',
						text : '清除条件刷新',
						handler : function() {
							page.loadTreeRoot(tree4, '岗位管理');
						}
					}, '->', {
						iconCls : 'table_refresh',
						text : '刷新',
						handler : function() {
							tree4.store.load();
						}
					} ]
				});
			}
		});

		var tree3 = Ext.create('com.hh.global.SimpleTreePanel', {
			width : 200,
			getTreePanelListeners : function() {
				return {
					itemclick : function(view, record) {
						page.loadTree(tree4, record, '岗位管理');
					}
				};
			},
			extraParams : {
				'lx_' : 2
			},
			title : '部门管理',
			query_action : 'usersystem-Org-queryOrgListByPidAndLx',
			delete_action : 'usersystem-Org-deleteByIds',
			editPage : 'com.hh.usersystem.org.OrgEdit',
			lx_ : 2,
			getBbar : function() {
				return Ext.create('com.hh.base.BaseToolbar', {
					items : [ {
						iconCls : 'table_refresh',
						text : '清除条件刷新',
						handler : function() {
							page.loadTreeRoot(tree3, '部门管理');
						}
					}, '->', {
						iconCls : 'table_refresh',
						text : '刷新',
						handler : function() {
							tree3.store.load();
						}
					} ]
				});
			}
		});

		var tree2 = Ext.create('com.hh.global.SimpleTreePanel', {
			collapsible : false,
			width : 200,
			getTreePanelListeners : function() {
				return {
					itemclick : function(view, record) {
						page.loadTree(tree3, record, '部门管理');
						page.loadTree(tree4, record, "岗位管理");
					}
				};
			},
			extraParams : {
				'lx_' : 1
			},
			title : '机构管理',
			query_action : 'usersystem-Org-queryOrgListByPidAndLx',
			delete_action : 'usersystem-Org-deleteByIds',
			editPage : 'com.hh.usersystem.org.OrgEdit',
			lx_ : 1,
			getBbar : function() {
				return Ext.create('com.hh.base.BaseToolbar', {
					items : [ {
						iconCls : 'table_refresh',
						text : '清除条件刷新',
						handler : function() {
							page.loadTreeRoot(tree2, '机构管理');
						}
					}, '->', {
						iconCls : 'table_refresh',
						text : '刷新',
						handler : function() {
							tree2.store.load();
						}
					} ]
				});
			}
		});

		var tree1 = Ext.create('com.hh.global.SimpleTreePanel', {
			width : 200,
			getTreePanelListeners : function() {
				return {
					itemclick : function(view, record) {
						page.loadTree(tree2, record, "机构管理");
						page.loadTree(tree3, record, "部门管理");
						page.loadTree(tree4, record, "岗位管理");
					}
				};
			},
			extraParams : {
				'lx_' : 0
			},
			title : '集团管理',
			query_action : 'usersystem-Org-queryOrgListByPidAndLx',
			delete_action : 'usersystem-Org-deleteByIds',
			editPage : 'com.hh.usersystem.org.OrgEdit',
			lx_ : 0
		});

		this.add([ tree1, tree2, tree3, tree4 ]);
	},
	loadTree : function(tree2, record, title) {
		Ext.apply(tree2.store.proxy.extraParams, {
			'paramsMap.node' : record.get("id")
		});
		tree2.store.load();
		tree2.setTitle(title + '&nbsp;&nbsp;<font color="red">('
				+ record.get("text") + ')</font>');
		tree2.prent_record = record;
	},
	loadTreeRoot : function(tree2, title) {
		Ext.apply(tree2.store.proxy.extraParams, {
			'paramsMap.node' : 'root'
		});
		tree2.store.load();
		tree2.setTitle('机构管理');
		tree2.prent_record = null;
	}
});