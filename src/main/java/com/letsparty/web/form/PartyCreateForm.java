package com.letsparty.web.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class PartyCreateForm {
	
	@Min(value = 10, message = "카테고리를 선택해주세요.")
	private int categoryNo;
	
	@NotBlank(message = "파티이름은 필수 입력값입니다.")
	private String name;
	
	private int quota;
	private String birthStart;
	private String birthEnd;
	private String gender;
	private String description;
	@Nullable
	private MultipartFile imageFile;
	private String defaultImagePath;
	
}
