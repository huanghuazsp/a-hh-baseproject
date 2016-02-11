<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform","pinyin")%>
<script type="text/javascript">
	var params = BaseUtil.getIframeParams();
	var width = 600;
	var height = 400

	var objectid = params.treeNode ? params.treeNode.id : '';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('system-SysDataType-saveTree', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						params.callback();
						Dialog.close();
					}
				}
			});
		});
	}

	function findData() {
		if (objectid) {
			Request.request('system-SysDataType-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result);
				}
			});
		}else{
			if (params.selectNode) {
				if (params.selectNode.isParent) {
					$("#node_span").setValue(params.selectNode);
				}
			}
		}
	}

	function init() {
		findData();
	}
	function zwblur(){
		if(!$('#span_code').getValue()){
			$('#span_code').setValue(pinyin.getPinyin($('#span_text').getValue()));
		}
	}
	$(function(){
		$('#refreshName').click(function(){
			$('#span_code').setValue(pinyin.getPinyin($('#span_text').getValue()));
		});
	});
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
				<tbody>
					<tr>
						<td xtype="label">名称：</td>
						<td><span xtype="text" config=" name : 'text',required :true,blur: zwblur "></span></td>
						<td xtype="label">父节点：</td>
						<td><span xtype="selectTree" id="node_span"
							config="name: 'node' , tableName : 'SYS_DATA_TYPE', params : {isNoLeaf : true},  url : 'system-SysDataType-queryTreeList'"></span>
						</td>
					</tr>
					<tr>
						<td xtype="label">类型：</td>
						<td><span id="leafspan" xtype="radio"
							config="name: 'leaf' ,defaultValue : 0, data :[{id:1,text:'字典项'},{id:0,text:'类别'}]"></span></td>
						<td xtype="label">是否展开：</td>
						<td><span xtype="radio"
							config="name: 'expanded' ,defaultValue : 0,  data :[{id:1,text:'是'},{id:0,text:'否'}]"></span></td>
					</tr>
					<tr>
						<td xtype="label">标识：<img id="refreshName" style="cursor:pointer;"  src="/hhcommon/opensource/jquery/image/16/refresh.png"  title="根据中文名获取拼音"  /></td>
						<td colspan="3"><span xtype="text" config=" required :true,name : 'code'"></span></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span>
		<span xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>