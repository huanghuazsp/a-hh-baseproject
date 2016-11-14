<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>查看公告</title>
<%=BaseSystemUtil.getBaseJs("checkform","fileUpload")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 850;
	var height = 600;
	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';


	function findData() {
		if (objectid) {
			Request.request('oa-Notice-findReadObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					result.file = result.id;
					$('#form').setValue(result, {view:true});
				}
			});
		}else{
			$('#form').setValue({
				bid : uuid,
				file : uuid,
				rangeType : 1
			}, {view:true});
		}
	}
	

	function init() {
		findData();
		$('table').css('background',$.hh.property.classObject.themeContent)
	}
	
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<table width=100%>
				<tr>
					<td xtype="label" style="text-align:left;padding:10px;">
						<div style="text-align:center;">
						<font size=4 ><b><span xtype="html" config=" name : 'title' "></span></b></font>
						</div>
						发&nbsp;件&nbsp;人：<span xtype="html" config="name: 'vcreateName' "></span><br>
						时&nbsp;&nbsp;&nbsp;间：<span xtype="html" config="name: 'dcreate' ,dateType:'yyyy年MM月dd日 （EEE） HH:mm'"></span><br>
						范&nbsp;&nbsp;&nbsp;围：<span xtype="radio"
						config="   name: 'rangeType' ,value : 1 ,data : [{id:1,text:'所有'},{id:0,text:'选择机构/部门'}] ">
						</span>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<span  xtype="html" config="name: 'rangeText',many:true  "></span>
						<br>
						附&nbsp;&nbsp;&nbsp;件： <span xtype="fileUpload"
						config="name: 'file' , type:'notice' ,request:true"></span>
					</td>
					<td xtype="label">
					</td>
				</tr>
			</table>
			<div style="padding:25px;">
						<span xtype="html" config="name: 'content' "></span>
			</div>
		</form>
	</div>
	<div xtype="toolbar">
		<span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>