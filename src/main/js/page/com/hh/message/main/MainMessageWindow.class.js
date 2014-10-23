$import(["com.hh.message.main.OrgAndUserList",
		"com.hh.message.main.LxrUserList", "com.hh.message.message.MessageList"]);
Ext.define('com.hh.message.main.MainMessageWindow', {
	extend : 'com.hh.base.BaseServicePanel',
	title : '工具箱',
	width : 300,
	height : Browser.getHeight() - 100,
	layout : 'border',
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
		var cylxrsPanel = Ext.create("com.hh.message.main.LxrUserList");

		var messageList = Ext.create("com.hh.message.message.MessageList", {
					hi_crightMenu : true,
					hi_rightMenu : true,
					hi_bbar : true,
					getToolbarItem : function(type) {
						var page = this;
						if (type == "view") {
							return {
								iconCls : 'view',
								text : '查看',
								handler : function() {
									page.doView();
								}
							};
						}
						return this.callParent(arguments);
					},
					getTbarItems : function() {
						return [this.getToolbarItem("view")];
					},
					getGridColumns : function() {
						return [{
									text : '标题',
									dataIndex : 'title',
									width : 200
								}, {
									text : '已读',
									dataIndex : 'isRead',
									width : 40,
									renderer : GridPanel.getBool()
								}];
					},
					doView : function() {
						var record = this.getSelectRow();
						if (record == null) {
							ExtFrame.msg("请选中一条数据！！");
							return;
						}
						var page = this;
						Request.request('message-SysMessage-updateRead', {
									id : record.get('id')
								}, function(result) {
									if (record.get('path')) {
										var param = {};
										if (record.get('params')) {
											param = Ext.decode(record
													.get('params'));
										}

										if (record.get('path').substr(0, 3) != 'com') {
											param.vsj = record.get('path');
											return Desktop
													.openWindow(
															'com.hh.global.BaseIframeWindow',
															param);
										} else {
											ExtUtil.open(record.get('path'),
													param);
										}
									}
									if (record.get('jsCode')) {
										eval("(" + record.get('jsCode') + ")");
									}
									page.refresh();
								});
					}
				});
		var form = Ext.create('Ext.panel.Panel', {
			region : 'center',
//			title : '      ',
//			icon : '/hhcommon/images/icons/comment.png',
			layout : {
				type : 'accordion',
				animate : true
			},
			items : [{
						title : '我的消息',
						border : false,
						layout : 'fit',
						icon : '/hhcommon/images/icons/comment.png',
						items : [messageList]
					}, {
						title : '常用联系人',
						border : false,
						iconCls : 'user',
						layout : 'fit',
						listeners : {
							'expand' : function(panel) {
								if (panel.items.items.length == 0) {
									panel.add(cylxrsPanel);
								}
							}
						}
						// items : [cylxrsPanel]
				}	, {
						title : '组织机构',
						border : false,
						icon : '/hhcommon/images/extjsico/application_group.png',
						layout : 'fit',
						listeners : {
							'expand' : function(panel) {
								if (panel.items.items.length == 0) {
									var orgTreePanel = Ext
											.create(
													"com.hh.message.main.OrgAndUserList",
													{
														cylxrsPanel : cylxrsPanel
													});
									panel.add(orgTreePanel);
								}
							}
						}
						// items : [orgTreePanel]
					}]
		});

//		var mainTab = Ext.create("com.hh.base.BaseTabPanel", {
//					padding : '1',
//					region : 'center'
//				});
//
//		mainTab.add(form);
//		mainTab.setActiveTab(form);

		this.add(form);
	}
});