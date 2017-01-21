<%@page import="com.hh.system.util.Convert"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>日程编辑</title>
<%=SystemUtil.getBaseJs("checkform", "date")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 750;
	var height = 600

	var object = params.object;
	var objectid = object ? object.id : '';

	var yxjconfig = {
		name : 'level',
		value:'0',
		data : [{
					'id' : '0',
					'text' : '无优先级'
				},
				{
					'id' : '4',
					'text' : '<img src=\'/hhcommon/images/icons/flag/flag_red.png\'/>重要紧急'
				},
				{
					'id' : '3',
					'text' : '<img src=\'/hhcommon/images/icons/flag/flag_yellow.png\'/>重要不紧急'
				},
				{
					'id' : '2',
					'text' : '<img src=\'/hhcommon/images/icons/flag/flag_blue.png\'/>紧急不重要'
				},
				{
					'id' : '1',
					'text' : '<img src=\'/hhcommon/images/icons/flag/flag_green.png\'/>不重要不紧急'
				}]
	}

	function save(ok) {
		$.hh.validation.check('form', function(formData) {
			if(ok!=null){
				formData.isOk=ok;
			}else if(object){
				formData.isOk = object.isOk;
			}
			Request.request('oa-Schedule-save', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						params.callback(result);
						Dialog.close();
					}
				}
			});
		});
	}

	function ok(ok) {
		if (objectid) {
			Request.request('oa-Schedule-ok', {
				data : {
					id : objectid,
					isOk : ok
				}
			}, function(result) {
				if (result.success!=false) {
					object.isOk = ok;
					object.start = $.hh.stringToDate(object.start);
					object.end = $.hh.stringToDate(object.end);
					params.callback(object);
					Dialog.close();
				}
			});
		}
	}
	function doDelete() {
		if (objectid) {
			Request.request('oa-Schedule-deleteByIds', {
				data : {
					ids : objectid
				}
			}, function(result) {
				if (result.success!=false) {
					params.callback('delete');
					Dialog.close();
				}
			});
		}
	}

	function findData() {
		if (object) {
			if (object.id) {
				if (object.isOk == 1) {
					$('#nookspan').show();
				} else {
					$('#okspan').show();
				}
				$('#deletespan').show();
			}
			$('#form').setValue(object);
			renderModular();
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
				renderModular();
			}
	};
	
	function renderModular(){
		var projectId1 = $('#span_projectId').getValue();
		
		if(projectId1){
			modularConfig.url='project-ProjectModular-queryListByProjectId';
			modularConfig.params={
					projectId : projectId1
			};
			$('#modularIdSpan').render(modularConfig);
			$('#modularIdTr').show();
		}else{
			$('#modularIdTr').hide();
		}
		
	}
	
	var modularConfig = {
		render:false,
		name:'modularId',
		renderAfter:function (data){
			if(!data ||data.length==0){
				$('#modularIdTr').hide();
			}
			if (object && object.modularId) {
				$('#modularIdSpan').setValue(object.modularId);
			}
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
					<td colspan="3"><span id="modularIdSpan" xtype="combobox"
						configVar=" modularConfig " ></span></td>
				</tr>
				<tr>
					<td xtype="label">内容：</td>
					<td colspan="3"><span xtype="textarea"
						config=" name : 'content' ,required :true "></span></td>
				</tr>
				<!-- <tr>
					<td xtype="label">发布范围：</td>
					<td colspan="3"><span xtype="selectUser"
						config="name: 'participants' "></span></td>
				</tr> -->
				<tr>
					<td xtype="label">开始：</td>
					<td><span xtype="date"
						config="name: 'start'  ,type:'datetime' ,required :true"></span></td>
					<td xtype="label">结束：</td>
					<td><span xtype="date"
						config="name: 'end'  ,type:'datetime'  "></span></td>
				</tr>
				<tr>
					<td xtype="label">优先级：</td>
					<td colspan="3"><span xtype="radio" configVar="yxjconfig"></span></td>
				</tr>
				
				<tr>
					<td xtype="label">总结：</td>
					<td colspan="3"><span xtype="textarea"
						config=" name : 'summary' "></span></td>
				</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span id="okspan" xtype="button"
			config="text:'完成' , onClick : save ,hidden:true ,params:1"></span><span
			id="nookspan" xtype="button"
			config="text:'未完成' , onClick : save ,hidden:true,params:0"></span> <span
			id="deletespan" xtype="button"
			config="text:'删除' , onClick : doDelete ,hidden:true"></span> <span
			id="savespan" xtype="button" config="text:'保存' , onClick : save "></span>
		<span xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>