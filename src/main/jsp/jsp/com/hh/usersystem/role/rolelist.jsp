<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>角色管理</title>
<%=SystemUtil.getBaseJs("layout", "ztree", "ztree_check")%>
<script type="text/javascript">
	function doAdd() {
		Dialog.open({
			url : 'jsp-usersystem-role-roleedit',
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
				url : 'jsp-usersystem-role-roleedit',
				params : {
					row : row,
					callback : function() {
						$("#pagelist").loadData();
					}
				}
			});
		});
	}
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'usersystem-role-deleteByIds'
		});
	}

	function rendernzt(value) {
		return value == 0 ? '<font class=hh_green>正常</font>' : '<font class=hh_red>冻结</font>';
	}
	function rendernlx(value) {
		return value == 1 ? '业务角色' : value == 2 ? "管理角色"
				: value == 3 ? "系统内置角色" : "";
	}
	function renderoper(value, row) {
		return '<a  href="javascript:loadMenuTree(\'' + value
				+ '\',\'' + row.text
				+ '\')" >权限设置</a>';
	}
	var saveRoleId = null;
	function loadMenuTree(roleId,text) {
		$("#menuTreeDiv").undisabled();
		$("#menuTreeDivspan").html(text);
		saveRoleId = roleId;
		if ($('#menuid').length == 0) {
			treeconfig.params = {
				roleid : roleId
			};
			var menuTree = $('<span id="menuid" xtype="tree" configVar="treeconfig"></span>');
			$('#menuTreeDiv').append(menuTree);
			menuTree.render();
		} else {
			$.hh.tree.loadData('menuTree', {
				params : {
					roleid : roleId
				}
			});
		}
	}

	function doSave() {
		if (saveRoleId) {
			var menuids = $.hh.objsToStr($.hh.tree
					.getCheckedNodes('menuTree'), 'id');
			Request.request('usersystem-role-saveJsMenu', {
				data : {
					menuIds : menuids,
					id : saveRoleId
				}
			});
		} else {
			Dialog.infomsg('请选择角色！');
		}
	}

	var cztreeconfig = {
		id : 'cztree',
		url : 'usersystem-operate-queryCheckOperateListByPid',
		check : {
			radioType : 'level',
			chkStyle : "radio",
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		}
	}

	function menuTreeClick(treeNode) {
		if (treeNode.leaf == 1) {
			$("#czTreeDiv").undisabled();
			$("#czTreeDivspan").html(treeNode.text);
			cztreeconfig.params = {
				roleid : saveRoleId,
				vpid : treeNode.id
			};
			if ($('#cztreespan').length == 0) {
				var menuTree = $('<span id="cztreespan" xtype="tree" configVar="cztreeconfig"></span>');
				$('#czTreeDiv').append(menuTree);
				menuTree.render();
			} else {
				$.hh.tree.loadData('cztree', {
					params : cztreeconfig.params
				});
			}
		} else {
			$("#czTreeDiv").disabled('请选择菜单！');
		}
	}

	function doOperSave() {
		var menuId = cztreeconfig.params.vpid;
		var roleId = cztreeconfig.params.roleid;
		if (roleId && menuId) {
			var czid_operLevel = $.hh.objsToStr($.hh.tree
					.getCheckedNodes('cztree'), 'id');
			Request.request('usersystem-role-saveJsOper', {
				data : {
					menuIds : menuId,
					roles : roleId,
					czid_operLevel : czid_operLevel
				}
			});
		} else {
			Dialog.infomsg('请选择角色和菜单！');
		}
	}

	var treeconfig = {
		id : 'menuTree',
		url : 'usersystem-menu-queryMenuAllListByPid',
		onClick : menuTreeClick,
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		}
	}

	function init() {
		$("#menuTreeDiv").disabled('请选择角色！');
		$("#czTreeDiv").disabled('请选择菜单！');
	}
</script>
</head>
<body xtype="border_layout">
	<div>
		<div xtype="toolbar" config="type:'head'">
			<span xtype="button" config="onClick:doAdd,text:'添加'"></span> <span
				xtype="button" config="onClick:doEdit,text:'修改'"></span> <span
				xtype="button" config="onClick:doDelete,text:'删除'"></span>
		</div>
		<div id="pagelist" xtype="pagelist"
			config=" url: 'usersystem-role-queryPagingData' ,column : [
					{
						name : 'text' ,
						text : '名称'
					},{
						name : 'state' ,
						text : '状态',
						width : 50,
						render : 'rendernzt'
					},{
						name : 'nlx' ,
						text : '类型',
						render : 'rendernlx'
					},{
						name : 'vbz' ,
						text : '备注',
						contentwidth: '150'
					},{
						name : 'id' ,
						text : '操作',
						width: '40',
						render : 'renderoper'
					}
				]">
		</div>
	</div>
	<div config="render : 'east' ,width:400 ">
		<div xtype="border_layout">
			<div id="menuTreeDiv" config="render : 'west'"  >
				<div xtype="toolbar" config="type:'head'">
					<span xtype="button" config="onClick:doSave,text:'保存权限'"></span>
					&nbsp;<span id="menuTreeDivspan" style="color:red;" ></span>
				</div>
			</div>
			<div id="czTreeDiv">
				<div xtype="toolbar" config="type:'head'">
					<span xtype="button" config="onClick:doOperSave,text:'保存权限'"></span>
					&nbsp;<span id="czTreeDivspan" style="color:red;" ></span>
				</div>
			</div>
		</div>
	</div>
</body>
</html>