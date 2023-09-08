package com.letsparty.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letsparty.dto.PostAttachment;
import com.letsparty.mapper.MediaMapper;
import com.letsparty.mapper.PlaceMapper;
import com.letsparty.mapper.PollMapper;
import com.letsparty.vo.Media;
import com.letsparty.vo.Place;
import com.letsparty.vo.Poll;
import com.letsparty.vo.PollOption;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaService {
	
	private final PlaceMapper placeMapper;
	private final PollMapper pollMapper;
	private final MediaMapper mediaMapper;
	@Value("${s3.path.media}")
	private String mediaPath;

	public PostAttachment getMediaByPostId(long postId) {
		PostAttachment pa = new PostAttachment();
		Place place = placeMapper.getPlaceByPostId(postId);
		List<Media> mediaList = mediaMapper.getMediaByPostId(postId);
		List<Media> imgList = new ArrayList<>();
		List<Media> videoList = new ArrayList<>();
		if (mediaList.size() >= 1) {
			for (Media media : mediaList) {
				media.setName(mediaPath + media.getName());
			}
			for (Media media : mediaList) {
				if (media.getContentType().equals("image")) {
					imgList.add(media);
				} else {
					videoList.add(media);
				}
			}
		}
		pa.setImgList(imgList);
		pa.setVideoList(videoList);
		Poll poll = pollMapper.getPollByPostId(postId);
		if (null != poll) {
			List<PollOption> pollOptions = pollMapper.getPollOptionsByPollNo(poll.getNo());
			pa.setPollOptions(pollOptions);
		}
		pa.setPlace(place);
		pa.setPoll(poll);
		
		return pa;
	}

}
