package com.universal.music.assignment.dto;

import org.springframework.beans.BeanUtils;

import com.universal.music.assignment.model.Track;

import lombok.Data;

@Data
public class TrackResponseDTO {
	
	private String status; 
	private TrackResponse track;

	public TrackResponseDTO() { 
		this.status = "Failure";
		this.track = null;
	}

	
	public TrackResponseDTO(Track track) { 
		this.status = "Success";
		this.track = new TrackResponse(track);
	}
	
	@Data
	class TrackResponse {
		
		//Keeping the fields names same as Model. This allows us to copy them with one generic method call.
		//However, this might expose the column names to API consumer. 
		//This risk can be mitigated by using different naming convention for columns and mapping those on model class properties explicitly  
		private String isrc;
		private String name;
		private int duration;
		private boolean explicit;
		
		public TrackResponse(Track track) {
			BeanUtils.copyProperties(track, this);
			this.explicit=track.getExplicit()==1;
		}
	}
}
