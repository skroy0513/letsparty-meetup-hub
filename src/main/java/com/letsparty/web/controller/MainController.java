package com.letsparty.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.letsparty.service.UserService;
import com.letsparty.web.form.AddUserForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
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
		userService.registerUser(userform);
		return "redirect:/";
	}
}
