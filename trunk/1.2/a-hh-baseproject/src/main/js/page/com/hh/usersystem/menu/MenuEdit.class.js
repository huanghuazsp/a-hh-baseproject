Ext
		.define(
				'com.hh.usersystem.menu.MenuEdit',
				{
					extend : 'com.hh.global.SimpleFormPanelWindow',
					action : 'usersystem-menu-',
					title : '菜单编辑',
					width : 600,
					height : 400,
					constructor : function(config) {
						this.config = config || {};
						this.superclass.constructor.call(this, this.config);
					},
					getFormItems : function() {
						var page = this;
						var vpid = '';
						if (page.config.parentPanel.defaultParentNode != null) {
							vpid = page.config.parentPanel.defaultParentNode
									.get("id");
						}
						if (page.config.selectRecord
								&& page.config.addType != 'root') {
							vpid = page.config.selectRecord.get("id");
						}
						return [
								{
									fieldLabel : '菜单名称',
									name : 'text',
									maxLength : 32,
									allowBlank : false,
									columnWidth : 0.5
								},
								{
									name : 'node',
									xtype : 'widgetComboBoxTree',
									fieldLabel : '父菜单',
									tableName : 'hh_xt_cd',
									allowBlank : true,
									columnWidth : 0.5,
									value : page.config.parentRecord == null ? vpid
											: '',
									action : 'usersystem-menu-queryMenuListByPid'
								},
								{
									name : 'icon',
									xtype : 'widgetComboBoxTree',
									fieldLabel : '小图标',
									allowBlank : false,
									columnWidth : 1,
									action : 'system-ResourceFile-queryIconFilePathList',
									extraParams : {
										path : "/hhcommon/images"
									},
									selectType : 'img',
									select : 'leaf'
								},
								{
									name : 'vdtp',
									xtype : 'widgetComboBoxTree',
									fieldLabel : '桌面',
									allowBlank : false,
									columnWidth : 1,
									action : 'system-ResourceFile-queryIconFilePathList',
									extraParams : {
										path : "/hhcommon/images/big"
									},
									selectType : 'img',
									select : 'leaf'
								},
								{
									fieldLabel : '备注',
									xtype : 'textarea',
									name : 'vbz',
									maxLength : 256,
									columnWidth : 1
								},
								{
									fieldLabel : '参数',
									xtype : 'widgetPropertiesField',
									columnWidth : 1,
									name : 'params'
								},
								{
									xtype : 'widgetComboBoxTree',
									fieldLabel : '动作',
									editable : true,
									name : 'vsj',
									submitType : 'text',
									textName : 'vsj',
									idName : 'vsj',
									action : 'usersystem-menu-queryAllMenuList',
									allowBlank : false,
									maxLength : 128,
									columnWidth : 1,
									hidden : page.config.parentPanel.menu_leaf == 0,
									value : page.config.parentPanel.menu_leaf == 0 ? 'com.hh.global.NavigAtionWindow'
											: ''
								},
								{
									xtype : 'widgetRadioGroup',
									fieldLabel : '是否展开',
									allowBlank : false,
									hidden : page.config.parentPanel.menu_leaf != 1,
									name : 'expanded',
									value : 0,
									data : [ {
										"id" : 1,
										"text" : "是"
									}, {
										"id" : 0,
										"text" : "否"
									} ]
								}, {
									fieldLabel : 'ID',
									name : 'id',
									hidden : true
								}, {
									fieldLabel : '是否叶子',
									name : 'leaf',
									hidden : true,
									value : page.config.parentPanel.menu_leaf==1?0:1
								} ];
					}
				});