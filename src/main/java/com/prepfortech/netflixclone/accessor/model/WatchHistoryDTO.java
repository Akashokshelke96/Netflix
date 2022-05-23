package com.prepfortech.netflixclone.accessor.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Getter
public class WatchHistoryDTO {
    private String profileId;
    private String videoId;
    private double rating;
    private int watchedLength;
    private Date lastWatchedAt;
    private Date firstWatchedAt;

}
