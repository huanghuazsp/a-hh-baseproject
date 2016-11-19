<%@page import="com.hh.system.util.Convert"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>模块列表</title>
<%=BaseSystemUtil.getBaseJs()%>

<script type="text/javascript">

	var projectId = '<%=Convert.toString(request.getParameter("projectId"))%>';
	
	var width = 900;
	var height = 650;

	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'project-ProjectModular-deleteByIds'
		});
	}
	function doAdd() {
		Dialog.open({
			url : 'jsp-project-projectmodular-ProjectModularEdit',
			urlParams : {
				projectId:projectId
			},
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
				url : 'jsp-project-projectmodular-ProjectModularEdit',
				urlParams : {
					id : row.id,
					projectId:projectId
				},
				params : {
					callback : function() {
						$("#pagelist").loadData();
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
			config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'project-ProjectModular-order'}  ,  icon : 'hh_up' "></span>
		<span xtype="button"
			config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'project-ProjectModular-order'} , icon : 'hh_down' "></span>
	</div>
	<!-- <table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">test：</td>
			<td><span xtype="text" config=" name : 'test'"></span></td>
		</tr>
	</table> -->
	<div id="pagelist" xtype="pagelist"
		config=" params : {projectId:'<%=Convert.toString(request.getParameter("projectId"))%>'} , url: 'project-ProjectModular-queryPagingData' ,column : [
		
		
		
			{
				name : 'text' ,
				text : '名称',
				width:170
			},
		
			{
				name : 'describe' ,
				align:'left',
				text : '描述'
			}
		
	]">
	</div>
</body>
</html>