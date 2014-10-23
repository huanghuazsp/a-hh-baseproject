Ext.define('com.hh.usersystem.user.OnlineUserList', {
	extend : 'com.hh.global.SimpleGridPanelWindow',
	action : 'usersystem-user-',
	gridAction : 'queryOnLinePagingData',
	editPage : 'com.hh.usersystem.user.UserEdit',
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
	},
	getTbarItems : function() {
		return [ this.getToolbarItem("delete") ];
	},
	getRightMenuItems : function() {
		var toolbarItems = [];
		toolbarItems.push(this.getToolbarItem("delete"));
		return toolbarItems;
	},
	getContainerRightMenuItems : function() {
		var toolbarItems = [];
		return toolbarItems;
	},
	getGridColumns : function() {
		return [ {
			dataIndex : 'id',
			flex : 1,
			hidden : true
		}, {
			text : '性别',
			dataIndex : 'nxb',
			flex : 0.5,
			renderer : GridPanel.getSex()
		}, {
			text : '状态',
			dataIndex : 'nzt',
			flex : 0.5,
			renderer : function(value) {
				return value == 0 ? '正常' : '<font color=red>冻结</font>';
			}
		}, {
			text : '用户名称',
			dataIndex : 'text',
			flex : 1
		}, {
			text : '电子邮件',
			dataIndex : 'vdzyj',
			flex : 1
		}, {
			text : '电话',
			dataIndex : 'vdh',
			flex : 1
		}, {
			text : '生日',
			dataIndex : 'dsr',
			renderer : Ext.util.Format.dateRenderer('Y年m月d日'),
			flex : 1
		} ];
	},
	getStoreFields : function() {
		return [ 'id', 'text', 'nxb', 'nzt', 'vdzyj', 'vdh', 'dsr' ];
	},
	doDelete : function() {
		var panel = this;
		var url = panel.action + 'deleteOnLineByIds';
		var grid = this.grid;
		var records = grid.getSelectionModel().getSelection();
		this.doBaseDelete(grid, records, url);
	}
});