<%@page import="com.hh.system.util.SystemUtil"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>编辑项目信息</title>
<%=BaseSystemUtil.getBaseJs("checkform","date", "ueditor")+SystemUtil.getUser()%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 800;
	var height = 650;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('project-ProjectInfo-save', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						if(params.callback){
							params.callback(formData);
						}
						wdxm();
					}
				}
			});
		});
	}
	
	function wdxm(){
		if(parent.wdxm){
			parent.wdxm();
		}else{
			Dialog.close();
		}
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
		}else{
			$('#form').setValue({
				manager:loginUser.id,
				managerText:loginUser.text
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
						<td xtype="label">项目名称：</td>
						<td colspan="3"><span xtype="text" config=" name : 'text' ,required :true "></span></td>
					</tr>
					<tr>
						<td xtype="label">开始日期：</td>
						<td><span xtype="date"
							config="name: 'startDate'  ,type:'date' ,required :true"></span></td>
						<td xtype="label">（计划）<br>结束日期：</td>
						<td><span xtype="date"
							config="name: 'planEndDate'  ,type:'date' ,required :true "></span></td>
					</tr>
					<tr>
						<td xtype="label">项目经理：</td>
						<td><span xtype="selectUser" config=" name : 'manager' ,required :true "></span></td>
						<td xtype="label">所有人可见：</td>
						<td><span xtype="check" config=" name : 'allUserRead',num:1 "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">客户名称：</td>
						<td><span xtype="text" config=" name : 'client' "></span></td>
						<td xtype="label">（万）<br>项目金额：</td>
						<td><span xtype="text" config=" name : 'money',number:true "></span></td>
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

 
 