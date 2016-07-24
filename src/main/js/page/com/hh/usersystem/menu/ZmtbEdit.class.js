Ext.define('com.hh.usersystem.menu.ZmtbEdit', {
	extend : 'com.hh.base.BaseServicePanel',
	width : 600,
	height : 450,
	title : '快捷方式设置',
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
		var page = this;
		this.tree = Ext.create('com.hh.base.BaseTreePanel', {
			region : 'center',
			width : null,
			collapsible : false,
			store : this.getTreeStore(),
			tbar : this.getBbar(),
			listeners : {
				itemdblclick : function(tree, record) {
					if (!page.grid.getStore()
							.findRecord('id', record.get("id"))) {
						Request.request('usersystem-menu-addZmtb', {
									id : record.get("id")
								}, function(resultObject) {
									if (resultObject.success == true) {
										var menuList = static_var.loginuser.menuList;
										page.grid.getStore().add(record.raw);
									}
								}, {
									isDefaultMsg : true
								});
					} else {
						ExtFrame.msg("此菜单已经是您的快捷方式！");
					}

				}
			}
		});
		this.createGrid();
		this.add(this.tree);
		this.add(this.grid);
	},
	getTreeStore : function() {
		this.store = Ext.create('Ext.data.TreeStore', {
					root : {
						children : Menu.getTreeChildrens('root')
					}
				});
		return this.store;
	},
	getBbar : function() {
		var page = this;
		return Ext.create('com.hh.base.BaseToolbar', {
					items : [{
								iconCls : 'expand',
								text : '全部展开',
								handler : function() {
									page.tree.collapseAll();
									page.tree.expandAll();
								}
							}, {
								iconCls : 'collapse',
								text : '全部收缩',
								handler : function() {
									page.tree.collapseAll();
								}
							}]
				});
	},
	createGrid : function() {
		var page = this;
		var grid = this.grid = Ext.create('Ext.grid.Panel', {
					store : page.getStore(),
					padding : '1',
					split : true,
					tbar : this.getGridTbar(),
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
					width : 250,
					region : 'east',
					selModel : Ext.create('Ext.selection.CheckboxModel'),
					listeners : page.getGridListeners()
				});
	},
	getGridListeners : function() {
		var page = this;
		return {
			itemdblclick : function(grid, record) {
				Request.request('usersystem-menu-deleteZmtb', {
							id : record.get("id")
						}, function(resultObject) {
							if (resultObject.success == true) {
								grid.getStore().remove(record);
							}
						}, {
							isDefaultMsg : true
						});
			}
		};
	},
	getStore : function() {
		var page = this;
		var data = [];
		var items = page.desktopStore.data.items;
		for (var i = 0; i < items.length; i++) {
			var item = items[i];
			if (item) {
				if (item.data) {
					data.push(item.data);
				}
			}
		}
		var storeCfg = {
			fields : ['id', 'text', 'vsj', 'icon', 'leaf', 'params', 'vdtp'],
			data : data
		};
		return Ext.create('com.hh.base.BaseDataStore', storeCfg);
	},
	getGridTbar : function() {
		var page = this;
		return Ext.create('com.hh.base.BaseToolbar', {
					enableOverflow : true,
					items : [{
						iconCls : 'up',
						text : '上移',
						handler : function() {
							var store = page.grid.getStore();
							var record = page.grid.getSelectionModel()
									.getLastSelected();
							var index = store.indexOf(record);
							if (index > 0) {
								var recordup = store.getAt(index - 1);
								Request.request('usersystem-Zmtb-order', {
											id1 : record.get("id"),
											id2 : recordup.get("id")
										}, function(result) {
											if (result.isSuccess == true) {
												store.removeAt(index);
												store.insert(index - 1, record);
												page.grid.getSelectionModel()
														.select(index - 1);
											}
										}, {
											isDefaultMsg : true
										});
							} else {
								ExtFrame.msg("已经是第一个了");
							}
						}
					}, {
						iconCls : 'down',
						text : '下移',
						handler : function() {
							var store = page.grid.getStore();
							var record = page.grid.getSelectionModel()
									.getLastSelected();
							var index = store.indexOf(record);
							if (index < store.getCount() - 1) {
								var recordup = store.getAt(index + 1);
								Request.request('usersystem-Zmtb-order', {
											id1 : record.get("id"),
											id2 : recordup.get("id")
										}, function(result) {
											if (result.isSuccess == true) {
												store.removeAt(index);
												store.insert(index + 1, record);
												page.grid.getSelectionModel()
														.select(index + 1);
											}

										}, {
											isDefaultMsg : true
										});
							} else {
								ExtFrame.msg("已经是最后一个了");
							}
						}
					}, '->', {
						iconCls : 'delete',
						text : '删除',
						handler : function() {
							var records = page.grid.getSelectionModel()
									.getSelection();
							if (records != null) {
								var ids = "";
								for (var i = 0; i < records.length; i++) {
									var record = records[i];
									ids += record.get("id") + ",";
								}
								if (ids != "") {
									ids = ids.substr(0, ids.length - 1);
								}
								Request.request('usersystem-menu-deleteZmtb', {
											id : ids
										}, function(resultObject) {
											if (resultObject.success == true) {
												page.grid.getStore()
														.remove(records);
											}
										}, {
											isDefaultMsg : true
										});
							} else {
								ExtFrame.msg("请选中要删除的数据！");
							}
						}
					}, {
						iconCls : 'yes',
						text : '确定',
						handler : function() {
							page.closePage();
							page.desktopStore.removeAll();
							page.grid.getStore().each(function(record) {
										page.desktopStore.add(record.data);
									});
						}
					}]
				});
	}
});