Ext.define('com.hh.system.sql.SqlList', {
	extend : 'com.hh.global.SimpleGridPanelWindow',
	action : 'system-Sql-',
	gridAction : 'queryPagingData',
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
			text : 'SQL',
			dataIndex : 'sql',
			flex : 1
		}, {
			text : '耗时（毫秒）',
			dataIndex : 'elapsedTime',
			width : 100
		} ];
	},
	getStoreFields : function() {
		return [ 'id', 'elapsedTime', 'sql' ];
	}
});