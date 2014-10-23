Ext
		.define(
				'com.hh.message.email.ShouEmailList',
				{
					extend : 'com.hh.global.SimpleGridPanelWindow',
					action : 'message-ShouEmail-',
					editPage : 'com.hh.message.email.ShouEmail',
					constructor : function(config) {
						this.config = config || {};
						this.superclass.constructor.call(this, this.config);
					},
					hi_add : true,
					updateText : '查看',
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
									dataIndex : 'dcreate',
									renderer : Ext.util.Format
											.dateRenderer('Y年m月d日 H时i分s秒'),
									flex : 1
								},
								{
									text : '状态',
									dataIndex : 'read',
									flex : 1,
									renderer : function(value, p, data) {
										return value == 0 ? '<font color=red>未读</font>'
												: '<font color=green>已读</font>';
									}
								},
								{
									text : '状态',
									dataIndex : 'type',
									renderer : Ext.util.Format
											.dateRenderer('Y年m月d日 H时i分s秒'),
									flex : 1,
									renderer : function(value, p, data) {
										return value == 'zc' ? '<font color=green>正常</font>'
												: '<font color=red>不知道</font>';
									}
								}];
					},
					getStoreFields : function() {
						return [ 'id', 'title', 'content', 'dcreate', 'users',
								'type', 'read' ];
					}
				});