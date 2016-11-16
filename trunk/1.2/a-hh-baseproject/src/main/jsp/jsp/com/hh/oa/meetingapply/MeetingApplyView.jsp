<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>会议室申请信息</title>
<%=BaseSystemUtil.getBaseJs("checkform","date")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 650;
	var height = 450;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function findData() {
		if (objectid) {
			Request.request('oa-MeetingApply-findReadObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result, {view:true});
					$('#meeting_a').click(function(){
						openMeeting(result.meetingId);
					});
				}
			});
		}
	}
	
	function openMeeting(params) {
		Dialog.open({
			url : 'jsp-oa-meeting-MeetingView' ,
			urlParams : {
				id : params
			}
		});
	}

	function init() {
		findData();
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
				
					<tr>
						<td xtype="label">会议名称：</td>
						<td colspan="3"><a id="meeting_a" href="javascript:void(0);"><span xtype="html" config=" name : 'meetingIdText' "></span></a></td>
					</tr>
				
					<tr>
						<td xtype="label">会议主题：</td>
						<td colspan="3"><span xtype="html" config=" name : 'text' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">参与人员：</td>
						<td colspan="3"><span xtype="html" config=" name : 'attendUserText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">参与部门：</td>
						<td colspan="3"><span xtype="html" config=" name : 'attendOrgText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">开始时间：</td>
						<td><span xtype="html" config=" name : 'start' ,dateType:'yyyy年MM月dd日 （EEE） HH:mm' "></span></td>
						<td xtype="label">结束时间：</td>
						<td><span xtype="html" config=" name : 'end',dateType:'yyyy年MM月dd日 （EEE） HH:mm'  "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">描述：</td>
						<td colspan="3"><span xtype="html" config=" name : 'describe' "></span></td>
					</tr>
				
			</table>
		</form>
	</div>
	<div xtype="toolbar"> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>

 
 