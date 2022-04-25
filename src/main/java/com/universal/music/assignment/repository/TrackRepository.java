package com.universal.music.assignment.repository;

import org.springframework.data.repository.CrudRepository;

import com.universal.music.assignment.model.Track;

public interface TrackRepository extends CrudRepository<Track, String> {

}
