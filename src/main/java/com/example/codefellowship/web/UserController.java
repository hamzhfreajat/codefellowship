package com.example.codefellowship.web;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.domain.Posts;
import com.example.codefellowship.infrastructure.ApplicationUserRepo;
import com.example.codefellowship.infrastructure.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Autowired
    PostRepo postRepo;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    @GetMapping("/")
    String getIndex(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            ApplicationUser currentUser = applicationUserRepo.findByUsername(userDetails.getUsername());
            model.addAttribute("username" , userDetails.getUsername());
        }
        return "index";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }


    @GetMapping("/signup")
    public String getSignup(){
        return "signup";
    }


    @PostMapping("/signup")
    public RedirectView addUser(@ModelAttribute ApplicationUser applicationUser){
        applicationUser.setPassword(encoder.encode(applicationUser.getPassword()));
        applicationUserRepo.save(applicationUser);
        return new RedirectView("/login");
    }

    @GetMapping("/users")
    String getAllUsers(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username" , userDetails.getUsername());
        model.addAttribute("users", applicationUserRepo.findAll());
        return "all-users";
    }


    @GetMapping("/users/{id}")
    String getUser(@PathVariable(name = "id" , required = false) Long id  , Model model){
        ApplicationUser applicationUser =  applicationUserRepo.findById(id).orElseThrow();

        model.addAttribute("username" , applicationUser.getUsername());
        model.addAttribute("bio" , applicationUser.getBio());
        model.addAttribute("postsList" , applicationUser.getPosts());
        return "single-user";
    }


    @GetMapping("/myprofile")
    String getProfile(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username" , userDetails.getUsername());
        model.addAttribute("bio" , applicationUserRepo.findByUsername(userDetails.getUsername()).getBio());
        model.addAttribute("postsList" , applicationUserRepo.findByUsername(userDetails.getUsername()).getPosts());
        return "/myprofile";
    }

    @PostMapping("/post")
    public RedirectView createPost(@ModelAttribute Posts posts){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        posts.setUser(applicationUserRepo.findByUsername(userDetails.getUsername()));
        postRepo.save(posts);
        return new RedirectView("/myprofile");
    }

    @Transactional
    @GetMapping("/follow/{id}")
    RedirectView showFollowSuccessScreen(@PathVariable("id") long id, Model model) {

        // user to follow
        ApplicationUser usertofollow = applicationUserRepo.findById(id).orElseThrow();

        // get current logged in user username
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // use the user name or ID to find the user by username or ID
        ApplicationUser currentUser = applicationUserRepo.findByUsername(userDetails.getUsername());
        // once you have that object

        // add the curetn logged in user to the following of usertofollow
        currentUser.getFollowers().add(usertofollow);

        // add usertofollow to current logged in user followers
        usertofollow.getFollowing().add(currentUser);

        // update both the userToFollow and the current logged in User to the DB by calling save

        applicationUserRepo.save(usertofollow);
        applicationUserRepo.save(currentUser);

        return new RedirectView("/");
    }


    @GetMapping("/error")
    public String handleError() {
        //do something like logging
        return "error";
    }
}
