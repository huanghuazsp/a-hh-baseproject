<%@page import="com.hh.system.util.SystemUtil"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>项目参与者编辑</title>
<%=BaseSystemUtil.getBaseJs("checkform","date", "ueditor")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 700;
	var height = 550;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';
	var projectId = '<%=Convert.toString(request.getParameter("projectId"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			formData.projectId = projectId;
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
						<td xtype="label">成员名称：</td>
						<td><span xtype="selectUser" config=" name : 'user' ,required :true  "></span></td>
						<td xtype="label">直接上级：</td>
						<td><span xtype="selectUser" config=" name : 'directManager' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">加入日期：</td>
						<td><span xtype="date" config=" name : 'joinDate',type:'date' ,required :true  "></span></td>
						<td xtype="label">职责：</td>
						<td><span xtype="text" config=" name : 'duty' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">角色：</td>
						<td colspan="3"><span xtype="checkbox" config=" name : 'role' ,required :true , data : <%=SystemUtil.getJsonDataByCode("xiangmuguanlicanyuzhejiaose")%>   "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">描述：</td>
						<td colspan="3"><span xtype="ckeditor" config=" name : 'describe' "></span></td>
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

 
 