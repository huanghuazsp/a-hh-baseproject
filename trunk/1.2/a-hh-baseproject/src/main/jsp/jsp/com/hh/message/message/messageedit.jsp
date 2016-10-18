<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>消息编辑</title>
<%=SystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 400

	var objectid = params.row ? params.row.id : '';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('message-SysMessage-save', {
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
			Request.request('message-SysMessage-findObjectById', {
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
					<td xtype="label">标题：</td>
					<td><span xtype="text" config=" name : 'title',required :true"></span></td>
				</tr>
				<tr>
					<td xtype="label">接收人：</td>
					<td><span xtype="selectUser"
						config="radio:true,name: 'shouUser',many:true "></span></td>
				</tr>
				<tr>
					<td xtype="label">地址：</td>
					<td><span xtype="text" config="name: 'path' "></span></td>
				</tr>
				<tr>
					<td xtype="label">参数：</td>
					<td><span xtype="tableitem" config="name: 'params' "></span></td>
				</tr>
				<tr>
					<td xtype="label">JS代码：</td>
					<td><span xtype="textarea" config="name: 'jsCode' "></span></td>
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