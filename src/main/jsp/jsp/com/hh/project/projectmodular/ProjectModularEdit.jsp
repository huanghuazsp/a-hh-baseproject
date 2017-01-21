<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>模块编辑</title>
<%=SystemUtil.getBaseJs("checkform","date", "ueditor")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 450;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';
	var projectId = '<%=Convert.toString(request.getParameter("projectId"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			formData.projectId = projectId;
			Request.request('project-ProjectModular-save', {
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
			Request.request('project-ProjectModular-findObjectById', {
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
						<td xtype="label">名称：</td>
						<td><span xtype="text" config=" name : 'text' ,required :true "></span></td>
					</tr>
					<tr>
						<td xtype="label">计划投入：</td>
						<td><span xtype="text" config=" name : 'input' , integer :true ,suffix:'人日' "></span></td>
					</tr>
					<tr>
						<td xtype="label">描述：</td>
						<td><span xtype="ckeditor" config=" name : 'describe' "></span></td>
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

 
 