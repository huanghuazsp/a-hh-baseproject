<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>系统参数</title>
<%=SystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	var params = BaseUtil.getIframeParams();
	function init() {
		findData();
	}
	var objectId = null;
	function findData() {
		Request.request('system-SysParam-findObjectById', {
			//data : {id:params.id},
			callback : function(result) {
				objectId = result.id;
				$('#form').setValue(result);
			}
		});
	}
	function save() {
		$.hh.validation.check('form', function(formData) {
			formData.id = objectId;
			Request.request('system-SysParam-save', {
				data : formData
			});
		});
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<table xtype="form" id="table">
				<tr>
					<td xtype="label" width=100px>系统名称</td>
					<td><span xtype="text"
						config=" name : 'sysName',required :true"></span></td>
					<td xtype="label">权限控制</td>
					<td colspan="3"><span xtype="radio"
						config="name: 'power' ,data :[{id:0,text:'开'},{id:1,text:'关'}]"></span></td>

				</tr>
				<tr>
					<td xtype="label">SQL语句日志</td>
					<td><span xtype="radio"
						config="name: 'logSql' ,data :[{id:1,text:'开'},{id:0,text:'关'}]"></span></td>
					<td xtype="label">SQL语句入库</td>
					<td><span xtype="radio"
						config="name: 'dataBaseSql' ,data :[{id:1,text:'开'},{id:0,text:'关'}]"></span></td>
				</tr>
				<tr>
					<td xtype="label">打开菜单方式</td>
					<td colspan="3"><span xtype="radio"
						config="name: 'onePage' ,data : [{	'id' : 1,	'text': 'onePage'}, {'id' : 0,'text' : '内嵌iframe'}, {'id' : 2,'text' : '弹出浏览器页签'}]"></span></td>
				</tr>
				<tr>
					<td xtype="label">系统图标img</td>
					<td><span xtype="uploadpic"
						config=" width:50,height:50, name: 'sysImg' , type : 'param'  , path:'/hhcommon/images/big' "></span></td>
					<td xtype="label">系统图标icon</td>
					<td><span xtype="uploadpic"
						config=" width:50,height:50, name: 'sysIcon' , type : 'param'  , path:'/hhcommon/images/extjsico' "></span></td>
				</tr>
				<!-- <tr>
					<td xtype="label">登陆背景图片</td>
					<td colspan="3"><span xtype="uploadpic"
						config=" width:550,height:400, name: 'loginBackImg' , type : 'param'  , path:'/hhcommon/images/background' "></span></td>
				</tr> -->
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>