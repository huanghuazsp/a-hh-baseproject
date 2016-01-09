<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>组织机构管理</title>
<%=SystemUtil.getBaseJs("layout", "ztree", "ztree_edit","right_menu")%>
<script type="text/javascript">
	function doAdd() {
		$('#centerdiv').undisabled();
		var selectNode = $.hh.tree.getSelectNode('orgTree');
		var iframe = window.frames['orgeditiframe'];
		iframe.callback = function() {
			$.hh.tree.refresh('orgTree');
			$('#centerdiv').disabled('请选择要编辑的机构树或添加新的数据！！');
		}
		if (selectNode) {
			iframe.newData({
				node : selectNode.id,
				sjbm_ : selectNode.code_,
				lx_ : selectNode.lx_
			});
		} else {
			iframe.newData({});
		}
		return;
		Dialog.open({
			url : 'jsp-usersystem-org-orgedit',
			params : {
				selectNode : selectNode,
				callback : function() {
					$.hh.tree.refresh('orgTree');
				}
			}
		});
	}
	function doEdit(treeNode) {
		Dialog.open({
			url : 'jsp-usersystem-org-orgedit',
			params : {
				object : treeNode,
				callback : function() {
					$.hh.tree.refresh('orgTree');
				}
			}
		});
	}
	function doDelete(treeNode) {
		$.hh.tree.deleteData({
			pageid : 'orgTree',
			action : 'usersystem-Org-deleteByIds',
			id : treeNode.id,
			callback : function(result) {
				if (result.success!=false) {
					$('#centerdiv').disabled('请选择要编辑的机构树或添加新的数据！');
				}
			}
		});
	}
	function orgTreeClick( treeNode) {
		$('#centerdiv').undisabled();
		var iframe = window.frames['orgeditiframe'];
		iframe.callback = function(object) {
			//$.hh.tree.refresh('orgTree');
			if (object.lx_) {
				treeNode.icon = object.lx_ == 0 ? "/hhcommon/images/myimage/org/group.png"
						: object.lx_ == 1 ? "/hhcommon/images/myimage/org/org.png"
								: object.lx_ == 2 ? "/hhcommon/images/myimage/org/dept.png"
										: object.lx_ == 3 ? "/hhcommon/images/myimage/org/job.png"
												: "";
			}
			treeNode.name = object.text;
			$.hh.tree.updateNode('orgTree', treeNode);
			$.hh.tree.getTree('orgTree').refresh();
		}
		iframe.findData(treeNode.id);
	}
	
	var bodyRightMenu = {
			data : [ {
				text : '导入',
				img : StaticVar.img_excel,
				onClick : function() {
					Dialog.open({
						url : 'jsp-system-tools-file',
						width : 450,
						height : 270,
						params : {
							saveUrl : 'usersystem-Org-importData',
							filePath : 'temp/file/org',
							callback : function(data) {
								$.hh.tree.refresh('orgTree');
							}
						}
					});
				}
			} ]
		};
	
	function init(){
		$('#centerdiv').disabled('请选择要编辑的机构树或添加新的数据！！');
	}
	
</script>
</head>
<body>
	<span xtype="rightMenu" configVar="bodyRightMenu"></span>
	<div xtype="border_layout">
		<div config="render : 'west' ,width:280">
			<div xtype="toolbar" config="type:'head'">
				<span xtype="button" config="onClick: doAdd ,text:'添加'"></span> <span
					xtype="button"
					config="onClick: $.hh.tree.doUp , params:{treeid:'orgTree',action:'usersystem-Org-order'}  , textHidden : true,text:'上移' ,icon : 'hh_up' "></span>
				<span xtype="button"
					config="onClick: $.hh.tree.doDown , params:{treeid:'orgTree',action:'usersystem-Org-order'} , textHidden : true,text:'下移' ,icon : 'hh_down' "></span>
				<span xtype="button"
					config="onClick : $.hh.tree.refresh,text : '刷新' ,params: 'orgTree'  "></span>
			</div>
			<span xtype="tree"
				config=" id:'orgTree', url:'usersystem-Org-queryTreeList' ,remove: doDelete , onClick : orgTreeClick ,nheight:38 "></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="orgeditiframe" name="orgeditiframe" width=100%
				height=100% frameborder=0 src="jsp-usersystem-org-orgedit"></iframe>
		</div>
	</div>
</body>
</html>