Ext.define('com.hh.usersystem.user.UserList', {
			extend : 'com.hh.global.SimpleGridPanelWindow',
			action : 'usersystem-user-',
			editPage : 'com.hh.usersystem.user.UserEdit',
			hi_up : false,
			hi_down : false,
			constructor : function(config) {
				this.config = config || {};
				this.superclass.constructor.call(this, this.config);
			},
			getExtraParams:function(){
				return {nxb:2};
			},
			getSelectTbarItems : function() {
				return [{
							fieldLabel : '用户名称',
							name : 'text'
						}, {
							xtype : 'widgetRadioGroup',
							fieldLabel : '性别',
							labelWidth : 50,
							width : 250,
							name : 'nxb',
							value:2,
							data : [{
										"id" : 1,
										"text" : "男"
									}, {
										"id" : 0,
										"text" : "女"
									}, {
										"id" : 2,
										"text" : "所有"
									}]
						}]
			},
			getGridColumns : function() {
				return [{
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
								return value == 0
										? '正常'
										: '<font color=red>冻结</font>';
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
						}, this.getOperateGridColumn()];
			},
			getStoreFields : function() {
				return ['id', 'text', 'nxb', 'nzt', 'vdzyj', 'vdh', 'dsr'];
			}
		});