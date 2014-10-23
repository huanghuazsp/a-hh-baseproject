Ext.define('com.hh.usersystem.user.UserEdit', {
	extend : 'com.hh.global.SimpleFormPanelWindow',
	action : 'usersystem-user-',
	title : '用户编辑',
	width : 650,
	height : 400,
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
	},
	loadData : function(id, parentRecord) {
		var page = this;
		Request.request(this.action + this.editAction, {
			id : id
		}, function(object) {
			FormPanel.setValues(page.form, object);
		});
	},
	getFormItems : function() {
		return [ {
			fieldLabel : '用户ID',
			name : 'id',
			hidden : true
		}, {
			fieldLabel : '密码',
			name : 'vmm',
			hidden : true
		}, {
			fieldLabel : '用户名称',
			name : 'text',
			maxLength : 32,
			allowBlank : false,
			columnWidth : 0.5
		}, {
			fieldLabel : '登录帐号',
			name : 'vdlzh',
			maxLength : 32,
			minLength : 2,
			columnWidth : 0.5
		}, {
			xtype : 'widgetRadioGroup',
			fieldLabel : '性别',
			name : 'nxb',
			columnWidth : 1,
			columns : 2,
			data : [ {
				"id" : 1,
				"text" : "男"
			}, {
				"id" : 0,
				"text" : "女"
			} ],
			value : 1
		}, {
			xtype : 'widgetRadioGroup',
			fieldLabel : '状态',
			name : 'nzt',
			hidden : this.config.objectId != null,
			columnWidth : 1,
			columns : 2,
			data : [ {
				"id" : 0,
				"text" : "正常"
			}, {
				"id" : 1,
				"text" : "冻结"
			} ],
			value : 0
		}, {
			fieldLabel : '电子邮件',
			name : 'vdzyj',
			vtype : 'email',
			allowBlank : false,
			maxLength : 64,
			columnWidth : 0.5
		}, {
			xtype : 'numberfield',
			fieldLabel : '电话',
			name : 'vdh',
			maxValue : 9999999999999999,
			columnWidth : 0.5
		}, {
			fieldLabel : '生日',
			name : 'dsr',
			xtype : 'datefield',
			allowBlank : false,
			maxValue : new Date(),
			format : 'Y年m月d日',
			columnWidth : 0.5
		}, {
			hidden : this.config.objectId != null,
			selectServiceType : 'gw',
			xtype : 'widgetOrgSelect'
		}, {
			xtype : 'widgetItemSelector',
			fieldLabel : '角色',
			name : 'jsList',
			hidden : this.config.objectId != null,
			submitType : 'list',
			action : 'usersystem-role-queryAllRoleList'
		} ];
	}
});