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
	function getType(ctype) {
		var img = '';
		if (ctype == 1) {
			img = '#000000';
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
			var data = $.extend(data, {
				title : data.content,
				start : $.hh.dateTimeToString(data.start),
				end : $.hh.dateTimeToString(data.end),
				color : getBackground(data.isOk),
				className : getClassName(data.level),
				textColor : getType(data.ctype),
				borderColor : getType(data.ctype)
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
		object.participants = event.participants;
		object.level = event.level;
		object.isOk = event.isOk;
		object.userId = event.userId;
		object.ctype = event.ctype;
		return object;
	}

	$(document)
			.ready(
					function() {
						$('#calendar')
								.fullCalendar(
										{
											//lazyFetching : false,
											header : {
												left : 'title',
												center : 'prevYear,prev,next,nextYear today',
												right : 'month,agendaWeek,agendaDay'
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
												prev : '‹', // ‹
												next : '›', // ›
												prevYear : '«', // «
												nextYear : '»' // »
											},
											selectable : true,
											selectHelper : true,
											eventClick : function(calEvent,
													jsEvent, view) {
												var object = getEventObject(calEvent);
												Dialog
														.open({
															url : 'jsp-oa-schedule-calendaredit?ctype='
																	+ object.ctype,
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
																	}

																}
															}
														});
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
															.format('YYYY-MM-DD HH:mm:ss')
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
																}
															}
														});
											},
											editable : true,
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
																				.format('YYYY-MM-DD HH:mm:ss')
																	}
																},
																function(result) {
																	callback(toEvents(result));
																});
											}
										});

					});
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