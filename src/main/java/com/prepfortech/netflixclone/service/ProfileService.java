package com.prepfortech.netflixclone.service;

import com.prepfortech.netflixclone.accessor.ProfileAccessor;
import com.prepfortech.netflixclone.accessor.model.ProfileType;
import com.prepfortech.netflixclone.accessor.model.UserDTO;
import com.prepfortech.netflixclone.controller.model.ProfileTypeInput;
import com.prepfortech.netflixclone.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ProfileService {
    @Autowired
    ProfileAccessor profileAccessor;

    public void activateProfile(final String name, final ProfileTypeInput type){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        if(name.length() <5 || name.length()>20){
            throw new InvalidDataException("Name length should be between 5  and 20");

        }
        profileAccessor.addNewProfile(userDTO.getUserId(),name, ProfileType.valueOf(type.name()));


    }
    public void deactivateProfile(final String profileId){
        profileAccessor.deleteProfile(profileId);
    }


}
