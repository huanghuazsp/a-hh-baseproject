Ext
		.define(
				'com.hh.usersystem.org.OrgEdit',
				{
					extend : 'com.hh.global.SimpleFormPanelWindow',
					action : 'usersystem-Org-',
					title : '机构编辑',
					width : 600,
					height : 400,
					constructor : function(config) {
						this.config = config || {};
						this.superclass.constructor.call(this, this.config);
					},
					afDataLoad : function() {
						this.setCodeValue(this);
					},
					bfSubmitForm : function() {
						if (this.form.getForm().findField('code_').getValue() == this.form
								.getForm().findField('sjbm_').getValue()) {
							ExtFrame.info("不能选自己为上级！");
							return false;
						}
					},
					setCodeValue : function(page) {
//						page.form
//								.getForm()
//								.findField('code_')
//								.setValue(
//										page.form
//												.getForm()
//												.findField('code_')
//												.getValue()
//												.replace(
//														page.form
//																.getForm()
//																.findField(
//																		'sjbm_')
//																.getValue(), ''));
					},
					submitForm : function(callbackObject) {
						var page = this;
						var callback = function(result) {
							page.setCodeValue(page);
							if (callbackObject) {
								callbackObject.afcallback(result);
							}
						}
						var bereturn = this.bfSubmitForm();
						if (bereturn != false) {
							FormPanel.submit(this.form, {
								afcallback : callback
							});
						}
					},
					getFormItems : function() {
						var page = this;
						var prent_record = page.config.parentPanel.prent_record;
						if (page.config.selectRecord) {
							prent_record = page.config.selectRecord;
						}
						if (prent_record == null) {
							prent_record = {
								raw : {}
							};
						}
						var recod = page.config.parentPanel.tree
								.getSelectionModel().getSelection();
						var lx_ = page.config.parentPanel.lx_;

						var uuid = UUID.getUUID();
						var code = uuid.substr(uuid.length - 3);

						var formItems = [
								{
									fieldLabel : 'ID',
									name : 'id',
									hidden : true
								},
								{
									fieldLabel : '类型',
									name : 'lx_',
									hidden : true,
									value : lx_
								},
								{
									fieldLabel : '名称',
									name : 'text',
									maxLength : 32,
									allowBlank : false,
									columnWidth : 1
								},
								{
									fieldLabel : '自定义编码',
									name : 'zdybm_',
									maxLength : 16,
									vtype : 'alphanum',
									columnWidth : 0.5
								},
								{
									fieldLabel : '简称',
									name : 'jc_',
									maxLength : 8,
									columnWidth : 0.5
								},
								{
									fieldLabel : '上级编码',
									name : 'sjbm_',
									readOnly : true,
									columnWidth : 0.5,
									value : prent_record.raw.code_
								},
								{
									fieldLabel : '编码',
									name : 'code_',
									maxLength : 3,
									minLength : 3,
									allowBlank : false,
									columnWidth : 0.5,
									vtype : 'alphanum',
									value : code
								},
								{
									xtype : 'widgetRadioGroup',
									fieldLabel : '状态',
									columnWidth : 0.5,
									allowBlank : false,
									name : 'state',
									data : [ {
										"id" : 0,
										"text" : "正常"
									}, {
										"id" : 1,
										"text" : "冻结"
									} ],
									value : 0
								},
								{
									xtype : 'widgetRadioGroup',
									fieldLabel : '是否展开',
									columnWidth : 0.5,
									allowBlank : false,
									name : 'expanded',
									data : [ {
										"id" : 1,
										"text" : "是"
									}, {
										"id" : 0,
										"text" : "否"
									} ],
									value : 0
								},
								/*
								 * { name : 'icon', xtype :
								 * 'widgetComboBoxTree', fieldLabel : '图标',
								 * columnWidth : 0.5, action :
								 * 'system-ResourceFile-queryIconFilePathList',
								 * extraParams : { path : "/hhcommon/images" },
								 * selectType : 'img', select : 'leaf' },
								 */
								{
									name : 'node',
									xtype : 'widgetComboBoxTree',
									fieldLabel : '上级',
									tableName : 'US_ORGANIZATION',
									allowBlank : true,
									columnWidth : 1,
									action : 'usersystem-Org-queryOrgListByPid',
									value : prent_record.raw.id,
									afDeleteData : function() {
										this.up('form').getForm().findField(
												'sjbm_').setValue('');
									},
									beitemclick : function(view, record) {
//										if (record.raw.lx_ == lx_
//												|| record.raw.lx_ == lx_ - 1) {
											this.up('form').getForm()
													.findField('sjbm_')
													.setValue(record.raw.code_);
											return true;
//										} 
//										return true;
//										else {
//											if (lx_ == 0) {
//												ExtFrame.info("请选择集团！");
//											} else if (lx_ == 1) {
//												ExtFrame.info("请选择集团或者机构！");
//											} else if (lx_ == 2) {
//												ExtFrame.info("请选择机构或者部门！");
//											} else if (lx_ == 3) {
//												ExtFrame.info("请选择部门或者岗位！");
//											}
//											return;
//										}
									}
								}, {
									xtype : 'widgetItemSelector',
									fieldLabel : '角色',
									name : 'jsList',
									submitType : 'list',
									action : 'usersystem-role-queryAllRoleList'
								}, {
									fieldLabel : '描述',
									xtype : 'textarea',
									name : 'ms_',
									maxLength : 256,
									columnWidth : 1
								} ];
						return formItems;
					}
				});