package com.letsparty.web.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.letsparty.security.user.LoginUser;
import com.letsparty.service.UserService;
import com.letsparty.service.partyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.letsparty.web.form.PartyCreateForm;
import com.letsparty.web.form.SignupForm;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
	
	private final UserService userService;
	private final partyService partyService;

	@GetMapping("/")
	public String home() {
		return "page/main/home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "page/main/login";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "page/main/signup";
	}

	@PostMapping("/signup")
	public String signup(SignupForm signupForm) {
		userService.signupUser(signupForm);
		return "redirect:/";
	}
    
	// 파티생성폼으로 이동
	@GetMapping("/party-create")
	public String partyCreate(Model model) {
		
		// 가입 조건 중 나이 반복문을 위한 로직
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		List<Integer> birthYears = new ArrayList<>();
		for (int i = year - 13; i > year - 100; i--) {
		    birthYears.add(i);
		}
		model.addAttribute("birthYears", birthYears);
		model.addAttribute("currentYear", year);

		model.addAttribute("partyCreateFrom", new PartyCreateForm());
		
		return "page/main/party-create";
	}
	
	// 파티생성을 수행
	@PostMapping("/party-create")
	public String partyCreate(@AuthenticationPrincipal LoginUser user, PartyCreateForm partyCreateForm) throws IllegalStateException, IOException {
		String leaderId = user.getId();
		partyService.createParty(partyCreateForm, leaderId);
		return "redirect:/";
	}
}
