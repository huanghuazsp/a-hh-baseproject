Ext.define('com.hh.system.sysparam.ParamList', {
			extend : 'com.hh.global.SimpleFormPanelWindow',
			action : 'system-SysParam-',
			title : '编辑',
			constructor : function(config) {
				this.config = config || {};
				this.superclass.constructor.call(this, this.config);
				this.loadData();
			},
			loadData : function() {
				var page = this;
				Request.request(this.action + this.editAction, {}, function(
								object) {
							page.object = object;
							FormPanel.setValues(page.form, page.object);
							page.afDataLoad();
						});
			},
			getFormItems : function() {
				var page = this;
				return [{
							fieldLabel : 'ID',
							name : 'id',
							hidden : true
						}, {
							fieldLabel : '系统名称',
							name : 'sysName',
							maxLength : 32,
							labelWidth : 120,
							allowBlank : false,
							columnWidth : 1
						}, {
							name : 'sysIcon',
							xtype : 'widgetComboBoxTree',
							fieldLabel : '系统图标',
							labelWidth : 120,
							allowBlank : false,
							columnWidth : 1,
							action : 'system-ResourceFile-queryIconFilePathList',
							extraParams : {
								path : "/hhcommon/images"
							},
							selectType : 'img',
							select : 'leaf'
						}, {
							xtype : 'widgetRadioGroup',
							fieldLabel : 'SQL语句日志',
							name : 'logSql',
							labelWidth : 120,
							allowBlank : false,
							data : [{
										"id" : 1,
										"text" : "开"
									}, {
										"id" : 0,
										"text" : "关"
									}]
						}, {
							xtype : 'widgetRadioGroup',
							fieldLabel : 'SQL语句入库',
							labelWidth : 120,
							name : 'dataBaseSql',
							allowBlank : false,
							data : [{
										"id" : 1,
										"text" : "开"
									}, {
										"id" : 0,
										"text" : "关"
									}]
						}, {
							xtype : 'widgetRadioGroup',
							fieldLabel : '权限控制',
							labelWidth : 120,
							name : 'power',
							allowBlank : false,
							data : [{
										"id" : 0,
										"text" : "开"
									}, {
										"id" : 1,
										"text" : "关"
									}]
						}, {
							xtype : 'widgetRadioGroup',
							fieldLabel : '打开菜单方式',
							name : 'onePage',
							labelWidth : 120,
							allowBlank : false,
							data : [{
										"id" : 1,
										"text" : "onePage"
									}, {
										"id" : 0,
										"text" : "内嵌iframe"
									}, {
										"id" : 2,
										"text" : "弹出浏览器页签"
									}]
						}];
			}
		});