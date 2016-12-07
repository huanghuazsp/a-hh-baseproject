Ext.define('com.hh.message.email.SendEmailList', {
	extend : 'com.hh.global.SimpleGridPanelWindow',
	action : 'message-SendEmail-',
	editPage : 'com.hh.message.email.WriteEmail',
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
	},
	addText : '写信',
	getGridColumns : function() {
		return [
				{
					dataIndex : 'id',
					flex : 1,
					hidden : true
				},
				{
					text : '标题',
					dataIndex : 'title',
					flex : 1
				},
				{
					text : '时间',
					dataIndex : 'createTime',
					renderer : Ext.util.Format.dateRenderer('Y年m月d日 H时i分s秒'),
					flex : 1
				},
				{
					text : '状态',
					dataIndex : 'type',
					flex : 1,
					renderer : function(value, p, data) {
						return value == 'yfs' ? '<font color=red>已发送</font>'
								: '<font color=green>未发送</font>';
					}
				}, this.getOperateGridColumn() ];
	},
	getStoreFields : function() {
		return [ 'id', 'title', 'content', 'createTime', 'users', 'type' ];
	}
});