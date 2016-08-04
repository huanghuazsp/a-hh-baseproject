<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>日程安排</title>
<%=SystemUtil.getBaseJs("layout", "ztree")%>
<script>
	var selectNode = null;
	var orgtreeconfig = {
			id : 'orgTree',
			url : 'usersystem-Org-queryOrgAndUsersList',
			onClick : function(node) {
				doQuery(node);
			},
			rightMenu : [ {
				text : '刷新',
				img : $.hh.property.img_refresh,
				onClick : function() {
					$.hh.tree.refresh('orgTree');
				}
			} ],
			itemRightMenu : [  {
				text : '刷新',
				img : $.hh.property.img_refresh,
				onClick : function() {
					$.hh.tree.refresh('orgTree');
				}
			} ]
		}
	function doQuery(node) {
		if(node){
			selectNode=node;
		}
		var paramsObj = {};
		paramsObj.objectId = selectNode.id;
		$('#pagelist').loadData({
			params : paramsObj
		});
	}
	
	function deleteLeader(){
		if(selectNode){
			$.hh.pagelist.callRows('pagelist', function(rows) {
				Dialog.confirm({
					message : '您确认要删除数据吗？',
					yes : function(result) {
						var ids = $.hh.objsToStr(rows);
						var data = {};
						data.leaderId = ids;
						data.objectId = selectNode.id;
						Request.request('usersystem-UsLeader-deleteLeaders', {
							data : data
						}, function(result) {
							doQuery();
						});
					}
				});
			});
		}else{
			Dialog.infomsg("请选中被分管对象！");
		}
	}
	
	function addLeader(){
		if(selectNode){
			$.hh.selectUser.openUser({
				callback:function(data){
					Request.request('usersystem-UsLeader-addLeaders', {
						data : {
							objectId : selectNode.id,
							leaderId : data.id
						},
						callback:function(){
							doQuery();
						}
					});
				}
			});
		}else{
			Dialog.infomsg("请选中被分管对象！");
		}
	}
</script>
</head>
<body  xtype="border_layout">
	<div config="render : 'west' ,width:260 ">
		<span xtype="tree" configVar="orgtreeconfig"></span>
	</div>
	<div  style="overflow: visible;" >
	
	
	<div xtype="toolbar" config="type:'head'">
				<span xtype="button"
					config="onClick:addLeader,text:'添加' , itype :'add' "></span> 
				<span xtype="button"
					config="onClick:deleteLeader,text:'删除' , itype :'delete' "></span> 
			</div>
	<div id="pagelist" xtype="pagelist"
				config=" url: 'usersystem-UsLeader-queryPagingData' ,column : [
		{
			name : 'leaderText' ,
			text : '用户名称'
		},{
			name : 'leaderVdzyj' ,
			text : '电子邮件'
		},{
			name : 'leaderVdh' ,
			text : '联系电话'
		}
	]">
			</div>
	</div>
</body>
</html>