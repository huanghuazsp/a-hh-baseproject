Ext.define('com.hh.system.data.DataListEdit', {
			extend : 'com.hh.global.SimpleFormPanelWindow',
			action : 'system-Data-',
			submitMethod : 'saveTree',
			title : '数据编辑',
			width : 500,
			height : 200,
			typeHidden : true,
			constructor : function(config) {
				this.config = config || {};
				this.superclass.constructor.call(this, this.config);
			},
			getFormItems : function() {
				var page = this;
				return [{
							fieldLabel : 'ID',
							name : 'id',
							hidden : true
						}, {
							fieldLabel : 'dataType',
							name : 'dataType',
							hidden : true,
							value : 1
						}, {
							fieldLabel : 'type',
							name : 'type',
							hidden : true,
							value : ''
						}, {
							fieldLabel : 'node',
							name : 'node',
							hidden : true,
							value : page.parentPanel.treeRecord.get("id")
						}, {
							fieldLabel : '名称',
							name : 'text',
							maxLength : 32,
							columnWidth : 1
						}, {
							fieldLabel : '备注',
							xtype : 'textarea',
							name : 'describe_',
							maxLength : 256,
							columnWidth : 1
						}];
			}
		});