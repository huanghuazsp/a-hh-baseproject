Ext.define('com.hh.usersystem.role.RoleList', {
			extend : 'com.hh.global.SimpleGridPanelWindow',
			action : 'usersystem-role-',
			editPage : 'com.hh.usersystem.role.RoleEdit',
			constructor : function(config) {
				this.config = config || {};
				this.superclass.constructor.call(this, this.config);
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
							text : '状态',
							dataIndex : 'nzt',
							flex : 1,
							renderer : function(value) {
								return value == 0
										? '正常'
										: '<font color=red>冻结</font>';
							}
						}, {
							text : '类型',
							dataIndex : 'nlx',
							flex : 1,
							renderer : function(value) {
								return value == 1 ? '业务角色' : value == 2
										? "管理角色"
										: value == 3 ? "系统内置角色" : "";
							}
						}, {
							text : '备注',
							dataIndex : 'vbz',
							flex : 2
						}, this.getOperateGridColumn()];
			},
			getStoreFields : function() {
				return ['id', 'text', 'nzt', 'vbz', 'nlx'];
			}
		});