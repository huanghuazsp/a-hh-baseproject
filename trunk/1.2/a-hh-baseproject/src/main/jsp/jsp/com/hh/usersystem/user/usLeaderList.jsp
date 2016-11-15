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
		id : 'orgTree' ,nheight:42,
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
		itemRightMenu : [ {
			text : '刷新',
			img : $.hh.property.img_refresh,
			onClick : function() {
				$.hh.tree.refresh('orgTree');
			}
		} ]
	}
	function allData(){
		selectNode = null;
		doQuery();
	}
	function doQuery(node) {
		if (node) {
			selectNode = node;
		}
		if (selectNode) {
			$('#selectNodeTd')
					.html(
							selectNode.text
									+ '&nbsp;&nbsp;<a href="javascript:allData();">所有数据</a>');
		} else {
			$('#selectNodeTd').html('所有数据');
		}

		var paramsObj = $('#queryForm').getValue();
		if(selectNode){
			paramsObj.objectId = selectNode.id;
		}
		$('#pagelist').loadData({
			params : paramsObj
		});
	}

	function deleteLeader() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'usersystem-UsLeader-deleteByIds'
		});
	}

	function addLeader() {
		if (selectNode) {
			$.hh.selectUser.openUser({
				callback : function(data) {
					Request.request('usersystem-UsLeader-addLeaders', {
						data : {
							objectId : selectNode.id,
							leaderId : data.id
						},
						callback : function() {
							doQuery();
						}
					});
				},many:true
			});
		} else {
			Dialog.infomsg("请选中被分管对象！");
		}
	}
	function querytree(){
		$('#span_orgTree').loadData({
			params : {text:$('#span_orgText').getValue()}
		});
	}
</script>
</head>
<body xtype="border_layout">
	<div config="render : 'west' ,width:260 "  style="overflow :hidden; ">
		<div xtype="toolbar" config="type:'head'">
			<table style="font-size: 12" width=100%  cellspacing="0" cellpadding="0" ><tr>
			<td >
			<span xtype="text" config=" name : 'orgText' ,enter: querytree"></span>
			</td>
			<td width="40px" style="text-align:right;">
			<span xtype="button" config=" icon :'hh_img_query' , onClick : querytree "></span>
			</td><tr></table>
		</div>
		<span xtype="tree" configVar="orgtreeconfig"></span>
	</div>
	<div>

		<div xtype="toolbar" config="type:'head'">
			<span xtype="button"
				config="onClick:addLeader,text:'添加' , itype :'add' "></span> <span
				xtype="button"
				config="onClick:deleteLeader,text:'删除' , itype :'delete' "></span>
		</div>

		<table xtype="form" id="queryForm" style="width: 650px;">
			<tr>
				<td width=200px id="selectNodeTd" style="text-align: center;">所有数据</td>
				<td xtype="label">领导：</td>
				<td><span xtype="selectUser" config="name: 'leaderId'  " ></span></td>
				<td width="80px"><span xtype="button"
					config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
			</tr>
		</table>
		<div id="pagelist" xtype="pagelist"
			config=" url: 'usersystem-UsLeader-queryPagingData' ,column : [
		{
			name : 'objectText' ,
			text : '分管对象'
		},{
			name : 'leaderText' ,
			text : '领导名称'
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