package com.example.codefellowship.web;


import com.example.codefellowship.domain.Album;
import com.example.codefellowship.domain.Song;
import com.example.codefellowship.infrastructure.AlbumRepo;
import com.example.codefellowship.infrastructure.SongRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@Controller
public class HelloController {

    public HelloController(AlbumRepo albumRepo, SongRepo songRepo) {
        this.albumRepo = albumRepo;
        this.songRepo = songRepo;
    }

    @GetMapping("/hello")
    String hello(){
        return "hello" ;
    }

    @GetMapping("/capitalize/{name}")
    String capitalize(@PathVariable(name = "name" , required = false) String name , Model model){
        name = name.toUpperCase();
        model.addAttribute("name" , name);
        return "capitalize" ;
    }

//    @ResponseBody
//   @GetMapping("/albums")
//    ArrayList<Album> albums(){
//       ArrayList<Album> albumArrayList = new ArrayList<>();
//       albumArrayList.add(new Album("Hamzh albom" , "Hamzh" , 5 , 50 , "/hamzh.png"));
//       albumArrayList.add(new Album("Ahmad albom" , "Ahmad" , 3 , 120 , "/Ahmad.png"));
//       albumArrayList.add(new Album("Mohammed albom" , "Mohammed" , 5 , 130 , "/Mohammed.png"));
//        return albumArrayList;
//   }


    @GetMapping("/get-albums")
    String getAlbums(Model model){
        model.addAttribute("albumList" , albumRepo.findAll());
        return "album";
    }

   @Autowired
    private final AlbumRepo albumRepo;
    private final SongRepo songRepo ;



    @ResponseBody
    @PostMapping("/addAlbum")
    public Album createNewAlbum(@RequestBody Album album){
       return albumRepo.save(album);
    }

    @ResponseBody
    @PostMapping("/albums/{id}")
    public Song createNewSong(@RequestBody Song song , @PathVariable Long id){
        Album album2  = albumRepo.findById(id).orElseThrow();
        song.setAlbum(album2);
        return songRepo.save(song);
    }
    @ResponseBody
    @GetMapping("/albums")
    List<Album> getAlbums(){
        return albumRepo.findAll();
    }


    @ResponseBody
    @GetMapping("/get-album/{id}")
    Album getAlbum(@PathVariable Long id , Model model){
         Album album = albumRepo.findById(id).get();
        return album;
    }


    @GetMapping("/getSongs")
    String getSongs(Model model){
        model.addAttribute("songList" , songRepo.findAll());
        return "song";
    }

    @PostMapping("/addSong")
    RedirectView createNewSong(@ModelAttribute Song song){
        songRepo.save(song);
        return new RedirectView("/get-songs");
    }

}

// @ModelAttribute