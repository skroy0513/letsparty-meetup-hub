$(function() {
	/*	let clickedEvent;
		 부트스트랩의 모달객체 생성하기
		const eventModal = new bootstrap.Modal("#eventModal", {
			keyboard: false
		});
	
		 모달 엘리먼트에 이벤트 핸들러 함수 등록하기
		let el = document.querySelector("eventModal");*/

	var calendar = null;

	function clearEventField() {
		$(":input[name=title]").val("");
		$(":input[name=description]").val("");
		$(":input[name=startDate]").val("");
		$(":input[name=endDate]").val("");
		$(":input[name=startTime]").val("").prop("disabled", false);
		$(":input[name=endTime]").val("").prop("disabled", false);
		$(":checkbox[name=allDay]").prop("checked", false);
		$(":input[name=place]").val("");
		$(":input[name=noti]").val("");
	};

	//   document.addEventListener('DOMContentLoaded', function() {
	$(document).ready(function() {
		var Calendar = FullCalendar.Calendar;
		var Draggable = FullCalendar.Draggable;

		var containerEl = document.getElementById('external-events');
		var calendarEl = document.getElementById('calendar');
		var checkbox = document.getElementById('drop-remove');

		// 외부이벤트 초기화
		new Draggable(containerEl, {
			itemSelector: '.fc-event',
			eventData: function(eventEl) {
				return {
					title: eventEl.innerText
				};
			}
		});

		calendar = new Calendar(calendarEl, {
			customButtons: {
				myCustomButton: {
					text: '일정 만들기',
					click: function() {
						$("#eventModal").modal("show");
					}
				}
			},
			// 원하는 지정 날짜에 일정등록하기
			dateClick: function(info) {
				// 일정을 클릭하기
				var clickedDate = info.date;
				// 날짜가 삽입된 모달 열기
				openModalWithStartDate(clickedDate);
				/*		        
								$("#eventModal").modal("show");
								var startDate = moment(info.event.start).format('YYYY-MM-DD');
									var endDate = moment(info.event.end).format('YYYY-MM-DD');
								$("#save-event").on("click", function(){
									var title = $("title").val();
									var description = $("description").val();
									var startDate = $("startDate").val();
									var endDate = $("endDate").val();
									var startTime = $("startTime").val();
									var endTime = $("endTime").val();
								})
				*/
			},
			// 일정을 수정하는 모달 열기
			eventClick: function(info) {
				openEventDetailModal(info.event);

			},
			events:

				function(info, successCallback, failureCallback) {
					console.log(info)

					$.ajax({
						url: '/event/events',
						type: 'get',
						data: { startDate: moment(info.start).format("YYYY-MM-DD"), endDate: moment(info.end).format("YYYY-MM-DD") },
						success: function(data) {
							var events = [];

							data.forEach(function(eventData) {
								var event = {
									id: eventData.no,
									title: eventData.title,
									start: eventData.start,
									end: eventData.end
								};

								events.push(event);
							});

							successCallback(events);
							calendar.render();
						},
						error: function(error) {
							console.log(error)
							failureCallback();
						}
					});
				},

			headerToolbar: {
				left: 'prev,next,today',
				center: 'title',
				right: 'myCustomButton'
				//right: 'dayGridMonth,timeGridWeek,timeGridDay'
			},
			// 수정가능여부 
			editable: true,
			// 드래그 허용
			droppable: true,
			displayEventTime: false,
			drop: function(info) {
				// 드래그 후 제거 허용
				if (checkbox.checked) {
					// 드래그 가능한 이벤트를 목록에서 제거
					info.draggedEl.parentNode.removeChild(info.draggedEl);

				}
			},

			locale: 'ko',
		});
		/*
				// 일정 클릭 시 팝업으로 일정 정보 표시
				calendar.setOption('eventClick', function(info) {
				  var startDate = moment(info.event.start).format('YYYY-MM-DD');
				  var endDate = moment(info.event.end).format('YYYY-MM-DD');
					
				  alert('일정 제목: ' + info.event.title + '\n시작: ' + startDate + '\n종료: ' + endDate);
				});
				calendar.render();
		*/


		$(":checkbox[name=allDay]").change(function() {
			if ($(this).prop('checked')) {
				$(":input[name=startTime]").prop("readOnly", true);
				$(":input[name=endTime]").prop("readOnly", true);
			} else {
				let startTime = moment().format('HH:mm');
				let endTime = moment().add('1', 'h').format('HH:mm');
				$(":input[name=startTime]").prop("readOnly", false).val(startTime);
				$(":input[name=endTime]").prop("readOnly", false).val(endTime);
			}
		})

		$("#save-event").click(function() {

			let startDate = $(":input[name=startDate]").val();
			console.log(`startDate: ${startDate}`);
			let endDate = $(":input[name=endDate]").val();
			let startTime = $(":input[name=startTime]").val();
			console.log(`startTime: ${startTime}`);
			let endTime = $(":input[name=endTime]").val();
			let eventData = {
				title: $(":input[name=title]").val(),
				description: $(":input[name=description]").val(),
			};
			let allDay = $(":checkbox[name=allDay]:checked").val();
			console.log("flag");
			console.log(allDay);
			if (allDay) {	// 체크되었다
				eventData['allDay'] = 1;
				eventData['start'] = new Date(`${startDate}T00:00:00`).toISOString();
				endDate = new Date(`${endDate}T00:00:00`);
				endDate.setDate(endDate.getDate() + 1);
				eventData['end'] = endDate.toISOString();
			} else {
				eventData['allDay'] = 0;
				eventData['start'] = new Date(`${startDate}T${startTime}:00`).toISOString();
				eventData['end'] = new Date(`${endDate}T${endTime}:00`).toISOString();
			}
			addEvent(eventData);
			$("#eventModal").modal("hide");
		});
		/*
		$(document).on("click", ".edit-event-button", function() {
			// 클릭한 버튼의 data-eventno 속성 값을 가져옴
			var eventNo = $(this).data("eventno"); 
			console.log("Clicked edit button with eventNo:", eventNo);
		    
			$.ajax({
				url: '/event/${eventNo}',
				type: 'GET',
				dataType: 'json',
				success: function(response) {
					console/log('이벤트 검색', response);
					
					var title = response.title;
					var description = response.description;
				},
				error: function(xhr, status, error) {
					console.error('이벤트 검색 에러', error);
				}
			});
	
		});	
		*/
		// 여기서 eventNo를 활용하여 해당 이벤트에 대한 정보를 가져와서 모달에 설정하는 등의 작업을 수행
		$("#modify-event").click(function() {

			var eventNo = $("#eventDetailModal input[name='no']").val();
			alert("일정번호: " + eventNo);

			var title = $("#eventDetailModal input[name='title']").val();
			var description = $("#eventDetailModal input[name='description']").val();
			var startDate = $("#eventDetailModal input[name='startDate']").val();
			var startTime = $("#eventDetailModal input[name='startTime']").val();
			var endDate = $("#eventDetailModal input[name='endDate']").val();
			var endTime = $("#eventDetailModal input[name='endTime']").val();

			let eventData = {
				no: $("#eventDetailModal input[name='no']").val(),
				title: $(":input[name=title]").val(),
				description: $(":input[name=description]").val(),
				startDate: $(":input[name=startDate]").val(),
				startTime: $(":input[name=startTime]").val(),
				endDate: $(":input[name=endDate]").val(),
				endTime: $(":input[name=endTime]").val()
			};

			let allDay = $(":checkbox[name=allDay]:checked").val();
			if (allDay) {	// 체크되었다면
				eventData['allDay'] = 1;
			} else {
				eventData['allDay'] = 0;
				eventData['startTime'] = $(":input[name=startTime]").val();
				eventData['endTime'] = $(":input[name=endTime]").val();
			}

			// 캘린더에서 이벤트 찾기
			var updateEvent = calendar.getEventById(eventNo);
			if (updateEvent) {
				// 이벤트 수정
				updateEvent.setExtendedProp('title', title);
				updateEvent.setExtendedProp('description', description);
				updateEvent.setStart(startDate + "T" + startTime);
				updateEvent.setEnd(endDate + "T" + endTime);
			}

			// 이벤트 업데이트 요청
			$.ajax({
				url: `/event/${eventNo}`,
				type: 'GET',
				data: eventData,
				dataType: 'json',
				success: function(response) {

					// 수정된 이벤트 정보로 캘린더에서 찾아 업데이트
					var updatedEvent = calendar.getEventById(eventNo);
					if (updatedEvent) {
						updatedEvent.setExtendedProp('title', title);
						updatedEvent.setExtendedProp('description', description);
						updatedEvent.setStart(startDate + "T" + startTime);
						updatedEvent.setEnd(endDate + "T" + endTime);
					}

					$("#eventDetailModal").modal("hide");
				},
				error: function(error) {
					console.error("Error updating event:", error);
				}
			});

		});

		function addEvent(event) {
			$.ajax({
				type: 'post',
				url: '/event/register',
				data: event,
				dataType: 'json'
			})
				.done(function(event) {
					console.log(event)
					calendar.addEvent(event);
				})
		}
	});

	// 날짜가 적힌 모달 함수 열기
	function openModalWithStartDate(StartDate, endDate) {
		$("#eventModal").modal("show");
		var formattedDate = moment(StartDate).format('YYYY-MM-DD');
		$("#startDate").val(formattedDate);
		var formattedDate = moment(endDate).format('YYYY-MM-DD');
		$("#endDate").val(formattedDate);
	}

	function openEventDetailModal(event) {
		$("#eventDetailModal").modal("show");
		$("#eventDetailModal input[name='no']").val(event.id);
		$("#eventDetailModal input[name='title']").val(event.title);
		$("#eventDetailModal input[name='description']").val(event.extendedProps.description);
		$("#eventDetailModal input[name='startDate']").val(event.startStr);
		$("#eventDetailModal input[name='startTime']").val(event.startStr);
		$("#eventDetailModal input[name='endDate']").val(event.endStr);
		$("#eventDetailModal input[name='endTime']").val(event.endStr);
	}
	/*	var calendar = new FullCalendar.Calendar(calendarEl, {
			initialView: 'dayGridMonth',
			locale: "ko"
		});
		calendar.render();*/
});