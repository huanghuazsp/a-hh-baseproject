Ext.define('com.hh.usersystem.group.GroupList', {
			extend : 'com.hh.global.SimpleGridPanelWindow',
			action : 'usersystem-Group-',
			title : '用户组',
			editPage : 'com.hh.usersystem.group.GroupEdit',
			hi_up : false,
			hi_down : false,
			constructor : function(config) {
				this.config = config || {};
				this.callParent(arguments);
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
							dataIndex : 'remark',
							flex : 2
						}, this.getOperateGridColumn()];
			},
			getStoreFields : function() {
				return ['id', 'text', 'remark'];
			}
		});