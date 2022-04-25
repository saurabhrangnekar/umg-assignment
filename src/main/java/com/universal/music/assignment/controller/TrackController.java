package com.universal.music.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universal.music.assignment.dto.TrackResponseDTO;
import com.universal.music.assignment.services.TrackServiceI;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/codechallenge")
public class TrackController {

	@Autowired
	TrackServiceI trackService;
	
	//Response for this API is not defined in requirements. Keeping String for simplicity. 
	//In real application it should be a JSON with status, error code and error message
	@PostMapping("/createTrack") 
	public String createTrack(@RequestParam(required = true) String isrc) {		
		log.info("Creating Track for ISRC={}", isrc);
		return trackService.createTask(isrc);
	}
	
	//Adding status field in the response to indicate whether track was found or not
	//Alternatively, API can return HTTP 404 when this happens.
	@GetMapping("/getTrack") 
	public TrackResponseDTO getTrack(@RequestParam(required = true) String isrc) {
		log.info("Retriving Track for ISRC={}", isrc);
		return trackService.getTask(isrc);
	}	
	
}
