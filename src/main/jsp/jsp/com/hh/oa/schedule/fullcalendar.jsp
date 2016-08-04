<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>日程安排</title>
<%=SystemUtil.getBaseJs("layout", "ztree")%>
<script>
	var orgtreeconfig = {
			render : false,
			id : 'orgTree',
			nheight : 42,
			url : 'usersystem-Org-queryOrgAndUsersList',
			onClick : function(node) {
			},
			rightMenu : [ {
				text : '刷新',
				img : $.hh.property.img_refresh,
				onClick : function() {
					$.hh.tree.refresh('orgTree');
				}
			} ],
			itemRightMenu : [  {
				text : '刷新',
				img : $.hh.property.img_refresh,
				onClick : function() {
					$.hh.tree.refresh('orgTree');
				}
			} ]
		}
	
	var scheduleConfig = {
			
	}
	
	var tabconfig = {
		activate : function(ui) {
			var newPanel = ui.newPanel;
			var id = newPanel.attr('id');
			if (id == 'scheduleDiv' && grouprender == false) {
			} else if (id == 'shareDiv' && orgrender == false) {
			} 
		}
	}
</script>
</head>
<body  xtype="border_layout">
	<div config="render : 'west' ,width:260 ">
		<div id="tabs" xtype="tab" configVar="tabconfig">
			<ul>
				<li><a href="#scheduleDiv">日程</a></li>
				<li><a href="#shareDiv">共享</a></li>
			</ul>
			<div id="scheduleDiv">
				<span xtype=menu id="schedulespan" configVar=" scheduleConfig "></span>
			</div>
			<div id="shareDiv">
				<span xtype="tree" configVar="orgtreeconfig"></span>
			</div>
		</div>
	</div>
	<div  style="overflow: visible;" >
		<iframe  width=100%
				height=100% frameborder=0 src="jsp-oa-schedule-fullcalendarview"></iframe>
	</div>
</body>
</html>