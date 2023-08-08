package com.letsparty.service;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.letsparty.mapper.UserMapper;
import com.letsparty.vo.User;
import com.letsparty.web.form.AddUserForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	/*
	 * 회원가입을 위한 유저 정보 저장
	 */
	public void registerUser(AddUserForm userform) {
		User user = new User();
		BeanUtils.copyProperties(userform, user);
		String encryptedPassword = passwordEncoder.encode(userform.getPassword());
		user.setPassword(encryptedPassword);
		
		userMapper.createUser(user);
	}
}
