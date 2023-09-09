package com.letsparty.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.letsparty.dto.BeginEndPostNo;
import com.letsparty.dto.SimplePostDto;
import com.letsparty.vo.Post;

@Mapper
public interface PostMapper {

	void insertPost(Post post);

	Integer getLastPostNoByPartyNo(int partyNo);

	Post getPostByPostNoAndPartyNo(@Param("partyNo") int partyNo,@Param("postNo") int postNo);

	void updatePost(Post post);

	int[] getPostNoWithCurPostNoLimit5(@Param("partyNo") int partyNo,@Param("postNo") int postNo);

	SimplePostDto getSimplePostByPostNoAndPartyNo(@Param("partyNo") int partyNo,@Param("postNo") int postNo);

	BeginEndPostNo getBeginAndEndPostNo(int partyNo);

	BeginEndPostNo getThirdBeginAndEndPostno(@Param("partyNo") int partyNo,@Param("postNo") int postNo);
}
