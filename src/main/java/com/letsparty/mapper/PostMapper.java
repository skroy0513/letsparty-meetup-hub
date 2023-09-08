package com.letsparty.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.letsparty.vo.Post;

@Mapper
public interface PostMapper {

	void insertPost(Post post);

	int getLastPostNoByPartyNo(int partyNo);

	Post getPostByPostNoAndPartyNo(@Param("partyNo") int partyNo,@Param("postNo") int postNo);

	void readIncrement(Post post);
}
