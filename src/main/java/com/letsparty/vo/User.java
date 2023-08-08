package com.letsparty.vo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private Date birthday;
	private char gender;
	private String email;
	private String tel;
	private boolean disabled;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
	private String providerType;
	private String password;
	
	// 유저의 보유 권한을 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("USER_ROLE"));
	}
	
	// 유저의 고유 식별정보 반환
	@Override
	public String getUsername() {
		return id;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	// 유저의 계정 만료여부를 반환
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// 유저의 계정 잠김여부를 반환
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	// 유저의 비밀번호 만료여부를 반환
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 유저의 활성화 여부를 반환
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
