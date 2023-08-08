package com.letsparty.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.letsparty.vo.User;

@Mapper
public interface UserMapper {

	void createUser(User user);
}
