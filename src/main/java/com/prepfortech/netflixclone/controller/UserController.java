package com.prepfortech.netflixclone.controller;

import com.prepfortech.netflixclone.controller.model.CreateUserInput;
import com.prepfortech.netflixclone.exceptions.InvalidDataException;
import com.prepfortech.netflixclone.security.Roles;
import com.prepfortech.netflixclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/user")
    public ResponseEntity<String> addNewUser(@RequestBody CreateUserInput createUserInput){
        String name = createUserInput.getName();
        String email = createUserInput.getEmail();
        String password = createUserInput.getPassword();
        String phoneNo = createUserInput.getPhoneNo();


        try {
            userService.addNewUser(email, name, password, phoneNo);
            return ResponseEntity.status(HttpStatus.OK).body("User Created Successfully!");
        }catch (InvalidDataException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @PostMapping("/user/subscription")
    @Secured({Roles.User})
    public String activateSubscription(){
        userService.activateSubscription();
        return "Subscription Activated Successfully!! Enjoy.";
        //this will now change the userRole from USER to CUSTOMER
    }


    @DeleteMapping("/user/subscription")
    @Secured({Roles.Customer})
    public String deleteSubscription(){
        userService.deleteSubscription();
        return "Subscription De-Activated Successfully!! Hope You Join Again Soon.";
        //this will now change the userRole from CUSTOMER to USER
    }


}
