package com.letsparty.service;

import org.springframework.stereotype.Service;

import com.letsparty.mapper.UserMapper;
import com.letsparty.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	
	/*
	 * 회원가입을 위한 유저 정보 저장
	 */
	public void registerUser(User user) {
		
		userMapper.createUser(user);
	}
}
