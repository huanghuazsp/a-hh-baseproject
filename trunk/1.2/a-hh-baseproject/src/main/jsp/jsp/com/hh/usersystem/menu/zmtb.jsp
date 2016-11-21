<%@page import="com.hh.usersystem.service.impl.ZmtbService"%>
<%@page import="com.hh.system.service.impl.BeanFactoryHelper"%>
<%@page import="com.hh.usersystem.service.impl.MenuService"%>
<%@page import="com.hh.system.util.StaticVar"%>
<%@page import="com.hh.system.util.dto.ParamFactory"%>
<%@page import="com.hh.usersystem.bean.usersystem.UsUserMenuZmtb"%>
<%@page import="com.hh.usersystem.bean.usersystem.SysMenu"%>
<%@page import="com.hh.usersystem.bean.usersystem.UsUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.google.gson.Gson"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>快捷页面</title>
<%=SystemUtil.getBaseJs()%>
<%
	Gson gson = new Gson();
	UsUser hhXtYh = (UsUser) session.getAttribute("loginuser");
	List<SysMenu> hhXtCds = hhXtYh.getDesktopQuickList();

	MenuService menuService = BeanFactoryHelper.getBean(MenuService.class);
	ZmtbService zmtbService = BeanFactoryHelper.getBean(ZmtbService.class);
	if (hhXtCds.size() == 0) {
		hhXtCds = menuService.addZmtb(StaticVar.systemProperties.get("menu.default_quickmenu"));
	}

	String hhxtcdStr = gson.toJson(hhXtCds);
%>
<script type="text/javascript">
	var hhxtcds =
<%=hhxtcdStr%>
	;
	for (var i = 0; i < hhxtcds.length; i++) {
		hhxtcds[i].img = hhxtcds[i].vdtp;
	}
	function init() {
	}
	function refresh() {
		Request.request('usersystem-App-queryZmtb', {
			callback : function(result) {
				for (var i = 0; i < result.length; i++) {
					result[i].img = result[i].vdtp;
				}
				$('#gridView').render({
					data : result
				});
			}
		});
	}
	var gridViewConfig = {
		margin : 10,
		rightMenu : [ {
			text : '删除',
			img : $.hh.property.img_delete,
			onClick : function(resultObject) {
				var content = resultObject.content;
				var id = $(content).attr('id');
				Request.request('usersystem-menu-deleteZmtb', {
					data : {
						id : id
					}
				}, function(result) {
					if (result.success != false) {
						refresh();
					}
				});
			}
		} ],
		onClick : function(data) {
			$.hh.addTab(data);
		},
		update : function(id) {
			Request.request('usersystem-Zmtb-orderIds', {
				data : {
					id : id
				}
			});
		},
		data : hhxtcds
	};
	var rightMenuConfig = {
		data : [ {
			text : '新建',
			img : $.hh.property.img_add,
			onClick : function() {
				Dialog.open({
					url : 'jsp-usersystem-menu-zmtbadd',
					params : {
						callback : refresh
					}
				});
			}
		} ]
	};

	function renderAllQuickMenu(timeData) {
		var mapData = {};
		var dataList = $('#gridView').getConfig('data');
		for (var i = 0; i < dataList.length; i++) {
			var data = dataList[i];
			mapData[data.vsj] = data;
		}
		for ( var p in timeData) {
			var data = timeData[p];
			if ($.isPlainObject(data) && data.vsj) {
				if (mapData[data.vsj]) {
					var gridli = $('#gridView')
					.find('#' + mapData[data.vsj].id);
					if(gridli && gridli.length>0 && data.count && data.count>0){
						gridli.append('<font class="hh_red" style="position: absolute; right: 3px;  top: 3px;">'+data.count+'</font>');
					}else if(gridli && gridli.length>0 && data.count==0){
						gridli.find('.hh_red').remove();
					}
				}

			}
		}
	}
</script>
</head>
<body>
	<span xtype="rightMenu" configVar="rightMenuConfig"></span>
	<div style="padding: 0px;">
		<span id="gridView" xtype="gridView" configVar="gridViewConfig"></span>
	</div>
</body>
</html>