<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 400

	var objectid = params.row ? params.row.id : '';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('usersystem-role-save', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						params.callback();
						Dialog.close();
					}
				}
			});
		});
	}

	function findData() {
		if (objectid) {
			Request.request('usersystem-role-findObjectById', {
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
				<tbody>
					<tr>
						<td xtype="label">名称：</td>
						<td><span xtype="text" config=" name : 'text',required :true"></span></td>
					</tr>
					<tr>
						<td xtype="label">角色属性：</td>
						<td><span xtype="text" config=" name : 'jssx' "></span></td>
					</tr>
					<tr>
						<td xtype="label">状态：</td>
						<td><span xtype="radio"
							config="name: 'state' ,value : 0, data :[{id:0,text:'正常'},{id:1,text:'冻结'}]"></span></td>
					</tr>
					<tr>
						<td xtype="label">类型：</td>
						<td><span xtype="radio"
							config="name: 'nlx' ,value : 1, data : [ {	'id' : 1,	text : '业务角色'}, {	'id' : 2,	text : '管理角色'}, {	'id' : 3,	text : '系统内置角色'  } ]"></span></td>
					</tr>
					<tr>
						<td xtype="label">备注：</td>
						<td><span xtype="textarea" config=" name : 'vbz'"></span></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span>
		<span xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>