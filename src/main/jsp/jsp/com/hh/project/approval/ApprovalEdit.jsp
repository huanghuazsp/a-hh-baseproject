<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 450;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('project-Approval-save', {
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
			Request.request('project-Approval-findObjectById', {
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
		<form id="form" xtype="form" class="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
				
				
					<tr>
						<td xtype="label">申请人：</td>
						<td><span xtype="text" config=" name : 'applyUser' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">申请人名称：</td>
						<td><span xtype="text" config=" name : 'applyUserText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">申请时间：</td>
						<td><span xtype="text" config=" name : 'applyDate' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">立项时间：</td>
						<td><span xtype="text" config=" name : 'approvalDate' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">申请内容：</td>
						<td><span xtype="text" config=" name : 'applyComment' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">部门经理：</td>
						<td><span xtype="text" config=" name : 'deptManager' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">部门经理：</td>
						<td><span xtype="text" config=" name : 'deptManagerText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">部门经理意见：</td>
						<td><span xtype="text" config=" name : 'deptManagerComment' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">分管副总：</td>
						<td><span xtype="text" config=" name : 'branchDeputyManager' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">分管副总：</td>
						<td><span xtype="text" config=" name : 'branchDeputyManagerText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">分管副总意见：</td>
						<td><span xtype="text" config=" name : 'branchDeputyManagerComment' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">总经理：</td>
						<td><span xtype="text" config=" name : 'overallManager' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">总经理：</td>
						<td><span xtype="text" config=" name : 'overallManagerText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">总经理意见：</td>
						<td><span xtype="text" config=" name : 'overallManagerComment' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">项目ID：</td>
						<td><span xtype="text" config=" name : 'projectId' "></span></td>
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

 
 