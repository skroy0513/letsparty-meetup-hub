package com.letsparty.web.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.letsparty.vo.Category;
import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class PartyCreateForm {
	
	private int categoryNo;
	
	@NotBlank(message = "파티의 이름은 필수 입력값입니다.")
	@Size(min = 3, message = "파티의 이름은 세 글자 이상이어야 합니다.")
	private String name;
	
	private int quota;
	private String birthStart;
	private String birthEnd;
	private String gender;
	private String description;
	private String imageFile;
	
}
