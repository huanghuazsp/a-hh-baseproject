Ext
		.define(
				'com.hh.system.data.DataListTree',
				{
					extend : 'com.hh.global.BaseSimpleTreePanel',
					title : null,
					collapsible : false,
					query_action : 'system-Data-queryDataTreeByTypeAndPid',
					editPage : 'com.hh.system.data.DataEdit',
					delete_action : 'system-Data-deleteTreeByIds'
				});