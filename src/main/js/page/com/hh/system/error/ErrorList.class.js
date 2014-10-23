Ext.define('com.hh.system.error.ErrorList', {
	extend : 'com.hh.global.SimpleGridPanelWindow',
	action : 'system-Error-',
	gridAction : 'queryPagingData',
	editPage : 'com.hh.usersystem.user.UserEdit',
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
	},
	gridOpen : true,
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
			text : '异常类名',
			dataIndex : 'name',
			flex : 0.5
		}, {
			text : '异常生成时间',
			dataIndex : 'dcreate',
			flex : 1,
			renderer : Ext.util.Format.dateRenderer('Y年m月d日 H时i分s秒')
		}, {
			text : '异常内容',
			dataIndex : 'message',
			flex : 0.5
		}, {
			text : '异常完整内容',
			dataIndex : 'allMessage',
			flex : 1
		} ];
	},
	getStoreFields : function() {
		return [ 'id', 'name', 'message', 'allMessage', 'dcreate' ];
	}
});