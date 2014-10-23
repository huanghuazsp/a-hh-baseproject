Ext.define('com.hh.usersystem.group.GroupEdit', {
	extend : 'com.hh.global.SimpleFormPanelWindow',
	action : 'usersystem-Group-',
	title : '用户组编辑',
	width : 650,
	height : 400,
	constructor : function(config) {
		this.config = config || {};
		this.callParent(arguments);
	},
	getFormItems : function() {
		return [ {
			name : 'id',
			hidden : true
		}, {
			fieldLabel : '名称',
			name : 'text',
			allowBlank : false,
			columnWidth : 1,
			maxLength : 64
		}, {
			fieldLabel : '备注',
			xtype : 'textarea',
			name : 'remark',
			maxLength : 512,
			columnWidth : 1
		}, {
			xtype : "widgetUserSelect",
			fieldLabel : "组员",
			name : 'users'
		} ];
	}
});