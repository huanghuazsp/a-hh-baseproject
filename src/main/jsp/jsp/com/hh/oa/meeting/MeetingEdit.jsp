<%@page import="com.hh.system.util.SystemUtil"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform","date")+SystemUtil.getUser()%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 500;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('oa-Meeting-save', {
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
			Request.request('oa-Meeting-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result);
				}
			});
		}else{
			$('#form').setValue({
				manager : loginUser.id,
				managerText : loginUser.text
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
						<td xtype="label">会议名称：</td>
						<td><span xtype="text" config=" name : 'text' ,required :true "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">可容纳人数：</td>
						<td><span xtype="text" config=" name : 'peopleNumber' , integer :true ,number:true ,required :true"></span></td>
					</tr>
				
					<tr>
						<td xtype="label">会议管理员：</td>
						<td><span xtype="selectUser" config=" name : 'manager',required :true  "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">设备情况：</td>
						<td><span xtype="textarea" config=" name : 'device' ,maxSize:512,required :true "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">所在地点：</td>
						<td><span xtype="textarea" config=" name : 'locale' ,maxSize:512 ,required :true "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">会议室描述：</td>
						<td><span xtype="textarea" config=" name : 'describe' ,maxSize:512   "></span></td>
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

 
 