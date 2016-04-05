<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=BaseSystemUtil.getBaseJs("checkform","date")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 450;
	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function callback() {
	}
	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('system-ResourcesType-saveTree', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						callback(formData);
					}
				}
			});
		});
	}

	function findData(objid) {
		if (objid) {
			Request.request('system-ResourcesType-findObjectById', {
				data : {
					id : objid
				},
				callback : function(result) {
					$('#form').setValue(result);
				}
			});
		}
	}

	function newData(params) {
		params.expanded=0;
		params.leaf=0;
		$('#form').setValue(params);
	}

	function init() {

	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form" width=80% align=center>
				<tr>
					<td xtype="label">名称：</td>
					<td><span xtype="text" config=" name : 'text',required :true"></span></td>
				</tr>
				<tr>
					<td xtype="label">父节点：</td>
					<td><span id="node_span" xtype="selectTree"
						config="name: 'node' , findTextAction : 'system-ResourcesType-findObjectById' , url : 'system-ResourcesType-queryTreeList' "></span>
					</td>
				</tr>
				<tr>
					<td xtype="label">类型：</td>
					<td><span xtype="radio"
						config="name: 'leaf' ,defaultValue : 0,  data :[{id:1,text:'子节点'},{id:0,text:'父节点'}]"></span></td>
				</tr>
				<tr>
					<td xtype="label">是否展开：</td>
					<td><span xtype="radio"
						config="name: 'expanded' ,defaultValue : 0,  data :[{id:1,text:'是'},{id:0,text:'否'}]"></span></td>
				</tr>
				
				
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span>
	</div>
</body>
</html>