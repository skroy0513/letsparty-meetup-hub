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
	
	private int categoryNo;
	
	@NotBlank(message = "파티 이름은 필수 입력 값입니다.")
	private String name;
	
	@Min(value = 10, message = "최소 정원은 10명 이상입니다.")
	private int quota;
	
	private String birthStart;
	private String birthEnd;
	private String gender;
	private String description;
	private MultipartFile imageFile;
	private String defaultImagePath;
	private String savedName;
	
}
