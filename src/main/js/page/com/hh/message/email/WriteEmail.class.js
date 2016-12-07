Ext
		.define(
				'com.hh.message.email.WriteEmail',
				{
					extend : 'com.hh.global.SimpleFormPanelWindow',
					action : 'message-SendEmail-',
					title : '写信',
					submitMethod : 'sendEmail',
					width : 800,
					constructor : function(config) {
						this.config = config || {};
						this.superclass.constructor.call(this, this.config);
						if (this.eobject != null) {
							var form = this.form.getForm();
							form.findField('users').setValue(
									this.eobject.sendUser);
							if (this.eobject.title) {
								form.findField('title').setValue(
										'回复-->' + this.eobject.title);
								var tpl2 = Ext
										.create(
												'Ext.XTemplate',
												'<tpl for=".">',
												'{#}.&nbsp;&nbsp;<a href="javascript:Href.download(\'{attachmentFileName}\',\'{path}\')">{attachmentFileName}</a>',
												'</tpl>');
								form
										.findField('content')
										.setValue(
												'<div style="background-color:#efefef">'
														+ '<br/>'
														+ '<span style="margin: 0 0 5px 0px;font-size: 13px;	font-weight: bold;	width: 80px;	display: -moz-inline-box;display: inline-block;">正 文：</span>'
														+( this.eobject.content==null?"":this.eobject.content)
														+ '<span style="margin: 0 0 5px 0px;font-size: 13px;	font-weight: bold;	width: 80px;	display: -moz-inline-box;display: inline-block;">标 题：</span>'
														+ this.eobject.title
														+ '<br/>'
														+ '<span style="margin: 0 0 5px 0px;font-size: 13px;	font-weight: bold;	width: 80px;	display: -moz-inline-box;display: inline-block;">发件人：</span>'
														+ this.eobject.sendUserName
														+ '<br/>'
														+ '<span style="margin: 0 0 5px 0px;font-size: 13px;	font-weight: bold;	width: 80px;	display: -moz-inline-box;display: inline-block;">时   间：</span>'
														+ this.eobject.createTime
														+ '<br/>'
														+ '<span style="margin: 0 0 5px 0px;font-size: 13px;	font-weight: bold;	width: 80px;	display: -moz-inline-box;display: inline-block;">收件人：</span>'
														+ this.eobject.userNames
														+ '<br/>'
														+ '<span style="margin: 0 0 5px 0px;font-size: 13px;	font-weight: bold;	width: 80px;	display: -moz-inline-box;display: inline-block;">附 件：</span>'
														+ tpl2
																.apply(Ext
																		.decode(this.eobject.files))
														+ '<br/>----------------------以上为原始邮件----------------------<br/></div><br/>');
							}
						}
					},
					getBtns : function() {
						var page = this;
						return [
								{
									icon : '/hhcommon/images/icons/email/email_go.png',
									text : '发送',
									handler : function() {
										page.form.getForm().findField('type')
												.setValue("yfs");
										page.form.getForm()
												.findField('leixing').setValue(
														"0");
										FormPanel.submit(page.form, {
											afcallback : function() {
												page.closePage();
											}
										});
									}
								}, this.getBtnByType("save"),
								this.getBtnByType("cancel") ];
					},
					getFormItems : function() {
						var page = this;

						return [ {
							name : 'leixing',
							hidden : true,
							value : "1"
						}, {
							fieldLabel : '用户ID',
							name : 'id',
							hidden : true
						}, {
							fieldLabel : 'type',
							name : 'type',
							hidden : true,
							value : "cgx"
						}, {
							xtype : "widgetUserSelect",
							fieldLabel : "收件人",
							name : 'users'
						}, {
							fieldLabel : '标题',
							name : 'title',
							maxLength : 64,
							allowBlank : false,
							columnWidth : 1
						}, {
							xtype : 'widgetFileGridField',
							fieldLabel : '附件',
							name : 'files',
							filePath : 'task/sys_send_email#sys_shou_email/files',
							columnWidth : 1
						}, {
							xtype : 'widgetCkeditor',
							fieldLabel : '内容',
							name : 'content',
							columnWidth : 1
						} ];
					}
				});