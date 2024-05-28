package dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto;

import lombok.Getter;

@Getter
public class PlaceReview {
    String author_name;
    Number rating;
    String relative_time_description;
    String profile_photo_url;
    String text;
    boolean translated;
}
