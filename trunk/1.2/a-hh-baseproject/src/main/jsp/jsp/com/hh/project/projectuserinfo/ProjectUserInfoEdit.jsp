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
			Request.request('project-ProjectUserInfo-save', {
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
			Request.request('project-ProjectUserInfo-findObjectById', {
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
						<td xtype="label">成员id：</td>
						<td><span xtype="text" config=" name : 'user' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">成员名称：</td>
						<td><span xtype="text" config=" name : 'userName' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">角色：</td>
						<td><span xtype="text" config=" name : 'roleName' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">职责：</td>
						<td><span xtype="text" config=" name : 'duty' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">加入日期：</td>
						<td><span xtype="text" config=" name : 'joinDate' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">直接上级：</td>
						<td><span xtype="text" config=" name : 'directManager' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">描述：</td>
						<td><span xtype="text" config=" name : 'describe' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">项目id：</td>
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

 
 