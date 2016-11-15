<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.pk.PrimaryKey"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=BaseSystemUtil.getBaseJs("layout","ztree", "ztree_edit")%>
<%
	String iframeId = PrimaryKey.getUUID();
%>

<script type="text/javascript">
	var iframeId = '<%=iframeId%>';
	function treeClick(treeNode) {
		window.frames[iframeId].iframeClick(treeNode);
	}
	function init(){
	}
	function querytree(){
		$('#span_tree').loadData({
			params : {
				text:$('#span_treeText').getValue(),
				vcreate :$('#span_vcreate').getValue()
			}
		});
	}

</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west',width:220"  style="overflow :hidden; ">
			<div xtype="toolbar" config="type:'head'">
				<span xtype="selectUser" config=" name : 'vcreate' "></span>
				<span xtype="text" config=" name : 'treeText' ,width:160 ,enter: querytree"></span>
				<span xtype="button" config=" icon :'hh_img_query' , onClick : querytree "></span>
			</div>
			<span xtype="tree"
				config=" id:'tree', url:'system-ResourcesType-queryTreeList' ,onClick : treeClick ,params :{state:1}  ,nheight:42 "></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="<%=iframeId%>" name="<%=iframeId%>" width=100%
				height=100% frameborder=0 src="jsp-system-resources-ResourcesList?share=1"></iframe>
		</div>
	</div>
</body>
</html>