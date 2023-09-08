package com.letsparty.dto;

import java.util.List;

import com.letsparty.vo.Media;
import com.letsparty.vo.Place;
import com.letsparty.vo.Poll;
import com.letsparty.vo.PollOption;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostAttachment {
	
	private List<Media> imgList;
	private List<Media> videoList;
	private Place place;
	private Poll poll;
	private List<PollOption> pollOptions;
}
