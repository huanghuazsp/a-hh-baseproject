<%@page import="com.hh.usersystem.bean.usersystem.SysMenu"%>
<%@page import="com.hh.usersystem.bean.usersystem.UsUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.google.gson.Gson"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>添加快捷菜单</title>
<%=SystemUtil.getBaseJs("ztree","ztree_check")%>
<%
	Gson gson = new Gson();
UsUser hhXtYh =	(UsUser)session.getAttribute("loginuser");
List<SysMenu> hhXtCds =  hhXtYh.getHhXtCdList();
List<SysMenu> zmtbcd =  hhXtYh.getHhXtYhCdZmtbList();

String hhxtcdStr =  gson.toJson(hhXtCds);
String zmtbcdStr =  gson.toJson(zmtbcd);
%>
<script type="text/javascript">
	var height = 400;
	var params = BaseUtil.getIframeParams();
	var Menu = {
		getTreeChildrens : function(menuid) {
			var hhXtCdList = hhxtcds;
			var resultTreeChildrens = [];
			for (var i = 0; i < hhXtCdList.length; i++) {
				var hhXtCd = hhXtCdList[i];
				if (hhXtCd.node == menuid) {
					var treeNode = {};
					jQuery.extend(treeNode, hhXtCd);
					treeNode.id = hhXtCd.id;
					treeNode.pId = hhXtCd.node;
					treeNode.name = hhXtCd.text;
					treeNode.icon = hhXtCd.icon;
					treeNode.isParent = hhXtCd.leaf == 0;
					treeNode.open = hhXtCd.expanded == 1;
					Menu.addTreeChildrens(treeNode, hhXtCdList);
					resultTreeChildrens.push(treeNode);
				}
			}
			return resultTreeChildrens;
		},
		addTreeChildrens : function(parentTreeNode, hhXtCdList) {
			parentTreeNode.children = [];
			for (var i = 0; i < hhXtCdList.length; i++) {
				var hhXtCd = hhXtCdList[i];
				if (hhXtCd.node == parentTreeNode.id) {
					var treeNode = {};
					jQuery.extend(treeNode, hhXtCd);
					treeNode.id = hhXtCd.id;
					treeNode.pId = hhXtCd.node;
					treeNode.name = hhXtCd.text;
					treeNode.icon = hhXtCd.icon;
					treeNode.isParent = hhXtCd.leaf == 0;
					treeNode.open = hhXtCd.expanded == 1;
					Menu.addTreeChildrens(treeNode, hhXtCdList);
					parentTreeNode.children.push(treeNode);
				}
			}
		}
	}
	var hhxtcds =
<%=hhxtcdStr%>
	;
	var zmtbcd =
<%=zmtbcdStr%>
	;
	var zmtbcdidList = [];
	for(var i=0;i<zmtbcd.length;i++){
		zmtbcdidList.push(zmtbcd[i].id);
	}
	for(var i=0;i<hhxtcds.length;i++){
		if((zmtbcdidList.inArray(hhxtcds[i].id))){
			hhxtcds[i].nocheck=true;
		}else{
			hhxtcds[i].nocheck=false;
		}
		if(hhxtcds[i].leaf==0){
			hhxtcds[i].nocheck=true;
		}
	}
	var configTree = {
		id:'menuTreeId',
		data : Menu.getTreeChildrens('root'),
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		}
	}
	function init() {
	}
	function save(){
		var nodes = $.hh.tree.getCheckedNodes('menuTreeId');
		var ids = BaseUtil.objsToStr(nodes);
		if(ids){
			Request.request('usersystem-menu-addZmtb', {
				data : {id:ids},
				callback : function(result) {
					if (result.success!=false) {
						params.callback();
						Dialog.close();
					}
				}
			});
		}else{
			Dialog.infomsg("请选中一条数据！");
		}
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<span xtype="tree" configVar="configTree"></span>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'确定' , onClick : save "></span> 
	</div>
</body>
</html>