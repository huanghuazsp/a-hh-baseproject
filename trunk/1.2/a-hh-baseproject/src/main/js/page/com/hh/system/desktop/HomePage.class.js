Ext.define('com.hh.system.desktop.HomePage', {
	extend : 'com.hh.base.BaseServicePanel',
	title : null,
	bodyStyle : "background-color:#ffffff;",
	showKjfs : function() {
		var page = this;
		ExtUtil.open('com.hh.usersystem.menu.ZmtbEdit', {
					desktopStore : page.store
				}, {
					modal : true
				});
	},
	constructor : function(config) {
		this.config = config || {};

		this.tbar = [{
					text : '快捷方式设置',
					handler : this.showKjfs,
					scope : this,
					iconCls : 'kjfs'
				}];
		this.superclass.constructor.call(this, this.config);
		var storeCfg = {
			fields : ['id', 'text', 'vsj', 'icon', 'leaf', 'params', 'vdtp'],
			data : static_var.loginuser.desktopQuickList
		};
		this.store = Ext.create('com.hh.base.BaseDataStore', storeCfg);
		var view = Ext.create("Ext.view.View", {
			singleSelect : true,
			store : this.store,
			region : 'center',
			overItemCls : 'x-view-over',
			itemSelector : 'div.thumb-wrap',
			tpl : [
					'<tpl for=".">',
					'<div class="thumb-wrap">',
					'<div class="thumb">',
					(!Ext.isIE6
							? '<img src="{vdtp}"  title="{text}" />'
							: '<div style="width:74px;height:74px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\'{vdtp}\')"></div>'),
					'</div>', '<span><strong>{text}</strong></span>', '</div>',
					'</tpl>']
		});
		view.on('itemdblclick', this.onShortcutItemClick, this);
		this.add(view);
	},
	onShortcutItemClick : function(dataView, record) {
		var data = {};
		data.vsj = record.data.vsj;
		data.id = record.data.id;
		data.text = record.data.text;
		data.icon = record.data.icon;
		Ext.apply(data, record.data);
		data.leaf = true;
		if (desktoptreeItemClick) {
			desktoptreeItemClick(data);
		}
	}
});