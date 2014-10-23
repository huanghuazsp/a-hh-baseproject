$import("com.hh.system.video.Video");
Ext.define('com.hh.system.video.VideoWindow', {
	extend : 'com.hh.base.BaseServicePanel',
	title : '播放器',
	iconCls : 'video',
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
		this.init();
	},
	init : function() {
		var me = this;
		var video = Ext.widget('video', {
			xtype : 'video',
			region : 'center',
			src : [ {
				src : 'http://dev.sencha.com/desktopvideo.mp4',
				type : 'video/mp4'
			}, {
				src : 'http://dev.sencha.com/desktopvideo.ogv',
				type : 'video/ogg'
			}, {
				src : 'http://dev.sencha.com/desktopvideo.mov',
				type : 'video/quicktime'
			} ],
			autobuffer : true,
			autoplay : true,
			controls : true,
			listeners : {
				afterrender : function(video) {
					me.videoEl = video.video.dom;
					if (video.supported) {
						me.tip = new Ext.tip.ToolTip({
							anchor : 'bottom',
							dismissDelay : 0,
							height : me.tipHeight,
							width : me.tipWidth,
							renderTpl : [ '<canvas width="', me.tipWidth,
									'" height="', me.tipHeight, '">' ],
							renderSelectors : {
								body : 'canvas'
							},
							listeners : {
								afterrender : me.onTooltipRender,
								show : me.renderPreview,
								scope : me
							}
						});
					}
				}
			}
		});
		this.add(video);
	},
    onTooltipRender: function (tip) {
        var el = tip.body.dom, me = this;
        me.ctx = el.getContext && el.getContext('2d');
    },
    renderPreview: function() {
        var me = this;
        if ((me.tip && !me.tip.isVisible()) || !me.videoEl) {
            return;
        }
        if (me.ctx) {
            try {
                me.ctx.drawImage(me.videoEl, 0, 0, me.tipWidth, me.tipHeight);
            } catch(e) {};
        }
        Ext.Function.defer(me.renderPreview, 20, me);
    }
});
