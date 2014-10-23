Ext.define('com.hh.system.desktop.Theme', {
	extend : 'Ext.window.Window',
	maximizable : true,
	layout : 'fit',
	title : '设置主题',
	iconCls : 'hh_zt',
	modal : true,
	width : 700,
	height : 420,
	border : false,

	initComponent : function() {
		var me = this;
		me.selected = '/hhcommon/opensource/ext/desktop/theme/ext-theme-neptune.jpg';
		if (static_var.loginuser.hhXtZmsx.theme) {
			me.selected = '/hhcommon/opensource/ext/desktop/theme/'
					+ static_var.loginuser.hhXtZmsx.theme + '.jpg';
		}

		me.submitValue = static_var.loginuser.hhXtZmsx.theme;

		me.preview = Ext.create('widget.wallpaper', {
			html : '<img src="/hhcommon/opensource/ext/desktop/theme/ext-theme-neptune.jpg">'
		});
		me.preview.setWallpaper(me.selected);
		me.tree = me.createTree();

		me.buttons = [{
					text : '确定',
					iconCls : 'yes',
					handler : me.onOK,
					scope : me
				}, {
					text : '取消',
					iconCls : 'cancel',
					handler : me.close,
					scope : me
				}];
		me.items = [{
					border : false,
					layout : 'border',
					items : [me.tree, {
								xtype : 'panel',
								title : '预览',
								region : 'center',
								layout : 'fit',
								items : [me.preview]
							}]
				}];
		me.callParent();
	},
	createTree : function() {
		var me = this;

		function child(id, img) {
			return {
				img : img,
				text : me.getTextOfWallpaper(img),
				iconCls : '',
				id : id,
				leaf : true
			};
		}

		var tree = new Ext.tree.Panel({
					title : '主题',
					rootVisible : false,
					lines : false,
					autoScroll : true,
					width : 150,
					region : 'west',
					split : true,
					minWidth : 100,
					listeners : {
						afterrender : {
							fn : this.setInitialSelection,
							delay : 100
						},
						select : this.onSelect,
						scope : this
					},
					store : new Ext.data.TreeStore({
								root : {
									text : 'Wallpaper',
									expanded : true,
									children : [child('ext-theme-neptune', '默认.jpg'),
											child('ext-theme-classic', '蓝.jpg'),
											child('ext-theme-gray', '白.jpg'),
											child('ext-theme-access', '黑.jpg')]
								}
							})
				});

		return tree;
	},

	getTextOfWallpaper : function(path) {
		var text = path, slash = path.lastIndexOf('/');
		if (slash >= 0) {
			text = text.substring(slash + 1);
		}
		var dot = text.lastIndexOf('.');
		text = Ext.String.capitalize(text.substring(0, dot));
		text = text.replace(/[-]/g, ' ');
		return text;
	},

	onOK : function() {
		var me = this;
		var resultObject = Request.synRequestObject(
				'usersystem-zmsx-updateTheme', {
					theme : me.submitValue
				});
		if (resultObject.success == true) {
			me.destroy();
			Login.show({
						jump : true
					});
		}
	},
	submitValue : '',
	onSelect : function(tree, record) {
		var me = this;

		if (record.data.id) {
			me.submitValue = record.data.id;
			me.selected = '/hhcommon/opensource/ext/desktop/theme/'
					+ record.data.id + '.jpg';
		} else {
			me.selected = Ext.BLANK_IMAGE_URL;
		}

		me.preview.setWallpaper(me.selected);
	},
	setInitialSelection : function() {
	}
});
