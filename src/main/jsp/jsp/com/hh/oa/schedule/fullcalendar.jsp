<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>日程安排</title>
<%=SystemUtil.getBaseJs("layout", "ztree")%>
<script>
var orgtreeconfig = {
		id : 'orgTree',
		url : 'usersystem-UsLeader-queryLeaderTree',
		onClick : function(node) {
			if(node.lx_==0){
				$('[name=jsp-oa-schedule-fullcalendarview]').attr('src','jsp-oa-schedule-fullcalendarview?currUserId='+node.id);
			}
		},
		rightMenu : [ {
			text : '刷新',
			img : $.hh.property.img_refresh,
			onClick : function() {
				$.hh.tree.refresh('orgTree');
			}
		} ],
		itemRightMenu : [ {
			text : '刷新',
			img : $.hh.property.img_refresh,
			onClick : function() {
				$.hh.tree.refresh('orgTree');
			}
		} ]
	}
	function doMyData(){
		$('[name=jsp-oa-schedule-fullcalendarview]').attr('src','jsp-oa-schedule-fullcalendarview');
	}
</script>
</head>
<body  xtype="border_layout">
	<div config="render : 'west' ,width:260 , closeText : '分管人员日程' ,initClose:true ">
		<div xtype="toolbar" config="type:'head'">
				<span xtype="button"
					config="onClick:doMyData,text:'刷新本人日程' ,width:'100%' " ></span>
			</div>
		<span xtype="tree" configVar="orgtreeconfig"></span>
	</div>
	<div  style="overflow: visible;" >
		<iframe name="jsp-oa-schedule-fullcalendarview"  width=100%
				height=100% frameborder=0 src="jsp-oa-schedule-fullcalendarview"></iframe>
	</div>
</body>
</html>