<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>样式</title>
<%=SystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	function submit() {
	}
	function init() {
	}
	var gridViewConfig = {
		onClick : function(data) {
			Request.request('usersystem-user-updateTheme', {
				data : {
					theme : data.value
				},
				callback : function(result) {
					Dialog.confirm({
						message : '修改样式成功，是否刷新页面？',
						callback : function(result) {
							if(result==1){
								$.hh.getRootFrame().document.location.reload();
							}else{
								Dialog.close();
							}
						}
					});
				}
			});
		},
		data : [ {
			img : '/hhcommon/opensource/jquery/image/themes/black-tie.png',
			value:'black-tie'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/blitzer.png',
			value:'blitzer'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/cupertino.png',
			value:'cupertino'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/dark-hive.png',
			value:'dark-hive'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/dot-luv.png',
			value:'dot-luv'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/eggplant.png',
			value:'eggplant'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/excite-bike.png',
			value:'excite-bike'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/flick.png',
			value:'flick'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/hot-sneaks.png',
			value:'hot-sneaks'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/humanity.png',
			value:'humanity'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/le-frog.png',
			value:'le-frog'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/mint-choco.png',
			value:'mint-choco'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/overcast.png',
			value:'overcast'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/pepper-grinder.png',
			value:'pepper-grinder'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/smoothness.png',
			value:'smoothness'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/south-street.png',
			value:'south-street'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/start.png',
			value:'start'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/sunny.png',
			value:'sunny'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/swanky-purse.png',
			value:'swanky-purse'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/trontastic.png',
			value:'trontastic'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/ui-darkness.png',
			value:'ui-darkness'
		}, {
			img : '/hhcommon/opensource/jquery/image/themes/ui-lightness.png',
			value:'ui-lightness'
		} ]
	};
</script>
</head>
<body>
	<span xtype="gridView" configVar="gridViewConfig"></span>
</body>
</html>