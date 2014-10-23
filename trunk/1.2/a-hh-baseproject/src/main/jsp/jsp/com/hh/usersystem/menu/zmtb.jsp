<%@page import="com.hh.usersystem.bean.usersystem.HhXtCd"%>
<%@page import="com.hh.usersystem.bean.usersystem.HhXtYh"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.google.gson.Gson"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>快捷页面</title>
<%=SystemUtil.getBaseJs("right_menu")%>
<%
	Gson gson = new Gson();
HhXtYh hhXtYh =	(HhXtYh)session.getAttribute("loginuser");
List<HhXtCd> hhXtCds =  hhXtYh.getHhXtYhCdZmtbList();
String hhxtcdStr =  gson.toJson(hhXtCds);
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
				$('#gridView').setConfig({
					data : result
				});
				$('#gridView').render();
			}
		});
	}
	var gridViewConfig = {
		margin : 10,
		rightMenu : [ {
			text : '删除',
			img : StaticVar.img_delete,
			onClick : function(resultObject) {
				var content = resultObject.content;
				var id = $(content).attr('id');
				Request.request('usersystem-menu-deleteZmtb', {
					data : {
						id : id
					}
				}, function(result) {
					if (result.success) {
						refresh();
					}
				});
			}
		} ],
		onClick : function(data) {
			if (BaseUtil.getRootFrame().addTab) {
				BaseUtil.getRootFrame().addTab(data);
			} else {
				Request.href(data.vsj);
			}
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
			img : StaticVar.img_add,
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
</script>
</head>
<body>
	<span xtype="rightMenu" configVar="rightMenuConfig"></span>
	<div style="padding: 25px;">
		<span id="gridView" xtype="gridView" configVar="gridViewConfig"></span>
	</div>
</body>
</html>