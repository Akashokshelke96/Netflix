package com.prepfortech.netflixclone.controller;


import com.prepfortech.netflixclone.controller.model.GetWatchHistoryInput;
import com.prepfortech.netflixclone.controller.model.WatchHistoryInput;
import com.prepfortech.netflixclone.exceptions.InvalidProfileException;
import com.prepfortech.netflixclone.exceptions.InvalidVideoException;
import com.prepfortech.netflixclone.security.Roles;
import com.prepfortech.netflixclone.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
public class WatchHistoryController {

    @Autowired
    WatchHistoryService watchHistoryService;

    @PostMapping("/video/{videoId}/watchTime")
    @Secured({Roles.Customer})
    public ResponseEntity<Void> updateWatchHistory(@PathVariable("videoId") String videoId,
                                                   @RequestBody WatchHistoryInput watchHistoryInput){
        System.out.println("video id " + videoId);
        try{
            String profileId = watchHistoryInput.getProfileId();
            int watchTime = watchHistoryInput.getWatchTime();
            watchHistoryService.updateWatchHistory(profileId,videoId,watchTime);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(InvalidVideoException | InvalidProfileException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/video/{videoId}/watchTime")
    @Secured({ Roles.Customer })
    public ResponseEntity<Integer> getWatchHistory(@PathVariable("videoId") String videoId,
                                                   @RequestBody GetWatchHistoryInput input) {
        String profileId = input.getProfileId();
        System.out.println("video id " + videoId);
        try {
            int watchLength = watchHistoryService.getWatchHistory(profileId, videoId);
            return ResponseEntity.status(HttpStatus.OK).body(watchLength);
        }
        catch(InvalidVideoException | InvalidProfileException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
