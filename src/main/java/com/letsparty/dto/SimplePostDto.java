package com.letsparty.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SimplePostDto {

	private int partyNo;
	private int postNo;
	private String title;
	private String nickname;
	private String content;
	private int readCnt;
	private int commentCnt;
}
