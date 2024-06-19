package dev.backend.wakuwaku.global.infra.google.places.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Review {
    String relativePublishTimeDescription;
    float rating;
    LocalizedText text;
    AuthorAttribution authorAttribution;

    @Builder
    public Review(String relativePublishTimeDescription, float rating, LocalizedText text, AuthorAttribution authorAttribution) {
        this.relativePublishTimeDescription = relativePublishTimeDescription;
        this.rating = rating;
        this.text = text;
        this.authorAttribution = authorAttribution;
    }
}
