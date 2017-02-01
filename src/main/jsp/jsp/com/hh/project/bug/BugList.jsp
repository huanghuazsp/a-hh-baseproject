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
			$.hh.addTab({
				id : row.id,
				text :  document.title+'-'+row.text,
				src : 'jsp-project-bug-BugView?' + $.param({
					id : row.id
				}),
				params:{
					callback:function(){
						doQuery();
					}
				}
			});
		});
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
	<table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">名称：</td>
			<td><span xtype="text" config=" name : 'text'"></span></td>
			<td><span xtype="button"
						config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
		</tr>
	</table> 
	<div id="pagelist" xtype="pagelist"
		config=" url: 'project-Bug-queryPagingData' ,column : [
			{
				name : 'state' ,
				text : '状态',
				render : stateRender,
				width:30
			},
			{
				name : 'text' ,
				text : '名称'
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
				text : '处理人名称'
			}
		
	]">
	</div>
</body>
</html>