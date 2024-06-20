package dev.backend.wakuwaku.domain.restaurant.dto.response;

import dev.backend.wakuwaku.global.infra.google.places.dto.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewResponse {
    private final String relativePublishTimeDescription;
    private final float rating;
    private final String text;
    private final String authorName;
    private final String authorProfileUrl;

    @Builder
    public ReviewResponse(Review review) {
        this.relativePublishTimeDescription = review.getRelativePublishTimeDescription();
        this.rating = review.getRating();
        this.text = review.getText().getText();
        this.authorName = review.getAuthorAttribution().getDisplayName();
        this.authorProfileUrl = review.getAuthorAttribution().getPhotoUri();
    }
}
