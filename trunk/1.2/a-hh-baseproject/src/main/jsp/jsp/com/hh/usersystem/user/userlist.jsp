<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>在线用户</title>
<%=SystemUtil.getBaseJs("layout", "ztree")%>
<script type="text/javascript">
	var selectOrgId ='';
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'usersystem-user-deleteByIds'
		});
	}
	function doAdd() {
		Dialog.open({
			url : 'jsp-usersystem-user-useredit',
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
				url : 'jsp-usersystem-user-useredit',
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
	function rendersex(value) {
		return (value == 1 ? '男' : '女')
				+ '<img src="/hhcommon/images/myimage/sex/'
				+ (value == 1 ? 'nan' : 'nv') + '.png" />';
	}
	function renderzt(value) {
		return value == 0 ? '正常' : '<font color=red>冻结</font>';
	}
	function doQuery() {
		debugger;
		var paramsObj =  $('#queryForm').getValue();
		paramsObj.orgs = selectOrgId;
		$('#pagelist').loadData({
			params : paramsObj
		});
	}

	function inExcel() {
		Dialog.open({
			url : 'jsp-system-tools-file',
			width : 450,
			height : 270,
			params : {
				saveUrl : 'usersystem-user-importData',
				type : 'temp',
				callback : function(data) {
					if (data.returnModel && data.returnModel.msg) {
						Dialog.alert(data.returnModel.msg);
					} else {
						doQuery();
					}
				}
			}
		});
	}

	function downloadExcel() {
		Request.submit('system-File-downloadFile', {
			path : 'temp/用户数据.xls'
		});
	}
	function outExcel() {
		Request.submit('usersystem-user-download', {});
	}
	
	function orgTreeClick( treeNode) {
		selectOrgId = treeNode.id;
		$('#selectNodeTd')
					.html(
							treeNode.text
									+ '&nbsp;&nbsp;<a href="javascript:allData();">所有数据</a>');
		doQuery();
	}
	function allData(){
		selectOrgId = '';
		$('#selectNodeTd').html('所有数据');
		doQuery();
	}
	function querytree(){
		$('#span_orgTree').loadData({
			params : {text:$('#span_orgText').getValue()}
		});
	}
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west' ,width:290" style="overflow :hidden; ">
			<div xtype="toolbar" config="type:'head'">
				<table style="font-size: 12" width=100%  cellspacing="0" cellpadding="0" ><tr>
				<td >
				<span xtype="text" config=" name : 'orgText' ,enter: querytree"></span>
				</td>
				<td width="40px" style="text-align:right;">
				<span xtype="button" config=" icon :'hh_img_query' , onClick : querytree "></span>
				</td><tr></table>
			</div>
			<span xtype="tree"
				config=" id:'orgTree', url:'usersystem-Org-queryTreeList' ,remove: doDelete , onClick : orgTreeClick ,nheight:42 "></span>
		</div>
		<div  id=centerdiv>
			<div xtype="toolbar" config="type:'head'">
				<span xtype="button"
					config="onClick:doAdd,text:'添加' , itype :'add' "></span> <span
					xtype="button" config="onClick:doEdit,text:'修改' , itype :'edit' "></span>
				<span xtype="button"
					config="onClick:doDelete,text:'删除' , itype :'delete' "></span> <span
					xtype="button"
					config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'usersystem-user-order'}  ,  icon : 'hh_up' "></span>
				<span xtype="button"
					config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'usersystem-user-order'} , icon : 'hh_down' "></span>
				<span xtype=menu
					config=" id:'menu1', data : [ { img : $.hh.property.img_excel , text : '导入' , onClick : inExcel } 
		,{ img : $.hh.property.img_excel ,text : '导出' , onClick : outExcel } 
		,{ img : $.hh.property.img_excel ,text : '下载模板' , onClick : downloadExcel } 
				  ]"></span>
				<span xtype="button"
					config=" text:'更多',icon : 'ui-icon-triangle-1-s' ,menuId:'menu1' "></span>
			</div>
			<table xtype="form" id="queryForm" style="width: 100%;">
				<tr>
					<td width=200px id="selectNodeTd" style="text-align: center;">所有数据</td>
					<td xtype="label" style="width:50px">名称：</td>
					<td><span xtype="text"
						config=" name : 'text' ,enter: doQuery "></span></td>
					<td xtype="label" style="width:50px">性别：</td>
					<td><span xtype="radio"
						config="name: 'nxb'  ,value : 2 , data :[{id:2,text:'所有'},{id:1,text:'男'},{id:0,text:'女'}]"></span></td>
					<td><span xtype="button"
						config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
				</tr>
			</table>
			<div id="pagelist" xtype="pagelist"
				config=" params :  {nxb:2},url: 'usersystem-user-queryPagingData' ,column : [
		{
			name : 'nxb' ,
			text : '性别',
			render:'rendersex'
		},{
			name : 'state' ,
			text : '状态',
			render:'renderzt'
		},{
			name : 'text' ,
			text : '用户名称'
		},{
			name : 'vdlzh' ,
			text : '账号'
		},{
			name : 'vdzyj' ,
			text : '电子邮件'
		},{
			name : 'vdh' ,
			text : '联系电话'
		},{
			name : 'dsr' ,
			text : '生日',
			render:'date'
		}
	]">
			</div>
		</div>
	</div>
</body>
</html>