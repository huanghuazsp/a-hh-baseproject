Ext.define('com.hh.system.desktop.Settings', {
	extend : 'Ext.window.Window',
	layout : 'fit',
	title : '设置桌面属性',
	iconCls : 'hh_zmbj',
	modal : true,
	width : 700,
	height : 420,
	border : false,
	initComponent : function() {
		var me = this;
		me.selected = me.desktop.getWallpaper();
		me.stretch = me.desktop.wallpaper.stretch;

		me.preview = Ext.create('widget.wallpaper');
		me.preview.setWallpaper(me.selected);
		me.tree = me.createTree();

		me.buttons = [ {
			text : '确定',
			iconCls : 'yes',
			handler : me.onOK,
			scope : me
		}, {
			text : '取消',
			iconCls : 'cancel',
			handler : me.close,
			scope : me
		} ];

		me.items = [ {
			// anchor : '0 -30',
			border : false,
			layout : 'border',
			items : [ me.tree, {
				xtype : 'panel',
				title : '预览',
				region : 'center',
				layout : 'fit',
				items : [ me.preview ]
			} ]
		} /*
			 * , { xtype : 'checkbox', boxLabel : '拉伸', checked : me.stretch,
			 * listeners : { change : function(comp) { me.stretch =
			 * comp.checked; } } }
			 */];

		me.callParent();
	},

	createTree : function() {
		var me = this;

		function child(img) {
			return {
				img : img,
				text : me.getTextOfWallpaper(img),
				iconCls : '',
				leaf : true
			};
		}

		var tree = new Ext.tree.Panel({
			title : '桌面背景',
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
				model : 'MyDesktop.WallpaperModel',
				root : {
					text : 'Wallpaper',
					expanded : true,
					children : [ {
						text : "None",
						iconCls : '',
						leaf : true
					}, child('Blue-Sencha.jpg'), child('Dark-Sencha.jpg'),
							child('Wood-Sencha.jpg'), child('blue.jpg'),
							child('desk.jpg'), child('desktop.jpg'),
							child('desktop2.jpg'), child('sky.jpg'),
							child('鸭子.jpg') ]
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
		var resultObject = Request.synRequestObject( 'usersystem-zmsx-updateZmbj',{
			vzmbj : me.selected
		});
		if (resultObject.success == true) {
			if (me.selected) {
				me.desktop.setWallpaper(me.selected, me.stretch);
			}
			me.destroy();
		}
	},

	onSelect : function(tree, record) {
		var me = this;

		if (record.data.img) {
			me.selected = '/hhcommon/opensource/ext/desktop/wallpapers/'
					+ record.data.img;
		} else {
			me.selected = Ext.BLANK_IMAGE_URL;
		}

		me.preview.setWallpaper(me.selected);
	},

	setInitialSelection : function() {
		var s = this.desktop.getWallpaper();
		if (s) {
			var path = '/hhcommon/opensource/ext/desktop/Wallpaper/'
					+ this.getTextOfWallpaper(s);
			this.tree.selectPath(path, 'text');
		}
	}
});
