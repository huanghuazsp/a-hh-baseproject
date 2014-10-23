Ext
		.define(
				'com.hh.usersystem.role.RoleEdit',
				{
					extend : 'com.hh.global.SimpleFormPanelWindow',
					action : 'usersystem-role-',
					title : '角色编辑',
					width : 900,
					height : 400,
					constructor : function(config) {
						this.config = config || {};
						this.superclass.constructor.call(this, this.config);
					},
					afSbmitForm : function(result) {
						if (result.isSuccess) {
							if (this.isAfDataLoad == null) {
								this.afDataLoad();
							}
						}
					},
					afDataLoad : function() {
						this.isAfDataLoad = true;
						var page = this;
						var tree2 = Ext
								.create(
										'com.hh.global.SimpleTreePanel',
										{
											region : 'west',
											padding : '0',
											getTreePanelListeners : function() {
												return {
													checkchange : function(
															node, checked,
															eOpts) {
														var roleid = page.form
																.getForm()
																.findField("id")
																.getValue();
														if (roleid) {
															if (!(node.data.leaf)) {
																Request
																		.synRequestObject(
																				'usersystem-role-insertOrdeleteOperate',
																				{
																					'paramsMap.roleid' : roleid,
																					'paramsMap.menuid' : node
																							.get('id'),
																					'paramsMap.checked' : node
																							.get('checked')
																				});
															} else {
																if (node.parentNode.data.checked) {
																	var childNodes = node.parentNode.childNodes;
																	for ( var i = 0; i < childNodes.length; i++) {
																		if (childNodes[i].data.id != node.data.id) {
																			childNodes[i]
																					.set(
																							'checked',
																							false);
																		}
																	}

																	Request
																			.synRequestObject(
																					'usersystem-role-updateOperateLevel',
																					{
																						'paramsMap.roleid' : roleid,
																						'paramsMap.menuid' : node.parentNode.data.id,
																						'paramsMap.operateLevel' : node.data.id
																								.substr(node.data.id
																										.indexOf('_') + 1),
																						'paramsMap.checked' : node
																								.get('checked')
																					});

																} else {
																	ExtFrame
																			.info("请选择操作，再设置操作级别！");
																	node
																			.set(
																					'checked',
																					!checked);
																}
															}
														} else {
															ExtFrame
																	.info("请先保存角色，再分配权限！");
															node.set('checked',
																	!checked);
														}
													}
												};
											},
											title : '操作设置',
											query_action : 'usersystem-operate-queryCheckOperateListByPid',
											getTbar : function() {
												return null;
											},
											extraParams : {
												roleid : page.object.id
											}
										});

						var tree = Ext
								.create(
										'com.hh.global.SimpleTreePanel',
										{
											region : 'west',
											padding : '0',
											getTreePanelListeners : function() {
												return {
													checkchange : function(
															node, checked,
															eOpts) {
														var roleid = page.form
																.getForm()
																.findField("id")
																.getValue();
														if (roleid) {
															Request
																	.synRequestObject(
																			'usersystem-role-insertOrdeleteMenu',
																			{
																				'paramsMap.roleid' : roleid,
																				'paramsMap.menuid' : node
																						.get('id'),
																				'paramsMap.checked' : node
																						.get('checked')
																			});
														} else {
															ExtFrame
																	.info("请先保存角色，再分配权限！");
															node.set('checked',
																	!checked);
														}
													},
													itemclick : function(view,
															record) {
														Ext
																.apply(
																		tree2.store.proxy.extraParams,
																		{
																			vpid : record
																					.get("id")
																		});
														tree2.store.load();
													}
												};
											},
											title : '菜单设置',
											query_action : 'usersystem-menu-queryMenuAllListByPid',
											getTbar : function() {
												return null;
											},
											extraParams : {
												roleid : page.object.id
											},
											collapsible : false,
											getBbar : function() {
												return Ext
														.create(
																'com.hh.base.BaseToolbar',
																{
																	enableOverflow : true,
																	items : [
																			{
																				iconCls : 'expand',
																				text : '全部展开',
																				handler : function() {
																					tree.tree
																							.collapseAll();
																					tree.tree
																							.expandAll();
																				}
																			},
																			{
																				iconCls : 'collapse',
																				text : '全部收缩',
																				handler : function() {
																					tree.tree
																							.collapseAll();
																				}
																			},
																			'->',
																			{
																				iconCls : 'table_refresh',
																				text : '刷新',
																				handler : function() {
																					tree.store
																							.load();
																				}
																			} ]
																});
											}
										});

						this.add(tree);
						this.add(tree2);
					},
					getFormItems : function() {
						return [ {
							fieldLabel : 'ID',
							name : 'id',
							hidden : true
						}, {
							fieldLabel : '角色名称',
							name : 'text',
							maxLength : 32,
							allowBlank : false,
							columnWidth : 1
						}, {
							xtype : 'widgetRadioGroup',
							fieldLabel : '状态',
							name : 'nzt',
							data : [ {
								"id" : 0,
								"text" : "正常"
							}, {
								"id" : 1,
								"text" : "冻结"
							} ],
							value : 0
						}, {
							xtype : 'widgetRadioGroup',
							fieldLabel : '类型',
							name : 'nlx',
							columnWidth : 1,
							data : [ {
								"id" : 1,
								"text" : "业务角色"
							}, {
								"id" : 2,
								"text" : "管理角色"
							}, {
								"id" : 3,
								"text" : "系统内置角色"
							} ],
							value : 1
						}, {
							fieldLabel : '备注',
							xtype : 'textarea',
							name : 'vbz',
							maxLength : 256,
							columnWidth : 1
						} ];
					}
				});