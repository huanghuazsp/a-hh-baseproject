Ext.define('com.hh.system.data.DataTree', {
			extend : 'com.hh.base.BaseServicePanel',
			action : 'system-Data-',
			submitMethod : 'saveTree',
			title : '编辑',
			width : 500,
			constructor : function(config) {
				this.config = config || {};
				this.superclass.constructor.call(this, this.config);
				this.myinit();
			},
			myinit : function() {
				var dataListTree = ExtUtil.create(
						'com.hh.system.data.DataListTree', {
							region : 'center',
							typeHidden : false,
							query_action : 'system-Data-queryDataTreeByPid'
						});
				this.add(dataListTree);
			}
		});