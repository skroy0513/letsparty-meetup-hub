package com.letsparty.service;

import org.springframework.stereotype.Service;

import com.letsparty.mapper.PostMapper;
import com.letsparty.vo.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	
	private final PostMapper postMapper;

	public int getLastPostNoByPartyNo(int partyNo) {
		return postMapper.getLastPostNoByPartyNo(partyNo);
	}

	public void readIncrement(int partyNo, int postNo) {
		Post post = postMapper.getPostByPostNoAndPartyNo(partyNo, postNo);
		postMapper.readIncrement(postNo);
	}
	
}
