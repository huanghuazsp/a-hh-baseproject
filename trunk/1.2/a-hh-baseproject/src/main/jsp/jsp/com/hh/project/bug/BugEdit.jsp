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
	var width = 800;
	var height = 450;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('project-Bug-save', {
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
			Request.request('project-Bug-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result);
					renderModular(result);
				}
			});
		}
	}

	function init() {
		findData();
	}
	
	function queryHtml(){
		return '<table xtype="form" id="queryForm" style="">'
							+'<tr>'
							+'<td xtype="label">名称：</td>'
							+'<td><span xtype="text" config=" name : \'text\' ,enter: doQuery "></span></td>'
							+'<td style="width:100px;"><span	xtype="button" config="onClick: doQuery ,text:\'查询\' , itype :\'query\' "></span></td>'
						+'</tr>'
					+'</table>';
	}
	
	var projectConfig = {
			openWidth:800,
			name : 'projectId',
			findTextAction :'project-ProjectInfo-findObjectById' ,
			pageconfig:{
				queryHtml : queryHtml(),
				url:'project-ProjectInfo-queryPartPage' ,
				column : [
					{
						name : 'text' ,
						text : '项目名称'
					},
					{
						name : 'startDate' ,
						text : '开始日期',
						render : 'date',
						width:80
					},
					{
						name : 'managerText' ,
						text : '项目经理'
					}
				]
			},
			onChange:function(data){
				renderModular({});
			}
	};
	
	function renderModular(object){
		var projectId1 = $('#span_projectId').getValue();
		
		if(projectId1){
			modularConfig.url='project-ProjectModular-queryTreeListSelect';
			modularConfig.params={
					projectId : projectId1
			};
			modularConfig.noCheckParent=true;
			$('#modularIdSpan').render(modularConfig);
			$('#modularIdTr').show();
		}else{
			$('#modularIdTr').hide();
		}
		
		if (object && object.modularId) {
			$('#modularIdSpan').setValue({
				id: object.modularId,
				text : object.modularIdText
			});
		}
		
	}
	
	var modularConfig = {
		render:false,
		name:'modularId',
		onChange:function(data){
			$('#span_processingPeople').setValue({
				id:data.processingPeople,
				text:data.processingPeopleText
			});
		}
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
				
					<tr>
						<td xtype="label">所属项目：</td>
						<td colspan="3"><span xtype="selectPageList"
							configVar=" projectConfig " ></span></td>
					</tr>
					<tr style="display:none;" id="modularIdTr">
						<td xtype="label">所属模块：</td>
						<td colspan="3"><span id="modularIdSpan" xtype="selectTree"
							configVar=" modularConfig " ></span></td>
					</tr>
				
					<tr>
						<td xtype="label">名称：</td>
						<td><span xtype="text" config=" name : 'text' "></span></td>
					</tr>
					
					<tr>
						<td xtype="label">负责人：</td>
						<td><span xtype="selectUser" config=" name : 'processingPeople' "></span></td>
					</tr>
					<tr>
						<td xtype="label">抄送人：</td>
						<td><span xtype="selectUser" config=" name : 'handlingUsers',many:true "></span></td>
					</tr>
					<tr>
						<td xtype="label">描述：</td>
						<td><span xtype="ckeditor" config=" name : 'describe_' "></span></td>
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

 
 