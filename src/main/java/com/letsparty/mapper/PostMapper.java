package com.letsparty.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.letsparty.vo.Post;

@Mapper
public interface PostMapper {

	void insertPost(Post post);

	int getLastPostNoByPartyNo(int partyNo);

	void readIncrement(int postNo);

	Post getPostByPostNoAndPartyNo(int partyNo, int postNo);
}
