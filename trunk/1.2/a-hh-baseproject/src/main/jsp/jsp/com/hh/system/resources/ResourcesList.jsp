<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=BaseSystemUtil.getBaseJs()%>

<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'system-Resources-deleteByIds'
		});
	}
	function doAdd() {
		Dialog.open({
			url : 'jsp-system-resources-ResourcesEdit?type='+type1,
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
				url : 'jsp-system-resources-ResourcesEdit?type='+type1,
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
	function doQuery() {
		var params = $('#queryForm').getValue();
		params.type = type1;
		$('#pagelist').loadData({
			params : params
		});
	}
	var type1 = null;
	function iframeClick(data) {
		type1=data.id;
		$('#pagelist').loadData({
			params : {type:type1}
		});
	}
	function renderData(value,item){
		var value = item.files;
		var table = $('<table></table>');
		if(value){
			var fileList = $.hh.toObject(value);
			for(var i=0;i<fileList.length;i++){
				var data = fileList[i];
				var tr = $('<tr></tr>');
				var td = $('<td></td>');
				var td2 = $('<td></td>');
				var img = '';
				if(item.img){
					img = $("<img style=\"height:30px;width:30px;\" src=\"system-File-download?params={id:'"+item.img+"'}\">");
					td2.append(img);
				}
				
				tr.append(td2).append(td);
				table.append(tr);
				td.append('<a href="javascript:Request.download(\''+data.id+'\');">'+(data.text||'')+'</a>');
			}
		}
		return table;
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
			config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'system-Resources-order'}  ,  icon : 'hh_up' "></span>
		<span xtype="button"
			config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'system-Resources-order'} , icon : 'hh_down' "></span>
	</div>
	<!-- <table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">test：</td>
			<td><span xtype="text" config=" name : 'test'"></span></td>
		</tr>
	</table> -->
	<div id="pagelist" xtype="pagelist"
		config=" url: 'system-Resources-queryPagingData' ,column : [
		
		
		
			{
				name : 'text' ,
				text : '资源名称',
				align:'left',
				render : renderData ,
				widthAuto : true
			}
		
	]">
	</div>
</body>
</html>