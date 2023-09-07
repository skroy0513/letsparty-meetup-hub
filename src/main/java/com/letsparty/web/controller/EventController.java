package com.letsparty.web.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.letsparty.service.EventService;
import com.letsparty.vo.Event;
import com.letsparty.vo.User;
import com.letsparty.web.form.RegisterEventForm;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/event")
@Slf4j
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	
	
	//@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	@ResponseBody
	public Event registerEvent(User user, RegisterEventForm registerEventform) {
		user = new User();
		user.setId("hong");
		Event event = eventService.insertEvent(user, registerEventform);	
		log.info("일정 정보 -> {}", registerEventform);
		
		return event;
	}
	
	@GetMapping("/{eventNo}")
	public ResponseEntity<Event> getEventByNo(@PathVariable int eventNo) {
		// evetnNo를 사용하여 이벤트 조회
		Event event = eventService.getEventByNo(eventNo);
		
		if (event != null) {
			log.info("event: {}", event);
			return ResponseEntity.ok(event);
		} else {
			log.error("event 오류");
			return ResponseEntity.noContent().build();
		}
	}
	/*
	@PostMapping("/update/{eventNo}")
	public ResponseEntity<String> updateEvent(@PathVariable int eventNo, @RequestBody RegisterEventForm updateEventForm) {
		
		return ResponseEntity.ok("success");
	}
	*/
	@GetMapping("/events")
	public List<Event> getEvents(@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
		return eventService.getEvents(startDate, endDate);
	}
	
	
}
