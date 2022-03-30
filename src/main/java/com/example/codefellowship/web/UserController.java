package com.example.codefellowship.web;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.infrastructure.ApplicationUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {
    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Autowired
    PasswordEncoder encoder;

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
}
