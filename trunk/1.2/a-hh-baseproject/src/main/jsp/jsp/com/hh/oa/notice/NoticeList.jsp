<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<% 
	String type=request.getParameter("type");
	String pageUrl = "oa-Notice-queryShouPage";
	if("wfbd".equals(type)){
		 pageUrl = "oa-Notice-queryPagingData?type=wfbd";
	}else if("sygg".equals(type)){
		 pageUrl = "oa-Notice-queryPagingData?type=sygg";
	}
%>
<html>
<head>
<title>数据列表</title>
<%=BaseSystemUtil.getBaseJs()%>

<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'oa-Notice-deleteByIds'
		});
	}
	function doQuery() {
		$('#pagelist').loadData({
			params : $('#queryForm').getValue()
		});
	}
	
	function doView() {
			$.hh.pagelist.callRow("pagelist", function(row) {
					Dialog.open({
						url : 'jsp-oa-notice-NoticeView',
						urlParams : {
							id : row.id
						}
					});
			});
	}
	
	function doUpdate() {
		$.hh.pagelist.callRow("pagelist", function(row) {
				parent.updateGG(row.id);
		});
}
	
	function readrender(value) {
		return value == 0 ? '<img src="'+$.hh.property.img_email_close+'" />'
				: '<img src="'+$.hh.property.img_email_open+'" />';
	}
	
	function deployrender(value) {
		return value == 0 ? '未发布'
				: '已发布';
	}
	
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button"
			config="onClick: doView ,text:'查看' , itype :'view' "></span>
			<% if("wfbd".equals(type)){ %>
			<span
			xtype="button" config="onClick: doUpdate ,text:'修改' , itype :'edit' "></span>
		<span
			xtype="button" config="onClick:doDelete,text:'删除' , itype :'delete' "></span>
			<%} %>
	</div>
	<table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">标题：</td>
			<td><span xtype="text" config=" name : 'title'"></span></td>
			<td><span xtype="button"
						config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
		</tr>
	</table> 
	<div id="pagelist" xtype="pagelist"
		config=" url: '<%=pageUrl %>' ,column : [
		<% if("ggtz".equals(type)){ %>
		{
			name : 'read' ,
			text : '状态',
			width :30,
			render : 'readrender'
		},
		<%}else{ %>
		{
			name : 'deploy' ,
			text : '是否发布',
			width :50,
			render : deployrender
		},
		<%} %>
		<% if("sygg".equals(type)){ %>{
			name : 'createUserName' ,
			text : '发布人',
			width:100
		},<%} %>{
			name : 'typeText' ,
			text : '公告类型',
			width :60
		},{
			name : 'createTime' ,
			text : '时间',
			render:'datetime',
			width:120
		},{
			name : 'title' ,
			text : '标题',
			align : 'left'
		}
		
	]">
	</div>
</body>
</html>