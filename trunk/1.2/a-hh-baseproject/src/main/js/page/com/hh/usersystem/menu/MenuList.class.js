Ext.define('com.hh.usersystem.menu.MenuList', {
	extend : 'com.hh.global.SimpleGridPanelWindow',
	action : 'usersystem-menu-',
	editPage : 'com.hh.usersystem.menu.MenuEdit',
	menu_leaf : 0,
	storeConfig : {
		autoLoad : false
	},
	deletesurl:'usersystem-menu-deleteByIds',
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
		var tree2 = Ext.create('com.hh.global.SimpleTreePanel', {
			id : this.id + '_tree2',
			region : 'east',
			hi_addRoot : true,
			hi_expandAll : true,
			hi_collapseAll : true,
			title : '操作权限',
			query_action : 'usersystem-operate-queryOperateListByPid',
			delete_action : 'usersystem-operate-deleteByIds',
			editPage : 'com.hh.usersystem.operate.OperateEdit',
			doAdd : function() {
				var page = this;
				if (tree2.store.proxy.extraParams.vpid) {
					var editPageUrl = this.editPage;
					ExtUtil.open(editPageUrl, {
								parentPanel : this,
								params : {
									vpid : tree2.store.proxy.extraParams.vpid
								},
								callbackRefresh : function() {
									page.store.load();
								}
							});
				} else {
					ExtFrame.info('请选中要增加操作的菜单！');
				}
			},
			doUpdate : function() {
				if (tree2.store.proxy.extraParams.vpid) {
					var page = this;
					var editPageUrl = this.editPage;
					var record = this.tree.getSelectionModel().getSelection();
					if (Util.isNull(record)) {
						ExtFrame.info('请选中要编辑的数据！');
					} else {
						ExtUtil.open(editPageUrl, {
									parentPanel : this,
									params : {
										vpid : tree2.store.proxy.extraParams.vpid
									},
									parentRecord : record[0],
									callbackRefresh : function() {
										page.store.load();
									}
								});
					}
				}
			}
		});
		this.tree2 = tree2;
		this.init();
	},
	extraParams : {
		leaf : 0,
		node : 0
	},
	init : function() {
		var page = this;
		var tree = Ext.create('com.hh.global.SimpleTreePanel', {
			getTreePanelListeners : function() {
				return {
					itemclick : function(view, record) {
						page.grid.getStore().proxy.extraParams = {
							node : record.get("id"),
							leaf : 0
						};
						page.putParam('defaultParentNode', record);
						page.grid.getStore().load();
					}
				};
			},
			title : '菜单类别',
			query_action : 'usersystem-menu-queryMenuListByPid',
			delete_action : 'usersystem-menu-deleteByIds',
			editPage : 'com.hh.usersystem.menu.MenuEdit',
			menu_leaf : 1,
			action : 'usersystem-menu-',
			getBbar : function() {
				return [this.getToolbarItem("up"), this.getToolbarItem("down")];
			}
		});
		this.add(tree);
		this.add(this.tree2);
	},
	getTbarItems : function() {
		var page = this;
		return [this.getToolbarItem("add"), '-', this.getToolbarItem("update"),
				'-', this.getToolbarItem("delete"), this.getToolbarItem("up"),
				'-', this.getToolbarItem("down"), '->', {
					iconCls : 'search',
					text : '无父菜单查询',
					handler : function() {
						page.grid.getStore().proxy.extraParams = {
							node : 'root',
							leaf : 0
						};
						page.putParam('defaultParentNode', null);
						page.grid.getStore().load();
					}
				}, this.getToolbarItem("search")];
	},
	getGridListeners : function() {
		var tree2id = this.id + '_tree2';
		return {
			itemclick : function(view, record) {
				var tree2 = Ext.getCmp(tree2id);
				Ext.apply(tree2.store.proxy.extraParams, {
							vpid : record.get("id")
						});
				tree2.setTitle("操作权限" + "<font color=red>（"
						+ record.get("text") + "）</font>");
				tree2.store.load();
			}
		};
	},
	getGridColumns : function() {
		return [{
					dataIndex : 'id',
					flex : 1,
					hidden : true
				}, {
					text : '名称',
					dataIndex : 'text',
					flex : 1
				},/*
					 * { text : '父ID', dataIndex : 'node', flex : 1 },
					 */{
					text : '动作',
					dataIndex : 'vsj',
					flex : 2
				}, {
					text : '排序',
					dataIndex : 'npx',
					flex : 1
				}, {
					text : '类型',
					dataIndex : 'nlx',
					flex : 1
				}, {
					text : '小图标',
					dataIndex : 'icon',
					flex : 1,
					renderer : function(value) {
						return '<img src="' + value + '"/>';
					}
				}, {
					text : '桌面图片',
					width : 80,
					dataIndex : 'vdtp',
					renderer : function(value) {
						return '<img src="' + value + '"/>';
					}
				}, {
					text : '描述',
					dataIndex : 'vbz',
					flex : 2
				}, this.getOperateGridColumn()];
	},
	getStoreFields : function() {
		return ['text', 'node', 'icon', 'vsj', 'leaf', 'vbz', 'nlx',
				'vdtp'];
	}
});