<%@page import="com.hh.system.util.Convert"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=SystemUtil.getBaseJs()+SystemUtil.getUser()%>

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
	function doEdit(view) {
		$.hh.pagelist.callRow("pagelist", function(row) {
			var p = '';
			if(view==1){
				p="&view=1";
			}
			Dialog.open({
				url : 'jsp-system-resources-ResourcesEdit?type='+type1+p,
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
		doEdit(1);
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
		
		var data = {type:type1};
		<%if("1".equals(request.getParameter("share"))){%>
		data.state=1;
		<%} %>
		$('#pagelist').loadData({
			params : data
		});
	}
	function renderData(value,item){
		var shareimg = '';
		if(item.state==1){
			shareimg = '<img src="/hhcommon/images/icons/folder/folder_star.png">';
		}
		var value = item.files;
		var table = $('<table></table>');
		if(value){
			var fileList = $.hh.toObject(value);
			for(var i=0;i<fileList.length;i++){
				var data = fileList[i];
				var tr = $('<tr></tr>');
				var td = $('<td></td>');
				var td2 = $('<td></td>');
				td2.append(shareimg);
				var img = '';
				if(item.img){
					img = $("<img style=\"height:30px;width:30px;\" src=\"system-File-download?params={id:'"+item.img+"'}\">");
					td2.append(img);
				}
				
				tr.append(td2).append(td);
				table.append(tr);
				td.append('<a href="javascript:Request.download(\''+data.id+'\');">'+(data.text||'')+'</a>');
			}
			if(fileList.length==0){
				return shareimg+(item.text||'');
			}
		}else{
			return shareimg+(item.text||'');
		}
		return table;
	}
	
	function doChange(data){
		var dataList = $('#pagelist').getWidget().getSelectDataList();
		/* var as = true;
		for(var i=0;i<dataList.length;i++){
			var data = dataList[i];
			if(data.vcreate!=loginUser.id){
				as = false;
				break;
			}
		}
		if(as){
			$('#upBtn').show();
			$('#downBtn').show();
			$('#editBtn').show();
			$('#deleteBtn').show();
		}else{
			$('#upBtn').hide();
			$('#downBtn').hide();
			$('#editBtn').hide();
			$('#deleteBtn').hide();
		} */
	}
	
	function doSetState(state) {
		$.hh.pagelist.callRows( 'pagelist', function(rows) {
				var ids = $.hh.objsToStr(rows);
				var data = {};
				data.ids = ids;
				data.state = state || 0;
				Request.request('system-Resources-doSetState', {
					data : data
				}, function(result) {
					if (result.success != false) {
						$("#pagelist" ).loadData();
					}
				});
		});
		
	}
	
	function doGX(){
		doSetState(1);
	}
	function doQXGX(){
		doSetState(0);
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<%if(!"1".equals(request.getParameter("share"))){%>
		
		<span xtype="button" config="onClick:doAdd,text:'添加' , itype :'add' "></span>
		<span xtype="button"
			id="editBtn"  config="onClick:doEdit,text:'修改' , itype :'edit' "></span> <span
			id="deleteBtn" xtype="button" config="onClick:doDelete,text:'删除' , itype :'delete' "></span>
		<!--  <span
			xtype="button" config="onClick: doQuery ,text:'查询' , itype :'query' "></span> --> <span
			id="upBtn" xtype="button"
			config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'system-Resources-order'}  ,  icon : 'hh_up' "></span>
		<span xtype="button"
			id="downBtn" config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'system-Resources-order'} , icon : 'hh_down' "></span>
			
			<span
			xtype="button" config="onClick: doGX ,text:'共享' "></span>
			<span
			xtype="button" config="onClick: doQXGX ,text:'取消共享' "></span>
			<%} %>
			<span
			xtype="button" config="onClick: doView ,text:'查看',itype:'view' "></span>
	</div>
	<!-- <table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">test：</td>
			<td><span xtype="text" config=" name : 'test'"></span></td>
		</tr>
	</table> -->
	<div id="pagelist" xtype="pagelist"
		config="
		<%if("1".equals(request.getParameter("share"))){%>
		params:{state:1},
		<%} %>
		  doChange : doChange , url: 'system-Resources-queryPagingData' ,column : [
		
		{
				name : 'vcreateName' ,
				text : '创建人',
				align:'center',
			width:80
			},{
			name : 'dcreate' ,
			text : '时间',
			render:'datetime',
			width:120
		},
		
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