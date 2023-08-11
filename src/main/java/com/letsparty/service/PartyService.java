package com.letsparty.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class PartyService {
	
	private final UserMapper userMapper;
	private final PartyMapper partyMapper;
	private final CategoryMapper categoryMapper;
	private final PartyReqMapper partyReqMapper;
	
	public void createParty(PartyCreateForm partyCreateForm, String leaderId) {
		String base64Prefix = "data:image/png;base64,";
		
		Party party = new Party();
		BeanUtils.copyProperties(partyCreateForm, party);
		
		// 파티 리더
		User leader = userMapper.getUserById(leaderId);
		party.setLeader(leader);
		
		// 카테고리 
		Category category = categoryMapper.getCategoryByNo(partyCreateForm.getCategoryNo());
		party.setCategory(category);
		
		// 헤더 저장
		// 1. 사용자가 직접 로컬에서 선택한 이미지를 편집하여 등록하는 경우에는 base64 데이터를
		// 	  디코드하여 파일시스템에 저장하고 파일 이름은 DB에 저장한다.
		if(partyCreateForm.getImageFile().startsWith(base64Prefix)) {
			UUID uuid = UUID.randomUUID();
			String filename = uuid + "editImage.png";
			
			String base64 = partyCreateForm.getImageFile();
			String base64String = base64.replaceAll(base64Prefix, "");
			
			byte[] decodeData = base64String.getBytes();
			
			Decoder decoder = Base64.getDecoder();
			
			
			byte[] decodeByte = decoder.decode(decodeData);
					
			FileOutputStream fos;
			try {
				File target = new File("C:\\Users\\jhta\\Desktop\\image\\" + "" + filename);
				target.createNewFile();
				fos = new FileOutputStream(target);
				fos.write(decodeByte);
				fos.close();
			} catch (IOException e) {
				throw new RuntimeException();
			}
			
		// 2. 반면 사용자가 서버에서 제공하는 기본 이미지를 선택했을 때는 저장된 이미지의 파일 이름이 등록.
		} else {
			party.setFilename(partyCreateForm.getImageFile());
		}
		
		// 파티 테이블에 파티 추가
		partyMapper.createParty(party);
		
		// 파티 - 게시물 시퀀스 추가
		partyMapper.createPartySequence(party.getNo());
		
		// 가입 조건테이블에 조건 추가
		// 최소나이 조건 추가
		PartyReq partyReq = new PartyReq();
		partyReq.setParty(party);
		partyReq.setName("생년1");
		partyReq.setValue(partyCreateForm.getBirthStart());
		partyReq.setDescription("가입할 수 있는 최소생년(예: 0000년 이후 출생자)");
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
