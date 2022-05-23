package com.prepfortech.netflixclone.service;

import com.prepfortech.netflixclone.accessor.UserAccessor;
import com.prepfortech.netflixclone.accessor.model.UserDTO;
import com.prepfortech.netflixclone.accessor.model.UserRole;
import com.prepfortech.netflixclone.accessor.model.UserState;
import com.prepfortech.netflixclone.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.regex.Pattern;

@Component
public class UserService {

    @Autowired
    private UserAccessor userAccessor;


    public void addNewUser(final String email,final String name, final String password,final String phoneNo ){
        if(phoneNo.length() != 10){
            throw new InvalidDataException("Phone No " + phoneNo + " is Invalid");

        }
        if(password.length() <= 5){
            throw new InvalidDataException("Password is Too Small");
        }
        if(name.length() < 5){
            throw new InvalidDataException("Name Input is not correct! ");

        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        if(!pat.matcher(email).matches()){
            throw new InvalidDataException("Email is not correct!");
        }
     //TO CHECK WEATHER EMAIL ALREADY EXISTS
        UserDTO userDTO = userAccessor.getUserByEmail(email);
        if(userDTO != null){
            throw new InvalidDataException("User with the given email already exists!");

        }//TO CHECK WEATHER PHONE NO ALREADY EXISTS
        userDTO = userAccessor.getUserByPhoneNo(phoneNo);

            if(userDTO != null){
                throw new InvalidDataException("User with given Phone Number already exists!");
        }
        userAccessor.addNewUser(email,name,password,phoneNo, UserState.ACTIVE, UserRole.ROLE_USER);
    }


    public void activateSubscription(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();//principal is the userDTO
        userAccessor.updateUserRole(userDTO.getUserId(),UserRole.ROLE_CUSTOMER);
    }


    public void deleteSubscription(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();//principal is the userDTO
        userAccessor.updateUserRole(userDTO.getUserId(),UserRole.ROLE_USER);
    }


}
