<%@page import="com.hh.system.util.Convert"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>日程安排</title>
<%=SystemUtil.getBaseJs()%>
<meta charset='utf-8' />
<link
	href='/hhcommon/opensource/jquery/fullcalendar/2.0.1/fullcalendar.css'
	rel='stylesheet' />
<link
	href='/hhcommon/opensource/jquery/fullcalendar/2.0.1/fullcalendar.print.css'
	rel='stylesheet' media='print' />
<script
	src='/hhcommon/opensource/jquery/fullcalendar/2.0.1/lib/moment.min.js'></script>
<script
	src='/hhcommon/opensource/jquery/fullcalendar/2.0.1/fullcalendar.min.js'></script>
<script>
	var currUserId = '<%=Convert.toString(request.getParameter("currUserId"))%>';
	var projectId = '<%=Convert.toString(request.getParameter("projectId"))%>';
	var isEdit_ = currUserId==''?true:false;
	if(projectId){
		isEdit_ = false;
	}
	var defaultView='basicWeek';
	if(isEdit_){
		defaultView = 'agendaWeek';
	}
	function getBackground(isOk) {
		return isOk == 0 ? 'red' : 'green';
	}
	function getClassName(level) {
		var img = '';
		if (level == '1') {
			img = 'green';
		} else if (level == '2') {
			img = 'blue';
		} else if (level == '3') {
			img = 'yellow';
		} else if (level == '4') {
			img = 'red';
		}
		return img;
	}

	function toEvents(dataList) {
		var resultList = [];
		for (var i = 0; i < dataList.length; i++) {
			resultList.push(toEvent(dataList[i]));
		}
		return resultList;
	}
	function toEvent(data) {
		if (data) {
			var title =  data.content ;
			
			if(data.summary){
				title+='\n总结：' +data.summary;
			}
			if(data.projectIdText && !projectId){
				title+='\n项目：' +data.projectIdText;
			}
			if(projectId){
				title+='\n创建者：' +data.createUserName;
			}
			var data = $.extend(data, {
				title : title,
				start : $.hh.formatDate(data.start,'yyyy-MM-dd HH:mm:ss'),
				end : $.hh.formatDate(data.end,'yyyy-MM-dd HH:mm:ss'),
				color : getBackground(data.isOk),
				className : getClassName(data.level),
				textColor : null,
				borderColor : null,
				level :data.level
			});
		}
		return data;
	}

	function getEventObject(event) {
		var object = {};
		object.start = event.start.format('YYYY-MM-DD HH:mm:ss');
		if(event.end){
			object.end = event.end.format('YYYY-MM-DD HH:mm:ss');
		}
		object.id = event.id;
		object.content = event.content;
		object.summary = event.summary;
		object.projectId = event.projectId;
		object.projectIdText = event.projectIdText;
		
		object.participants = event.participants;
		object.level = event.level || '0';
		object.isOk = event.isOk;
		object.userId = event.userId;
		return object;
	}

	$(document)
			.ready(
					function() {
						renderData();
					});
	
	function renderData(){
		$('#calendar')
		.fullCalendar(
				{
					defaultView: defaultView,
					//lazyFetching : false,
					header : {
						//month,basicWeek,basicDay,agendaWeek,agendaDay
						left : 'title',
						center : 'prevYear,prev,next,nextYear today',
						right : 'month,basicWeek,agendaWeek,basicDay,agendaDay'
					},
					titleFormat : {
						month : 'YYYY年MM月',
						day : 'YYYY年MM月DD日'
					},
					monthNames : [ "一月", "二月", "三月",
							"四月", "五月", "六月", "七月",
							"八月", "九月", "十月", "十一月",
							"十二月" ],
					monthNamesShort : [ "一月", "二月",
							"三月", "四月", "五月", "六月",
							"七月", "八月", "九月", "十月",
							"十一月", "十二月" ],
					dayNames : [ "周日", "周一", "周二",
							"周三", "周四", "周五", "周六" ],
					dayNamesShort : [ "周日", "周一", "周二",
							"周三", "周四", "周五", "周六" ],
					today : [ "今天" ],
					buttonText : {
						today : '当前',
						month : '月',
						week : '周',
						day : '日',
						agendaWeek : '周[时间]',
						agendaDay : '日[时间]',
						prev : '‹', // ‹
						next : '›', // ›
						prevYear : '«', // «
						nextYear : '»' // »
					},
					selectable : isEdit_,
					selectHelper : isEdit_,
					editable : isEdit_,
					eventClick : function(calEvent,
							jsEvent, view) {
						if(isEdit_){
							var object = getEventObject(calEvent);
							Dialog
									.open({
										url : 'jsp-oa-schedule-calendaredit',
										params : {
											object : object,
											callback : function(
													resultData) {
												if (resultData == 'delete') {
													$(
															'#calendar')
															.fullCalendar(
																	'removeEvents',
																	[ object.id ]);
												} else {
													$
															.extend(
																	calEvent,
																	toEvent(resultData));
													$(
															'#calendar')
															.fullCalendar(
																	'updateEvent',
																	calEvent,
																	true);
													renderEvent();
												}

											}
										}
									});
						}
					},
					eventDrop : function(event) {
						Request
								.request(
										'oa-Schedule-save',
										{
											data : getEventObject(event)
										});
					},
					eventResize : function(event) {
						Request
								.request(
										'oa-Schedule-save',
										{
											data : getEventObject(event)
										});
					},
					select : function(start, end) {
						var object = {
							start : start
									.format('YYYY-MM-DD HH:mm:ss'),
							end : end
									.format('YYYY-MM-DD HH:mm:ss'),
							level:'0'
						};
						Dialog
								.open({
									url : 'jsp-oa-schedule-calendaredit',
									params : {
										object : object,
										callback : function(
												resultData) {
											$(
													'#calendar')
													.fullCalendar(
															'renderEvent',
															toEvent(resultData));
											$(
													'#calendar')
													.fullCalendar(
															'unselect');
											renderEvent();
										}
									}
								});
					},
					
					events : function(start, end, ddd,
							callback) {
						Request
								.request(
										'oa-Schedule-queryListByDate',
										{
											data : {
												startDate : start
														.format('YYYY-MM-DD HH:mm:ss'),
												endDate : end
														.format('YYYY-MM-DD HH:mm:ss'),
														'currUserId': currUserId,
														'projectId' : projectId,
														level:'0'
											}
										},
										function(result) {
											callback(toEvents(result));
											renderEvent();
										});
					}
				});

	}
	
	
	function renderEvent(){
		$('.fc-event-inner').each(function(){
			$(this).attr('title',$(this).text());
		});
	}
</script>
<style>
body {
	padding: 0;
	font-size: 14px;
}

.fc-event-time {
	padding-left: 20px;
}

.fc-event-title {
	padding-left: 20px;
}

.green {
	background-image: url(/hhcommon/images/icons/flag/flag_green.png);
	width: 16px;
	height: 16px;
	background-repeat: no-repeat;
}

.red {
	background-image: url(/hhcommon/images/icons/flag/flag_red.png);
	width: 16px;
	height: 16px;
	background-repeat: no-repeat;
}

.blue {
	background-image: url(/hhcommon/images/icons/flag/flag_blue.png);
	width: 16px;
	height: 16px;
	background-repeat: no-repeat;
}

.yellow {
	background-image: url(/hhcommon/images/icons/flag/flag_yellow.png);
	width: 16px;
	height: 16px;
	background-repeat: no-repeat;
}

#calendar {
	width: 900;
	margin: 10px;
}
</style>
</head>
<body>
	<div id='calendar'></div>
</body>
</html>