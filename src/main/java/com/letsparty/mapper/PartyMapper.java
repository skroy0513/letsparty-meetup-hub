package com.letsparty.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.letsparty.vo.Party;

@Mapper
public interface PartyMapper {
	
	// 새파티 생성
	void createParty(Party party);
	
}
