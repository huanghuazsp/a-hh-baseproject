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

	var objectid = params.object ? params.object.id : '';
	params.selectMenuNode = params.selectMenuNode||{};
	function save() {
		$.hh.validation.check('form', function(formData) {
			if(!formData.vurl && !formData.pageText){
				Dialog.infomsg('页面名字和请求地址不能同时为空!');
				return;
			}
			Request.request('usersystem-operate-save', {
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
		$('#span_menuUrl').setValue(params.selectMenuNode.vsj);
		if(params.selectMenuNode){
			$('#span_menuId').setValue({
				id : params.selectMenuNode.id,
				text : params.selectMenuNode.name
			});
		}
		findData();
	}
	
	function blur(){
		$('#span_pageText').setValue($('#span_text').getValue());
	}
	
	function menuChange(data){
		$('#span_menuUrl').setValue(data.vsj);
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
				<tr>
					<td xtype="label">所属菜单：</td>
					<td><span  xtype="selectTree"
						config="onChange: menuChange ,name: 'menuId' ,required :true,  findTextAction : 'usersystem-menu-findObjectById' , url : 'usersystem-menu-queryMenuListByPid'"></span></td>
				</tr>
				<tr>
					<td xtype="label">所在页面地址：</td>
					<td><span xtype="text" config=" name : 'menuUrl',required :true"></span></td>
				</tr>
				<tr>
					<td xtype="label">名称：</td>
					<td><span xtype="text" config=" name : 'text',required :true , blur:blur"></span></td>
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