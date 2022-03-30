package com.example.codefellowship.infrastructure;

import com.example.codefellowship.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepo extends JpaRepository<Song, Long> {

}
