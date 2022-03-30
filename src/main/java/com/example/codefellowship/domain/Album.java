
package com.example.codefellowship.domain;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Setter
@Getter
@Entity
public class Album {
    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Album() {
    }

    public Album(String title, String artist, int songCount, int secondsLength, String imageUrl, Set<Song> songs) {
        this.title = title;
        this.artist = artist;
        this.songCount = songCount;
        this.secondsLength = secondsLength;
        this.imageUrl = imageUrl;
        this.songs = songs;
    }

    private String title;

    private String artist;

    private int songCount;

    private int secondsLength ;

    private String imageUrl ;

    @OneToMany(mappedBy = "album")
    private Set<Song> songs;
}