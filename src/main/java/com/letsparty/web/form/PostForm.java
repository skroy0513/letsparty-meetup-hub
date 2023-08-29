package com.letsparty.web.form;

import java.util.List;

import com.letsparty.vo.Place;
import com.letsparty.vo.Post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostForm {
	
	private Post post;
	private List<String> imageName;
	private List<String> videoName;
	
//	지도 vo 
	private Place place;

}
