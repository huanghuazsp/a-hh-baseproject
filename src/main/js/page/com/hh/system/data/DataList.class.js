Ext.define('com.hh.system.data.DataList', {
			extend : 'com.hh.global.SimpleGridPanelWindow',
			action : 'system-Data-',
			editPage : 'com.hh.system.data.DataListEdit',
			constructor : function(config) {
				this.config = config || {};
				this.superclass.constructor.call(this, this.config);
				var page = this;
				var dataListTree = ExtUtil.create(
						'com.hh.system.data.DataListTree', {
							query_action : 'system-Data-queryDataTreeByPid',
							hi_add : true,
							hi_addRoot : true,
							hi_update : true,
							hi_delete : true,
							getTreePanelListeners : function() {
								return {
									itemclick : function(view, record) {
										if (record.get("leaf") == true) {
											page.grid.setDisabled(false);
											page.treeRecord = record;
											page.grid.getStore().proxy.extraParams = {
												node : record.get("id")
											};
											page.grid.getStore().load();
										}
									}
								}
							}
						});
				this.add(dataListTree);
			},
			storeConfig : {
				autoLoad : false
			},
			getGridConfig : function() {
				return {
					disabled : true
				}
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
						}, {
							text : '备注',
							dataIndex : 'describe_',
							flex : 1
						}, this.getOperateGridColumn()];
			},
			getStoreFields : function() {
				return ['text', 'describe_'];
			}
		});