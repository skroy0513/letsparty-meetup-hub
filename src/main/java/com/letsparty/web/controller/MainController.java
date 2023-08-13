package com.letsparty.web.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


import com.letsparty.security.user.LoginUser;
import com.letsparty.exception.DuplicateEmailException;
import com.letsparty.exception.DuplicateUserIdException;

import com.letsparty.service.UserService;
import com.letsparty.vo.Category;
import com.letsparty.service.CategoryService;
import com.letsparty.service.PartyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.letsparty.web.form.PartyCreateForm;
import com.letsparty.web.form.SignupForm;

@Controller
@RequiredArgsConstructor 
@SessionAttributes("signupForm")
@Slf4j
public class MainController {
	
	private final UserService userService;
	private final PartyService partyService;
	private final CategoryService categoryService;

	@GetMapping("/")
	public String home() {
		return "page/main/home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "page/main/login";
	}
	
//		첫번째 회원가입 화면 진입
	@GetMapping("/signup")
	public String signup(Model model) {
		SignupForm signupForm = new SignupForm();
		model.addAttribute("signupForm", signupForm);

		log.info("첫번째 단계 비밀번호 -> {}", signupForm.getPassword());
		
		return "page/main/signup/step1";
	}
	
//		첫번째 회원가입에서 값 입력하고 POST 전송
	@PostMapping("/signup/step1")
	public String signup1(@ModelAttribute("signupForm") SignupForm signupForm, BindingResult errors) {
		
		boolean checkDuplicate = false;
		// id 유효값 검사
		if (!StringUtils.hasText(signupForm.getId())) {
			errors.rejectValue("id", null, "아이디는 필수입력값입니다.");
			checkDuplicate = true;
		} else {
			try {
				userService.checkDuplicateUserId(signupForm.getId());
			} catch (DuplicateUserIdException ex) {
				errors.rejectValue("id", null, "사용할 수 없는 아이디입니다.");
				checkDuplicate = true;
			}
		}
		
		// 비밀번호 유효값 검사
		if (!StringUtils.hasText(signupForm.getPassword())) {
			errors.rejectValue("password", null, "비밀번호는 필수입력값입니다.");
			checkDuplicate = true;
		}
		
		// 이름 유효값 검사
		if (!StringUtils.hasText(signupForm.getName())) {
			errors.rejectValue("name", null, "이름은 필수입력값입니다.");
			checkDuplicate = true;
		}
		
		// 이메일 유효값 검사
		if (!StringUtils.hasText(signupForm.getEmail())) {
			errors.rejectValue("email", null, "이메일은 필수입력값입니다.");
			checkDuplicate = true;
		} else {
			try {
				userService.checkDuplicateEmail(signupForm.getEmail());
			} catch (DuplicateEmailException ex) {
				errors.rejectValue("email", null, "사용할 수 없는 이메일입니다.");
				checkDuplicate = true;
			}
		}
		
		if (checkDuplicate) {
			return "page/main/signup/step1";
		}
		
		log.info("두번째 단계 비밀번호 -> {}", signupForm.getPassword());
		
		return "page/main/signup/step2";
	}

//		두번째 회원가입에서 값 입력하고 POST 전송
	@PostMapping("/signup/step2")
	public String signup2(@ModelAttribute("signupForm") SignupForm signupForm, BindingResult errors, SessionStatus sessionStatus) {
		
		log.info("세번째 단계 비밀번호 -> {}", signupForm.getPassword());

		boolean checkDuplicate = false;

		// 전화번호 유효값 검사
		if (!StringUtils.hasText(signupForm.getTel())) {
			errors.rejectValue("tel", null, "전화번호는 필수입력값입니다.");
			checkDuplicate = true;
		}
		
		// 생년월일 유효값 검사
		if (signupForm.getBirthday() == null) {
			errors.rejectValue("birthday", null, "생년월일은 필수입력값입니다.");
			checkDuplicate = true;
		}
		
		// 성별 유효값 검사
		if (!StringUtils.hasText(signupForm.getGender())) {
			errors.rejectValue("gender", null, "성별은 필수입력값입니다.");
			checkDuplicate = true;
		}
		
		if (checkDuplicate ) {
			return "page/main/signup/step2";
		}

		try {
			userService.signupUser(signupForm);
		} catch (DuplicateUserIdException ex) {
			errors.rejectValue("id", null, "사용할 수 없는 아이디입니다.");
			return "page/main/signup/step1";
		} catch (DuplicateEmailException ex) {
			errors.rejectValue("email", null, "사용할 수 없는 이메일입니다.");
			return "page/main/signup/step1";
		}
		
		sessionStatus.setComplete();
		
		return "redirect:/signup/complete";
	}
	
	@GetMapping("/signup/complete")
	public String signupComplete() {
		
		return "page/main/signup/complete";
	}
    
	// 파티생성폼으로 이동
	@GetMapping("/party-create")
	public String partyCreate(@RequestParam(value="catNo", required=false) Integer catNo, Model model) {
		// catNo파라미터가 없거나 0보다 작을 경우 루트 페이지로 리다이렉트
		if (catNo == null || catNo < 0) {
	        return "redirect:/";
	    }
		
		// 가입 조건 나이 선택 목록 반복문
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		List<Integer> birthYears = new ArrayList<>();
		for (int i = year - 13; i > year - 100; i--) {
		    birthYears.add(i);
		}
		model.addAttribute("birthYears", birthYears);
		model.addAttribute("currentYear", year);
		
		// 카테고리 목록 반복문
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		
		// 클릭한 카테고리가 셀렉트 되어있도록 함
		PartyCreateForm partyCreateForm = new PartyCreateForm();
		partyCreateForm.setCategoryNo(catNo);
		
		model.addAttribute("partyCreateForm", partyCreateForm);
		
		return "page/main/party-create";
	}
	
	// 파티생성을 수행
	@PostMapping("/party-create")
	public String partyCreate(@AuthenticationPrincipal LoginUser user, PartyCreateForm partyCreateForm) {
		String leaderId = user.getId();
		partyService.createParty(partyCreateForm, leaderId);
		return "redirect:/party/1234";
	}
}
