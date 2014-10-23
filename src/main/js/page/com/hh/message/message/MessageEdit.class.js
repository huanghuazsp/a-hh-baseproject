Ext.define('com.hh.message.message.MessageEdit', {
	extend : 'com.hh.global.SimpleFormPanelWindow',
	action : 'message-SysMessage-',
	title : '编辑',
	width : 600,
	height : 400,
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
	},
	getFormItems : function() {
		return [ {
			name : 'id',
			hidden : true
		}, {
			fieldLabel : '标题',
			name : 'title',
			maxLength : 128,
			allowBlank : false,
			columnWidth : 1
		}, {
			xtype : "widgetUserSelect",
			fieldLabel : "接收人",
			name : 'shouUser',
			multiSelect : false
		}, {
			fieldLabel : '地址',
			name : 'path',
			maxLength : 128,
			columnWidth : 1
		}, {
			fieldLabel : '参数',
			xtype : 'widgetPropertiesField',
			columnWidth : 1,
			name : 'params'
		}, {
			fieldLabel : 'JS代码',
			xtype : 'textarea',
			name : 'jsCode',
			columnWidth : 1
		} ]
	}
});