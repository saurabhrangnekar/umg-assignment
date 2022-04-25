package com.universal.music.assignment.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name="track_master")
@NoArgsConstructor
@AllArgsConstructor
public class Track {
	
	@Id
	private String isrc;
	private String name;
	private int duration;
	private int explicit;
}
