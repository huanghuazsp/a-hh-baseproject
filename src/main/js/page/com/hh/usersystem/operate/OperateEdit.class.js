Ext.define('com.hh.usersystem.operate.OperateEdit', {
	extend : 'com.hh.global.SimpleFormPanelWindow',
	action : 'usersystem-operate-',
	title : '操作编辑',
	width : 400,
	height : 160,
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
	},
	getFormItems : function() {
		var page = this;
		var vpid = null;
		if(page.config.params){
			 vpid = page.config.params.vpid;
		}
	
		return [ {
			fieldLabel : 'ID',
			name : 'id',
			hidden : true
		}, {
			fieldLabel : '操作名称',
			name : 'text',
			maxLength : 32,
			allowBlank : false,
			columnWidth : 1
		}, {
			name : 'vpid',
			columnWidth : 0.5,
			hidden : true,
			allowBlank : false,
			value : vpid
		}, {
			fieldLabel : '请求路径',
			name : 'vurl',
			columnWidth : 1,
			maxLength : 128,
			allowBlank : false
		} ];
	}
});