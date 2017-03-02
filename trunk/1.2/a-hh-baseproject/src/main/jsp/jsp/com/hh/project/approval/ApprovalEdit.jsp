<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform", "ueditor","date","workflow")+ SystemUtil.getUser()%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 450;

	var objectid = '<%=Convert.toString(request.getParameter("objectId"))%>';
	var dataManager = WF.getDataManager(params);
	
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
					
					WF.dataManagerWidgets(dataManager);
				}
			});
		}else{
			var baseObject = {};
			if(params.object){
				baseObject.projectId = params.object.id;
				baseObject.projectIdText = params.object.text;
			}
			$('#form').setValue(baseObject);
			WF.dataManagerWidgets(dataManager);
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
			textfield:'项目',
			name : 'projectId',
			findTextAction :'project-ProjectInfo-findObjectById' ,
			pageconfig:{
				queryHtml : queryHtml(),
				url:'project-ProjectInfo-queryPartPage' ,
				column : [
					{
						name : 'text' ,
						text : '项目名称',
						render :function(value,data){
							return '<a href="javascript:params.parentPage.doViewProject(\''+data.id+'\')">'+data.text+'</a>';
						}
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


	function doViewProject(projectId){
			Dialog.open({
				url : 'jsp-project-projectinfo-ProjectInfoView',
				urlParams : {
					id : projectId
				}
			});
	}
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
						<span xtype="text" config=" textfield:'申请人',required :true,name : 'applyUserText', readonly :true,value:loginUser.text "></span>
						<span xtype="text" config=" textfield:'申请人',required :true,name : 'applyUser',hidden:true,value:loginUser.id  "></span>
						</td>
						<td xtype="label">申请时间：</td>
						<td><span xtype="date" config="textfield:'申请时间', required :true,name : 'applyDate' , readonly :true,type:'datetime',value:$.hh.formatDate($.hh.getDate(), 'yyyy-MM-dd HH:mm:ss')"></span></td>
					</tr>
					<tr>
						<td xtype="label">申请内容：</td>
						<td colspan="3"><span xtype="ckeditor" config="textfield:'申请内容',required :true, name : 'applyComment' "></span></td>
					</tr>
					
					<tr>
						<td xtype="label">部门经理：</td>
						<td>
						<span xtype="text" config=" textfield:'部门经理',required :true,name : 'deptManagerText', readonly :true,value:loginUser.text "></span>
						<span xtype="text" config=" textfield:'部门经理',required :true,name : 'deptManager',hidden:true,value:loginUser.id "></span>
						</td>
						<td xtype="label">审批时间：</td>
						<td><span xtype="date" config=" textfield:'审批时间',required :true,name : 'deptManagerDate' , readonly :true,type:'datetime' ,value:$.hh.formatDate($.hh.getDate(), 'yyyy-MM-dd HH:mm:ss')"></span></td>
					</tr>
					
					<tr>
						<td xtype="label">部门经理意见：</td>
						<td colspan="3"><span xtype="textarea" config="textfield:'部门经理意见', name : 'deptManagerComment' "></span></td>
					</tr>
					
					<tr>
						<td xtype="label">分管副总：</td>
						<td>
						<span xtype="text" config=" textfield:'分管副总',required :true,name : 'branchDeputyManagerText', readonly :true,value:loginUser.text "></span>
						<span xtype="text" config=" textfield:'分管副总',required :true,name : 'branchDeputyManager',hidden:true,value:loginUser.id "></span>
						</td>
						<td xtype="label">审批时间：</td>
						<td><span xtype="date" config="textfield:'审批时间', required :true,name : 'branchDeputyManagerDate' , readonly :true,type:'datetime' ,value:$.hh.formatDate($.hh.getDate(), 'yyyy-MM-dd HH:mm:ss')"></span></td>
					</tr>
					
					<tr>
						<td xtype="label">分管副总意见：</td>
						<td colspan="3"><span xtype="textarea" config="textfield:'分管副总意见', name : 'branchDeputyManagerComment' "></span></td>
					</tr>
					
					<tr>
						<td xtype="label">总经理：</td>
						<td>
						<span xtype="text" config=" textfield:'总经理',required :true,name : 'overallManagerText', readonly :true,value:loginUser.text "></span>
						<span xtype="text" config=" textfield:'总经理',required :true,name : 'overallManager',hidden:true,value:loginUser.id "></span>
						</td>
						<td xtype="label">审批时间：</td>
						<td><span xtype="date" config=" textfield:'审批时间',required :true,name : 'overallManagerDate' , readonly :true,type:'datetime' ,value:$.hh.formatDate($.hh.getDate(), 'yyyy-MM-dd HH:mm:ss')"></span></td>
					</tr>
					
					<tr>
						<td xtype="label">总经理意见：</td>
						<td colspan="3"><span xtype="textarea" config="textfield:'总经理意见', name : 'overallManagerComment' "></span></td>
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

 
 