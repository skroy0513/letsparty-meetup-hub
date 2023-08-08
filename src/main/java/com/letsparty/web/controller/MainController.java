package com.letsparty.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.letsparty.service.UserService;
import com.letsparty.web.form.AddUserForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
	
	private final UserService userService;

	@GetMapping("/")
	public String home() {
		return "page/main/home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "page/main/login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "page/main/register";
	}
	
	@PostMapping("/register")
	public String register(AddUserForm userform) {
		log.info("AddUserForm -> {}, {}, {}, {}, {}, {}",
				userform.getId(), userform.getPassword(), userform.getName(), userform.getEmail(), userform.getTel(), userform.getGender());
		userService.registerUser(userform);
		return "redirect:/";
	}
}
