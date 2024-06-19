package dev.backend.wakuwaku.global.infra.google.places.details.dto.response;

import dev.backend.wakuwaku.global.infra.google.places.dto.DisplayName;
import dev.backend.wakuwaku.global.infra.google.places.dto.LocalizedText;
import dev.backend.wakuwaku.global.infra.google.places.dto.Photo;
import dev.backend.wakuwaku.global.infra.google.places.dto.Review;
import dev.backend.wakuwaku.global.infra.google.places.dto.openinghours.CurrentOpeningHours;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class DetailsResponse {
    private DisplayName displayName;

    private LocalizedText editorialSummary;

    private List<Photo> photos;

    private boolean dineIn;

    private boolean delivery;

    private boolean takeout;

    private String websiteUri;

    private String nationalPhoneNumber;

    private String formattedAddress;

    private boolean reservable;

    private boolean servesBreakfast;

    private boolean servesLunch;

    private boolean servesDinner;

    private boolean servesBeer;

    private boolean servesWine;

    private boolean servesVegetarianFood;

    private Number userRatingCount;

    private Number rating;

    private List<Review> reviews;

    private CurrentOpeningHours currentOpeningHours;

    public DisplayName getDisplayName() {
        return displayName != null ? displayName : new DisplayName();
    }

    public CurrentOpeningHours getCurrentOpeningHours() {
        return currentOpeningHours != null ? currentOpeningHours : new CurrentOpeningHours();
    }

    public LocalizedText getEditorialSummary() {
        return editorialSummary != null ? editorialSummary : new LocalizedText();
    }

    public List<Photo> getPhotos() {
        return photos != null ? photos : Collections.emptyList();
    }

    public List<Review> getReviews() {
        return reviews != null ? reviews : Collections.emptyList();
    }
}
