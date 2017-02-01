<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform", "ueditor")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 450;
	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';
	var projectId = '<%=Convert.toString(request.getParameter("projectId"))%>';

	function callback() {
	}
	function save() {
		$.hh.validation.check('form', function(formData) {
			formData.projectId = projectId;
			Request.request('project-ProjectModular-saveTree', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						callback(formData);
					}
				}
			});
		});
	}

	function findData(objid,oper) {
		if (objid) {
			Request.request('project-ProjectModular-findObjectById', {
				data : {
					id : objid
				},
				callback : function(result) {
					$('#form').setValue(result);
				}
			});
			if(!oper){
				$('#saveSpan').hide();
			}else{
				$('#saveSpan').show();
			}
		}
	}

	function newData(params) {
		$('#saveSpan').show();
		params.expanded=0;
		params.type=0;
		$('#form').setValue(params);
	}

	function init() {

	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form" width=80% align=center>
				<tr>
					<td xtype="label">类型：</td>
					<td><span xtype="radio"
						config="name: 'type' ,value : 0,  data :[{id:1,text:'模块'},{id:0,text:'任务'}]"></span></td>
				</tr>	
				<tr>
					<td xtype="label">名称：</td>
					<td><span xtype="text" config=" name : 'text',required :true"></span></td>
				</tr>
				<tr>
					<td xtype="label">处理人：</td>
					<td><span xtype="selectUser" config=" name : 'processingPeople' "></span></td>
				</tr>
				<tr>
					<td xtype="label">父节点：</td>
					<td><span id="node_span" xtype="selectTree"
						config="name: 'node' , findTextAction : 'project-ProjectModular-findObjectById' , url : 'project-ProjectModular-queryTreeList' "></span>
					</td>
				</tr>
				<tr>
					<td xtype="label">是否展开：</td>
					<td><span xtype="radio"
						config="name: 'expanded' ,value : 0,  data :[{id:1,text:'是'},{id:0,text:'否'}]"></span></td>
				</tr>				
				<tr>
					<td xtype="label">计划投入：</td>
					<td><span xtype="text" config=" name : 'input' , integer :true ,suffix:'人日' "></span></td>
				</tr>
				<tr>
					<td xtype="label">描述：</td>
					<td><span xtype="ckeditor" config=" name : 'describe' "></span></td>
				</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span id="saveSpan" xtype="button" config="text:'保存' , onClick : save "></span>
	</div>
</body>
</html>