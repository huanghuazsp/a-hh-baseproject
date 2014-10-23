Ext.define('com.hh.usersystem.user.ChangePassword', {
	extend : 'com.hh.base.BaseServicePanel',
	title : '修改密码',
	width : 300,
	height : 200,
	layout : 'border',
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
		var page = this;
		var form = Ext.create('com.hh.base.BaseFormPanel', {
			url : 'usersystem-user-updatePassWord',
			submitForm : function() {
				var callback = function(result) {
					var win = this.up('window');
					win.close();
				};
				FormPanel.submit(this.form,  {afcallback:callback});
			},
			padding : '1',
			items : [ {
				name : 'id',
				hidden : true,
				value : config.objectId
			}, {
				fieldLabel : '旧密码',
				columnWidth : 1,
				inputType : 'password',
				allowBlank : false,
				name : 'oldPassword'
			}, {
				fieldLabel : '新密码',
				columnWidth : 1,
				inputType : 'password',
				allowBlank : false,
				name : 'vmm'
			}, {
				fieldLabel : '重复新密码',
				columnWidth : 1,
				inputType : 'password',
				allowBlank : false,
				vtype : 'equals',
				name : 'mm'
			} ],
			buttons : [ {
				iconCls : 'yes',
				text : '保    存',
				handler : function() {
					var win = this.up('window');
					var callback = function(result) {
						if(result.returnModel==null){
							win.close();
						}
					};
					FormPanel.submit(this.up('form'),  {afcallback:callback});
				}
			}, {
				iconCls : 'cancel',
				text : '取    消',
				handler : function() {
					this.up('window').close();
				}
			} ]
		});
		this.add(form);
	}
});