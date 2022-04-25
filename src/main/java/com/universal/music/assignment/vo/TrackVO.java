package com.universal.music.assignment.vo;

import java.util.List;

import lombok.Data;

@Data
public class TrackVO {	
	
	InnerTrack tracks;
	
	@Data	
	public class InnerTrack {
		private List<ItemVO> items;
	}
}
