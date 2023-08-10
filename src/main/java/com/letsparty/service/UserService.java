package com.letsparty.service;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.letsparty.mapper.UserMapper;
import com.letsparty.vo.User;
import com.letsparty.web.form.SignupForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	/*
	 * 유저 정보 저장(회원가입)
	 */
	public void signupUser(SignupForm sigunupForm) {
		User user = new User();
		BeanUtils.copyProperties(sigunupForm, user);
		String encryptedPassword = passwordEncoder.encode(sigunupForm.getPassword());
		user.setPassword(encryptedPassword);
		
		userMapper.createUser(user);
	}

	/*
	 * 로그인
	 */
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		System.out.println(id);
		User user = userMapper.getUserById(id);
		if (user == null) {
			throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
		}
		
		return user;
	}
	
}
