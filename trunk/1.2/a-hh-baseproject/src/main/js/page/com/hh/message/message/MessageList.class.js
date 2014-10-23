Ext.define('com.hh.message.message.MessageList', {
			extend : 'com.hh.global.SimpleGridPanelWindow',
			action : 'message-SysMessage-',
			gridAction : 'queryPagingData',
			editPage : 'com.hh.message.message.MessageEdit',
			constructor : function(config) {
				this.config = config || {};
				this.superclass.constructor.call(this, this.config);
			},
			getGridColumns : function() {
				return [{
							text : '标题',
							dataIndex : 'title',
							flex : 1
						}, {
							text : '地址',
							dataIndex : 'path',
							flex : 1
						}, {
							text : 'js代码',
							dataIndex : 'jsCode',
							flex : 1
						}, {
							text : '参数',
							dataIndex : 'params',
							flex : 1
						}, {
							text : '已读',
							dataIndex : 'isRead',
							width : 40,
							renderer : GridPanel.getBool()
						}];
			},
			getStoreFields : function() {
				return ['title', 'path', 'jsCode', 'params', 'isRead'];
			}
		});