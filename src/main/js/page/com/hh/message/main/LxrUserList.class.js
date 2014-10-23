Ext.define('com.hh.message.main.LxrUserList', {
			extend : 'com.hh.global.BaseSimpleTreePanel',
			collapsible : false,
			hi_expandAll : true,
			hi_collapseAll : true,
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
			query_action : 'usersystem-user-queryCylxrTree',
			deleteCylxr : function() {
				var record = this.tree.getSelectionModel().lastFocused;
				if (Util.isNull(record)) {
					ExtFrame.msg("请选择人员！", 3000);
				} else {
					Request.synRequestObject('usersystem-user-deleteCylxr', {
								'paramsMap.cylxrid' : record.get("id")
							});
					this.tree.getStore().load();
				}
			},
			sendEmail : function() {
				var record = this.tree.getSelectionModel().lastFocused;
				if (Util.isNull(record)) {
					ExtFrame.msg("请选择人员！", 3000);
				} else {
					ExtUtil.open('com.hh.message.email.WriteEmail', {
								eobject : {
									sendUser : record.get("id")
								}
							});
				}
			},
			getExBeTbarItems : function() {
				var page = this;
				return [{
							icon : '/hhcommon/images/icons/email/email_go.png',
							text : '发送邮件',
							handler : function() {
								page.sendEmail();
							}
						}, {
							iconCls : 'delete',
							text : '删除常用联系人',
							handler : function() {
								page.deleteCylxr();
							}
						}];
			}
		});