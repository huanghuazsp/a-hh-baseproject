Ext.define('com.hh.message.main.OrgAndUserList', {
			extend : 'com.hh.global.BaseSimpleTreePanel',
			collapsible : false,
			constructor : function(config) {
				this.config = config || {};
				this.superclass.constructor.call(this, this.config);
			},
			getTreePanelListeners : function() {
				return {
					itemclick : function(view, record) {

					}
				};
			},
			query_action : 'usersystem-Org-queryOrgAndUsersList',
			getExBeTbarItems : function() {
				var page = this;
				return [{
							icon : '/hhcommon/images/icons/email/email_go.png',
							text : '发送邮件',
							handler : function() {
								page.sendEmail();
							}
						}, {
							iconCls : 'add',
							text : '添加到常用联系人',
							handler : function() {
								page.addcylxr();
							}
						}];
			},
			addcylxr : function() {
				var record = this.tree.getSelectionModel().lastFocused;
				if (Util.isNull(record)) {
					ExtFrame.msg("请选择人员！", 3000);
					return;
				}
				if (!record.get('leaf')) {
					ExtFrame.msg("请选择人员！", 3000);
				} else {
					Request.synRequestObject('usersystem-user-addCylxr', {
								'paramsMap.cylxrid' : record.get("id")
							});
					this.cylxrsPanel.tree.getStore().load();
				}
			},
			sendEmail : function() {
				var record = this.tree.getSelectionModel().lastFocused;
				if (Util.isNull(record)) {
					ExtFrame.msg("请选择人员或机构！", 3000);
					return;
				}
				if (!record.get('leaf')) {
					Request.request('usersystem-user-queryUserByOrcCode', {
								code : record.raw.code_
							}, function(result) {
								var useridstrs = Util.objectListToStr(result,
										'id');
								ExtUtil.open('com.hh.message.email.WriteEmail',
										{
											eobject : {
												sendUser : useridstrs
											}
										});
							});
				} else {
					ExtUtil.create('com.hh.message.email.WriteEmail', {
								eobject : {
									sendUser : record.get("id")
								}
							}).show();
				}
			}
		});