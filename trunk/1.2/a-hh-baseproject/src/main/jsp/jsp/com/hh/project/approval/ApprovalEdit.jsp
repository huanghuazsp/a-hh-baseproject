<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform", "ueditor","date")+ SystemUtil.getUser()%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 450;

	var objectid = '<%=Convert.toString(request.getParameter("objectId"))%>';

	function save() {
		var returnObject = null;
		$.hh.validation.check('form', function(formData) {
			Request.request('project-Approval-save', {
				async : false,
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						returnObject = result;
					}
				}
			});
		});
		return returnObject;
	}

	function findData() {
		if (objectid) {
			Request.request('project-Approval-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result);
				}
			});
		}else{
			$('#form').setValue({
				applyDate:$.hh.formatDate($.hh.getDate(), 'yyyy-MM-dd HH:mm:ss'),
				applyUser:loginUser.id,
				applyUserText:loginUser.text
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
			required :true,
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
			}
	};

</script>
</head>
<body>
	<div xtype="hh_content_main">
		<form id="form" xtype="form" class="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
					<tr>
						<td xtype="label">项目：</td>
						<td colspan="3"><span xtype="selectPageList"
						configVar=" projectConfig " ></span></td>
					</tr>
				
					<tr>
						<td xtype="label">申请人：</td>
						<td>
						<span xtype="text" config=" required :true,name : 'applyUserText', readonly :true "></span>
						<span xtype="text" config=" required :true,name : 'applyUser',hidden:true "></span></td>
						<td xtype="label">申请时间：</td>
						<td><span xtype="date" config=" required :true,name : 'applyDate' , readonly :true"></span></td>
					</tr>
				
					<tr>
						<td xtype="label">申请内容：</td>
						<td colspan="3"><span xtype="ckeditor" config="required :true, name : 'applyComment' "></span></td>
					</tr>
				
					<!-- <tr>
						<td xtype="label">立项时间：</td>
						<td><span xtype="text" config=" name : 'approvalDate' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">部门经理：</td>
						<td><span xtype="text" config=" name : 'deptManager' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">部门经理：</td>
						<td><span xtype="text" config=" name : 'deptManagerText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">部门经理意见：</td>
						<td><span xtype="text" config=" name : 'deptManagerComment' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">分管副总：</td>
						<td><span xtype="text" config=" name : 'branchDeputyManager' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">分管副总：</td>
						<td><span xtype="text" config=" name : 'branchDeputyManagerText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">分管副总意见：</td>
						<td><span xtype="text" config=" name : 'branchDeputyManagerComment' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">总经理：</td>
						<td><span xtype="text" config=" name : 'overallManager' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">总经理：</td>
						<td><span xtype="text" config=" name : 'overallManagerText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">总经理意见：</td>
						<td><span xtype="text" config=" name : 'overallManagerComment' "></span></td>
					</tr> -->
			</table>
		</form>
	</div>
	<!-- <div xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div> -->
</body>
</html>

 
 