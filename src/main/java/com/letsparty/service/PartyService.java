package com.letsparty.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.letsparty.mapper.CategoryMapper;
import com.letsparty.mapper.PartyMapper;
import com.letsparty.mapper.PartyReqMapper;
import com.letsparty.mapper.UserMapper;
import com.letsparty.vo.Category;
import com.letsparty.vo.Party;
import com.letsparty.vo.PartyReq;
import com.letsparty.vo.User;
import com.letsparty.web.form.PartyCreateForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyService {
	
	private final UserMapper userMapper;
	private final PartyMapper partyMapper;
	private final CategoryMapper categoryMapper;
	private final PartyReqMapper partyReqMapper;
	
	public void createParty(PartyCreateForm partyCreateForm, String leaderId) {	
		
		Party party = new Party();
		BeanUtils.copyProperties(partyCreateForm, party);
		
		// 파티 리더
		User leader = userMapper.getUserById(leaderId);
		party.setLeader(leader);
		
		// 카테고리 
		Category category = categoryMapper.getCategoryByNo(partyCreateForm.getCategoryNo());
		party.setCategory(category);
		
		if (partyCreateForm.getDefaultImagePath() != null) {
			party.setFilename(partyCreateForm.getDefaultImagePath());
		}
		if (partyCreateForm.getSavedName() != null) {
			party.setFilename(partyCreateForm.getSavedName());
		}
		
		log.info("S3 저장 파일 이름 ====> {}", partyCreateForm.getSavedName());
		log.info("제목 ====> {}", partyCreateForm.getName());
		log.info("내용 ====> {}", partyCreateForm.getDescription());
		log.info("리더 ====> {}", leaderId);
		
		// 파티 테이블에 파티 추가
		partyMapper.createParty(party);
		
		// 파티 - 게시물 시퀀스 추가
		partyMapper.createPartySequence(party.getNo());
		
		// 가입 조건테이블에 조건 추가
		PartyReq partyReq = new PartyReq();
		// 최소나이 조건 추가
		partyReq.setParty(party);
		partyReq.setName("생년1");
		partyReq.setValue(partyCreateForm.getBirthStart());
		partyReq.setDescription("가입할 수 있는 최대생년 (예: 0000년 이후 출생자)");
		partyReqMapper.insertPartyReq(partyReq);
		
		// 최대나이 조건 추가
		partyReq.setParty(party);
		partyReq.setName("생년2");
		partyReq.setValue(partyCreateForm.getBirthEnd());
		partyReq.setDescription("가입할 수 있는 최대생년 (예: 0000년 이전 출생자)");
		partyReqMapper.insertPartyReq(partyReq);
		
		// 성별 조건 추가
		partyReq.setParty(party);
		partyReq.setName("성별");
		partyReq.setValue(partyCreateForm.getGender());
		partyReq.setDescription("가입할 수 있는 성별");
		partyReqMapper.insertPartyReq(partyReq);
		
	}
	
}
