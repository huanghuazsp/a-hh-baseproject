<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	var params = BaseUtil.getIframeParams();
	var width = 600;
	var height = 400

	var objectid = params.object ? params.object.id : '';

	function save() {
		FormUtil.check('form', function(formData) {
			if(!formData.vurl && !formData.pageText){
				Dialog.infomsg('页面名字和请求地址不能同时为空!');
				return;
			}
			formData.vpid=params.selectMenuNode.id;
			formData.menuUrl=params.selectMenuNode.vsj;
			Request.request('usersystem-operate-save', {
				data : formData,
				callback : function(result) {
					if (result.success) {
						params.callback();
						Dialog.close();
					}
				}
			});
		});
	}

	function findData() {
		if (objectid) {
			Request.request('usersystem-operate-findObjectById', {
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
					<td><span xtype="text" config=" name : 'text',required :true"></span></td>
				</tr>
				<tr>
					<td xtype="label">页面名称：</td>
					<td><span xtype="text" config=" name : 'pageText'"></span></td>
				</tr>
				<tr>
					<td xtype="label">请求地址：</td>
					<td><span xtype="text" config=" name : 'vurl' "></span></td>
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