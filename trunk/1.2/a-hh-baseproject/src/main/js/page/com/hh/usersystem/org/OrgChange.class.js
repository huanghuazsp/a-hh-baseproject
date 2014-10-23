Ext.define('com.hh.usersystem.org.OrgChange', {
			extend : 'com.hh.base.BaseServicePanel',
			title : '切换身份',
			width : 400,
			height : 120,
			modal : true,
			layout : 'border',
			constructor : function(config) {
				this.config = config || {};
				this.superclass.constructor.call(this, this.config);
				var form = Ext.create('com.hh.base.BaseFormPanel', {
							url : 'usersystem-App-changeOrg',
							padding : '1',
							items : [{
										xtype : 'widgetComboBox',
										fieldLabel : '选择身份',
										name : 'currOrgId',
										action : 'usersystem-App-queryLoginOrgList',
										columnWidth : 0.8,
										allowBlank : false,
										value : static_var.currOrg.id
									}, {
										fieldLabel : '记住',
										xtype : 'checkbox',
										name : 'remember',
										columnWidth : 0.2,
										labelWidth : 40
									}],
							buttons : [{
										iconCls : 'yes',
										text : '保    存',
										handler : function() {
											var win = this.up('window');
											var callback = function(result) {
												static_var.currOrg = result;
												win.close();
												Login.show({
															jump : true
														});
											};
											FormPanel.submit(this.up('form'), {
														callback : callback
													});
										}
									}]
						});
				this.add(form);
			}
		});