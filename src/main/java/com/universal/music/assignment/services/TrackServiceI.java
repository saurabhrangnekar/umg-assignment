package com.universal.music.assignment.services;

import com.universal.music.assignment.dto.TrackResponseDTO;

public interface TrackServiceI {

	String createTask(String isrc);

	TrackResponseDTO getTask(String isrc);

}
