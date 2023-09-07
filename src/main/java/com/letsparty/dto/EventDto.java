package com.letsparty.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EventDto {

	private int startDay;
	private int startMonth;
	private String startTime;
	private String title;
	private String description;
}
