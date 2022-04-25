package com.universal.music.assignment.services;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.universal.music.assignment.dto.TrackResponseDTO;
import com.universal.music.assignment.model.Track;
import com.universal.music.assignment.repository.TrackRepository;
import com.universal.music.assignment.util.AssignmentConstants;
import com.universal.music.assignment.vo.ItemVO;
import com.universal.music.assignment.vo.TrackVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TrackService implements TrackServiceI {

	@Autowired
	TrackRepository trackRepo;
	
	@Autowired
	AssignmentConstants constants;
	
	//Ideally this method should be in some other class which caches it. However as per guidelines we are not caching it. Hence for simplicity keeping the method here
	//This method will throw Exceptions in case the credentials are Invalid. Its handled in the calling procedure. In real application, this should be handled differently.
	private String getToken() {
		
		String authKey = constants.CLIENT_ID +":"+constants.CLIENT_SECRET;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(Base64.getEncoder().encodeToString(authKey.getBytes()));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type","client_credentials");
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		
		//Mapping JSON on Map as its simple key value pair and we are interested in only one of the entries
		//It would be more typesafe to create a custom class and map the JSON on it. However, for simple JSONs Map works fine 
		ResponseEntity<Map> res = new RestTemplate().exchange(AssignmentConstants.URL_FOR_TOKEN, HttpMethod.POST, entity, Map.class, List.of());				
		String token = (String)res.getBody().get("access_token");
		
		log.info("Token="+token);		
		return token;
	}	
	
	@Override
	public String createTask(String isrc) {		
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(getToken());
			HttpEntity<String> entity = new HttpEntity<>("", headers);
		
			final String url = AssignmentConstants.URL_FOR_TRACK_SEARCH;
			ResponseEntity<TrackVO> res = new RestTemplate().exchange(String.format(url,isrc), HttpMethod.GET, entity, TrackVO.class, List.of() );		
			log.info("Output from Spotify Search API="+res.getBody());
			
			List<ItemVO> items = res.getBody().getTracks().getItems();
			
			if(items.size() == 0) 
				return "Could not find Track in Spotify";
			
			ItemVO item = items.get(0);	
			Track track = Track.builder().isrc(isrc)
										 .name(item.getName())
										 .duration(item.getDuration_ms())
										 .explicit(item.isExplicit()?1:0)
										 .build();
			trackRepo.save(track);
			
			return "Success";
		} catch (Exception e) {
			log.error("Error while creating track", e);
			return "Failure. Contact technical team for more details.";
		}
	}

	@Override
	public TrackResponseDTO getTask(String isrc) {
		
		Optional<Track> track = trackRepo.findById(isrc);
		if(track.isPresent())
			return new TrackResponseDTO(track.get());
		else
			return new TrackResponseDTO();
	}

}
