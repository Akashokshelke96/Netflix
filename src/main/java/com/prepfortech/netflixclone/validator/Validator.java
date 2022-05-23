package com.prepfortech.netflixclone.validator;

import com.prepfortech.netflixclone.accessor.ProfileAccessor;
import com.prepfortech.netflixclone.accessor.VideoAccessor;
import com.prepfortech.netflixclone.accessor.model.ProfileDTO;
import com.prepfortech.netflixclone.accessor.model.VideoDTO;
import com.prepfortech.netflixclone.exceptions.InvalidProfileException;
import com.prepfortech.netflixclone.exceptions.InvalidVideoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    @Autowired
    private ProfileAccessor profileAccessor;

    @Autowired
    private VideoAccessor videoAccessor;

    public void validateProfile(final String profileId,final String userId){
        ProfileDTO profileDTO = profileAccessor.getProfileByProfileId(profileId);
        if(profileDTO != null && profileDTO.getUserId().equals(userId)){
            throw new InvalidProfileException("Profile " + profileId + " is invalid or does not exist!");
        }

    }

    public void validateVideoId(final String videoId) {
        VideoDTO videoDTO = videoAccessor.getVideoByVideoId(videoId);
        if (videoDTO == null) {
            throw new InvalidVideoException("Video with videoId " + videoId + " does not exist!");
        }
    }
}
