package com.prepfortech.netflixclone.accessor.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class VideoDTO {

    private String videoId;
    private String name;
    private String seriesId;
    private String showId;
    private double rating;
    private Date releaseDate;
    private int totalLength;

}
