<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=BaseSystemUtil.getBaseJs("checkform","date", "ueditor")%>

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
		}
	}

	function init() {
		findData();
	}
	
	var  userConfig = {
			'name':'userStr', 
			trhtml : '<table style="width:100%"><tr>'
				+'<td xtype="label">参与者：</td><td><span xtype="selectUser" valuekey="user" config="  "></span></td>'
				+'<td xtype="label">角色：</td><td><span xtype="combobox" valuekey="roleName" config="  "></span></td>'
				+'</tr><tr>'
				+'<td xtype="label">职责：</td><td><span xtype="text" valuekey="user" config="  "></span></td>'
				+'<td xtype="label">加入日期：</td><td><span xtype="date" valuekey="joinDate" config=" type:'date' "></span></td>'
				+'</tr></table>' 
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
						<td colspan="3"><span xtype="selectUser" config=" name : 'manager' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">客户名称：</td>
						<td><span xtype="text" config=" name : 'client' "></span></td>
						<td xtype="label">（万）<br>项目金额：</td>
						<td><span xtype="text" config=" name : 'money',number:true "></span></td>
					</tr>
					<tr>
						<td  colspan="4">

							<fieldset>
							<legend>参与者信息</legend>
							<span configVar="userConfig" xtype="tableitem" ></span>
							</fieldset>
						</td>
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

 
 