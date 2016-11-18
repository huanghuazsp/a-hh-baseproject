<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=BaseSystemUtil.getBaseJs("checkform","date")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 450;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('project-ProjectInfo-save', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						params.callback(formData);
						Dialog.close();
					}
				}
			});
		});
	}

	function findData() {
		if (objectid) {
			Request.request('project-ProjectInfo-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result);
				}
			});
		}
	}

	function init() {
		findData();
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
				
				
					<tr>
						<td xtype="label">项目名称：</td>
						<td><span xtype="text" config=" name : 'text' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">项目经理：</td>
						<td><span xtype="text" config=" name : 'manager' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">项目经理名称：</td>
						<td><span xtype="text" config=" name : 'managerText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">客户：</td>
						<td><span xtype="text" config=" name : 'client' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">项目金额：</td>
						<td><span xtype="text" config=" name : 'money' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">描述：</td>
						<td><span xtype="text" config=" name : 'describe' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">开始日期：</td>
						<td><span xtype="text" config=" name : 'startDate' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">计划结束日期：</td>
						<td><span xtype="text" config=" name : 'planEndDate' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">结束日期：</td>
						<td><span xtype="text" config=" name : 'endDate' "></span></td>
					</tr>
				
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>

 
 