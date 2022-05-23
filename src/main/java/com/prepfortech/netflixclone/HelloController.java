package com.prepfortech.netflixclone;


import com.prepfortech.netflixclone.security.Roles;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Secured({Roles.Customer, Roles.User})
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello world in Netflix Clone!";
    }

}
