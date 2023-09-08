package com.letsparty.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.letsparty.vo.Post;

@Mapper
public interface PostMapper {

	void insertPost(Post post);

}
