<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=SystemUtil.getBaseJs()%>

<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'project-Bug-deleteByIds'
		});
	}
	function doAdd() {
		Dialog.open({
			url : 'jsp-project-bug-BugEdit',
			params : {
				callback : function() {
					$("#pagelist").loadData();
				}
			}
		});
	}
	function doEdit() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-project-bug-BugEdit',
				urlParams : {
					id : row.id
				},
				params : {
					callback : function() {
						$("#pagelist").loadData();
					}
				}
			});
		});
	}
	function doView(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			doView2(row.id,row.text)
		});
	}
	
	function doView2(id,text){
		$.hh.addTab({
			id : id,
			text :  document.title+'-'+text,
			src : 'jsp-project-bug-BugView?' + $.param({
				id : id
			}),
			params:{
				callback:function(){
					doQuery();
				}
			}
		});
	}
	
	function textRender(value,data){
		return '<a href="javascript:doView2(\''+data.id+'\',\''+value+'\')">'+value+'</a>';
	}
	
	function doQuery() {
		$('#pagelist').loadData({
			params : $('#queryForm').getValue()
		});
	}
	
	function stateRender(value){
		if(value==0){
			return '<font class=hh_red>新建</font>';
		}else if(value==1){
			return '<font class=hh_red>解决</font>';
		}else if(value==2){
			return '<font class=hh_red>重现</font>';
		}else if(value==9){
			return '<font class=hh_green>关闭</font>';
		}
	}
	
	
	
	var  queryHtml = '<table xtype="form" id="queryForm" style="">'
			+'<tr>'
			+'<td xtype="label">名称：</td>'
			+'<td><span xtype="text" config=" name : \'text\' ,enter: doQuery "></span></td>'
			+'<td style="width:100px;"><span	xtype="button" config="onClick: doQuery ,text:\'查询\' , itype :\'query\' "></span></td>'
		+'</tr>'
		+'</table>';
	var projectConfig = {
			openWidth:800,
			name : 'projectId',
			findTextAction :'project-ProjectInfo-findObjectById' ,
			pageconfig:{
				queryHtml : queryHtml,
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
			modularConfig.params={
					projectId : projectId1
			};
			$('#modularIdSpan').render(modularConfig);
		}
	}
	
	var modularConfig = {
		render:false,
		name:'modularId',
		noCheckParent:true,
		url : 'project-ProjectModular-queryTreeListSelect'
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doAdd,text:'添加' , itype :'add' "></span>
		<span xtype="button"
			config="onClick:doEdit,text:'修改' , itype :'edit' "></span> <span
			xtype="button" config="onClick:doDelete,text:'删除' , itype :'delete' "></span>
		<!--  <span
			xtype="button" config="onClick: doQuery ,text:'查询' , itype :'query' "></span> --> <span
			xtype="button"
			config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'project-Bug-order'}  ,  icon : 'hh_up' "></span>
		<span xtype="button"
			config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'project-Bug-order'} , icon : 'hh_down' "></span>
	
		<span xtype="button" config="onClick: doView ,text:'查看' , itype :'view' "></span>
	</div>
	<table xtype="form" id="queryForm" style="width:80%;">
		<tr>
			<td xtype="label">名称：</td>
			<td><span xtype="text" config=" name : 'text' ,watermark:'项目名称/名称' "></span></td>
			<td colspan="3" style="text-align:center;"><span xtype="radio"
						config="name: 'state' ,value : 99,  data :[
						{id:99,text:'所有'},
						{id:0,text:'新建'},
						{id:1,text:'解决'},
						{id:2,text:'重现'},
						{id:9,text:'关闭'},
						{id:90,text:'未关闭'},
						{id:91,text:'未解决'}
						] ,onChange:doQuery"></span>
			</td>
		</tr>
		<tr>
			<td xtype="label">项目：</td>
			<td><span xtype="selectPageList" configVar=" projectConfig " ></span></td>
			<td xtype="label">模块：</td>
			<td><span id="modularIdSpan" xtype="selectTree" configVar=" modularConfig " ></span></td>
			<td rowspan="3"><span xtype="button"
						config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
		</tr>
		<tr>
			<td xtype="label">发现人：</td>
			<td><span xtype="text" config=" name : 'findUserText' "></span></td>
			<td xtype="label">负责人：</td>
			<td><span xtype="text" config=" name : 'processingPeopleText'"></span></td>
		</tr>
		<tr>
			<td xtype="label">解决人：</td>
			<td><span xtype="text" config=" name : 'solveUserText'"></span></td>
			<td xtype="label">关闭人：</td>
			<td><span xtype="text" config=" name : 'closeUserText'"></span></td>
		</tr>
	</table> 
	<div id="pagelist" xtype="pagelist"
		config=" params:{state:99}, url: 'project-Bug-queryPagingData' ,column : [
			{
				name : 'state' ,
				text : '状态',
				render : stateRender,
				width:30
			},
			{
				name : 'text' ,
				text : '名称',
				render: textRender
			},
			{
				name : 'projectIdText' ,
				text : '项目名称'
			},
		
			{
				name : 'modularIdText' ,
				text : '模块名称'
			},
		
			{
				name : 'processingPeopleText' ,
				text : '负责人名称'
			}
		
	]">
	</div>
</body>
</html>