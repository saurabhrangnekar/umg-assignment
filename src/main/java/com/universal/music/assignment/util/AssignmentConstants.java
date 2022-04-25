package com.universal.music.assignment.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AssignmentConstants {
	
	@Value("${demo.client.id}")
	public String CLIENT_ID;
	
	@Value("${demo.client.secret}")
	public String CLIENT_SECRET;
	
	public static final String URL_FOR_TOKEN = "https://accounts.spotify.com/api/token";
	public static final String URL_FOR_TRACK_SEARCH = "https://api.spotify.com/v1/search?q=isrc:%s&type=track";
}
