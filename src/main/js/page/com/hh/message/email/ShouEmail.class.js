Ext
		.define(
				'com.hh.message.email.ShouEmail',
				{
					extend : 'com.hh.global.SimpleFormPanelWindow',
					action : 'message-ShouEmail-',
					title : '收件',
					submitMethod : 'sendEmail',
					width : 800,
					constructor : function(config) {
						this.config = config || {};
						this.superclass.constructor.call(this, this.config);
					},
					getBtns : function() {
						var page = this;
						return [
								{
									icon : '/hhcommon/images/icons/email/email_go.png',
									text : '回复',
									handler : function() {
										ExtUtil
												.open(
														'com.hh.message.email.WriteEmail',
														{
															eobject : page.object
														});
										page.closePage();
									}
								}, this.getBtnByType("cancel") ];
					},
					loadData : function(id, parentRecord) {
						var page = this;
						Request.request(this.action + this.editAction, {
							id : id
						}, function(object) {
							page.object = object;
							FormPanel.setValues(page.form, page.object);
							page.afDataLoad();
						});
					},
					afDataLoad : function() {
						var fileUUID = UUID.getUUID();
						this.object.createTime = Ext.util.Format.date(
								this.object.createTime, "Y年m月d日 H时i分s秒")
						var tpl = Ext
								.create(
										'Ext.Template',
										'<h1>{title}</h1>',
										'<span class=labelText>发件人：</span>{sendUserName}<br/>',
										'<span class=labelText>时   间：</span>{createTime}<br/>',
										'<span class=labelText>收件人：</span>{userNames}<br/>',
										fileUUID,
										'<hr/>',
										'<span class=labelText>正文：</span><p>{content}</p>',
										'<hr/>');
						var tpl2 = Ext
								.create(
										'Ext.XTemplate',
										'<span class=labelText>附件：</span>',
										'<tpl for=".">',
										'{#}.&nbsp;&nbsp;<a href="javascript:Href.download(\'{attachmentFileName}\',\'{path}\')">{attachmentFileName}</a>',
										'</tpl>');
						var html = tpl.apply(this.object).replace(fileUUID,
								tpl2.apply(Ext.decode(this.object.files)));
						this.fieldset.update(html);
					},
					getFormItems : function() {
						var page = this;
						page.fieldset = Ext.create("Ext.form.FieldSet", {
							xtype : 'fieldset',
							title : '收件',
							columnWidth : 1
						});
						return [ page.fieldset ];
					}
				});