<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 720;
	var height = 450

	var objectid = params.object ? params.object.id : '';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('usersystem-menu-saveTree', {
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
			$('#lxspan').hide();
			Request.request('usersystem-menu-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					lxChange(result.leaf);
					$('#form').setValue(result);
				}
			});
		}
	}

	function init() {
		$('[lxchange=true]').hide();
		if (params.selectNode) {
			if (params.selectNode.isParent) {
				$("#node_span").setValue(params.selectNode);
			}
		}
		findData();
	}

	function lxChange(value) {
		if (value == 0) {
			$('[lxchange=true]').hide();
			$('[lxchange=false]').show();
		} else {
			$('[lxchange=true]').show();
			$('[lxchange=false]').hide();
		}
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
					<td xtype="label">父菜单：</td>
					<td><span id="node_span" xtype="selectTree"
						config="name: 'node' , params : {isNoLeaf : true}, tableName : 'sys_menu' , url : 'usersystem-menu-queryMenuListByPid'"></span>
					</td>
				</tr>
				<tr>
					<td xtype="label">类型：</td>
					<td><span id="lxspan" xtype="radio"
						config="name: 'leaf' ,defaultValue : 0, data :[{id:1,text:'菜单'},{id:0,text:'类别'}] , onChange : lxChange "></span></td>
					<td xtype="label">是否展开：</td>
					<td><span lxchange="false" xtype="radio"
						config="name: 'expanded' ,defaultValue : 0,  data :[{id:1,text:'是'},{id:0,text:'否'}]"></span></td>
				</tr>
				<tr>
					<td xtype="label">小图标：</td>
					<td colspan="3"><span xtype="selectPic" config=" name : 'icon' "></span></td>
				</tr>
				<tr>
					<td xtype="label">大图标：</td>
					<td colspan="3"><span xtype="selectPic"
						config=" name : 'vdtp' ,params :{path:'/hhcommon/images/big'} ,selectType:'big' "></span></td>
				</tr>
				<tr>
					<td xtype="label">动作：</td>
					<td colspan="3"><span lxchange="true" xtype="text"
						config=" name : 'vsj'"></span></td>
				</tr>
				<tr>
					<td xtype="label">移动端动作：</td>
					<td colspan="3"><span lxchange="true" xtype="text"
						config=" name : 'mobileUrl'"></span></td>
				</tr>
				<tr>
					<td xtype="label">打开类型：</td>
					<td colspan="3"><span  xtype="radio"
						config="name: 'openType' ,defaultValue : 0, data :[{id:0,text:'tab页签'},{id:1,text:'浏览器href'}]  "></td>
				</tr>
				<tr>
					<td xtype="label">参数：</td>
					<td colspan="3"><span lxchange="true" xtype="tableitem"
						config="name: 'params' "></span></td>
				</tr>
				<tr>
					<td xtype="label">备注：</td>
					<td colspan="3"><span xtype="textarea" config=" name : 'vbz'"></span></td>
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