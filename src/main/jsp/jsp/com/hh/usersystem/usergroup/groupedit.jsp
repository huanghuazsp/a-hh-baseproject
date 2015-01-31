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

	var objectid = params.row ? params.row.id : '';

	function callback() {

	}

	function save() {
		FormUtil.check('form', function(formData) {
			Request.request('usersystem-UserGroup-save', {
				data : formData,
				callback : function(result) {
					if (result.success) {
						if (result.success) {
							callback(formData);
						}
					}
				}
			});
		});
	}

	function findData(objectid) {
		//$('#lx_tr').hide();
		Request.request('usersystem-UserGroup-findObjectById', {
			data : {
				id : objectid
			},
			callback : function(result) {
				$('#form').setValue(result);
			}
		});
	}

	function newData(params) {
		//$('#lx_tr').show();
		params.leaf=0;
		params.expanded=0;
		$('#form').setValue(params);
	}

	function init() {
		//findData();
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
				<tbody>
					<tr>
						<td xtype="label">名称：</td>
						<td><span xtype="text" config=" name : 'text',required :true"></span></td>
					</tr>
					<tr>
						<td xtype="label">父节点：</td>
						<td><span id="node_span" xtype="selectTree"
							config="name: 'node' , tableName : 'hh_user_group' , url : 'usersystem-UserGroup-queryTreeList' , params : {isNoLeaf : true} "></span>
						</td>
					</tr>
					<tr>
						<td xtype="label">是否展开：</td>
						<td><span xtype="radio"
							config="name: 'expanded' ,defaultValue : 0,  data :[{id:1,text:'是'},{id:0,text:'否'}]"></span></td>
					</tr>
					<tr id="lx_tr">
						<td xtype="label">类型：</td>
						<td><span xtype="radio"
							config="name: 'leaf' ,defaultValue : 0,  data :[{id:1,text:'子节点'},{id:0,text:'父节点'}]"></span></td>
					</tr>
					<tr>
						<td xtype="label">组员：</td>
						<td><span xtype="selectUser" config="name: 'users' "></td>
					</tr>
					<tr>
						<td xtype="label">备注：</td>
						<td><span xtype="textarea" config=" name : 'remark'"></span></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>