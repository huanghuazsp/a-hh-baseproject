<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>项目信息</title>
<%=BaseSystemUtil.getBaseJs("checkform")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 800;
	var height = 650;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';


	function findData() {
		if (objectid) {
			Request.request('project-ProjectInfo-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result,{view:true});
				}
			});
		}
	}

	function init() {
		findData();
	}
	
	var tabconfig = {
		activate : function(ui) {
			var newPanel = ui.newPanel;
			var id = newPanel.attr('id');
			
		}
	}
</script>
</head>
<body>
	<div xtype="hh_main_content">
		<div id="tabs" xtype="tab" configVar="tabconfig">
			<ul>
				<li><a href="#formDiv">项目信息</a></li>
				<li><a href="#modularDiv">模块信息</a></li>
				<li><a href="#userDiv">参与者信息</a></li>
				<li><a href="#fileDiv">附件文档</a></li>
			</ul>
			
			<div id="formDiv">
				<form id="form" xtype="form" class="form">
					<span xtype="text" config=" hidden:true,name : 'id'"></span>
					<table xtype="form">
							<tr>
								<td xtype="label">项目名称：</td>
								<td colspan="3"><span xtype="html" config=" name : 'text'  "></span></td>
							</tr>
							<tr>
								<td xtype="label">开始日期：</td>
								<td><span xtype="html"
									config="name: 'startDate'  ,dateType : 'yyyy年MM月dd日' "></span></td>
								<td xtype="label">（计划）<br>结束日期：</td>
								<td><span xtype="html"
									config="name: 'planEndDate'  ,dateType : 'yyyy年MM月dd日'  "></span></td>
							</tr>
							<tr>
								<td xtype="label">项目经理：</td>
								<td colspan="3"><span xtype="html" config=" name : 'managerText' "></span></td>
							</tr>
						
							<tr>
								<td xtype="label">客户名称：</td>
								<td><span xtype="html" config=" name : 'client' "></span></td>
								<td xtype="label">项目金额：</td>
								<td><span xtype="html" config=" name : 'money'  "></span>万</td>
							</tr>
						
							<tr>
								<td xtype="label">描述：</td>
								<td colspan="3"><span xtype="html" config=" name : 'describe' "></span></td>
							</tr>
						
						
					</table>
				</form>
			</div>
			
			<div id="modularDiv" xtype="pagelist"
				config=" params : {projectId:objectid} , url: 'project-ProjectModular-queryPagingData' ,column : [
					{
						name : 'text' ,
						text : '名称',
						width:170
					},
					{
						name : 'describe' ,
						align:'left',
						text : '描述'
					}
			]">
			</div>
			
			
		</div>
	</div>
</body>
</html>

 
 