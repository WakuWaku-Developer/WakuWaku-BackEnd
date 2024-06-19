package dev.backend.wakuwaku.global.infra.google.places;

import dev.backend.wakuwaku.global.infra.google.places.dto.DisplayName;
import dev.backend.wakuwaku.global.infra.google.places.dto.Location;
import dev.backend.wakuwaku.global.infra.google.places.dto.LocalizedText;
import dev.backend.wakuwaku.global.infra.google.places.dto.Photo;
import dev.backend.wakuwaku.global.infra.google.places.dto.Review;
import dev.backend.wakuwaku.global.infra.google.places.dto.openinghours.CurrentOpeningHours;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
public class Places {
    private String id;

    private double rating;

    private DisplayName displayName;

    private Location location;

    private CurrentOpeningHours currentOpeningHours;

    private List<Photo> photos;

    private boolean dineIn;

    private boolean takeout;

    private boolean delivery;

    private LocalizedText editorialSummary;

    private List<Review> reviews;

    private String nationalPhoneNumber;

    private String formattedAddress;

    private String websiteUri;

    private int userRatingCount;

    private boolean reservable;

    private boolean servesBreakfast;

    private boolean servesLunch;

    private boolean servesDinner;

    private boolean servesBeer;

    private boolean servesWine;

    private boolean servesVegetarianFood;

    private BusinessStatus businessStatus;

    @Builder
    public Places(String id, double rating, DisplayName displayName, Location location, CurrentOpeningHours currentOpeningHours, List<Photo> photos, boolean dineIn, boolean takeout, boolean delivery, LocalizedText editorialSummary, List<Review> reviews, String nationalPhoneNumber, String formattedAddress, String websiteUri, int userRatingCount, boolean reservable, boolean servesBreakfast, boolean servesLunch, boolean servesDinner, boolean servesBeer, boolean servesWine, boolean servesVegetarianFood) {
        this.id = id;
        this.rating = rating;
        this.displayName = displayName;
        this.location = location;
        this.currentOpeningHours = currentOpeningHours;
        this.photos = photos;
        this.dineIn = dineIn;
        this.takeout = takeout;
        this.delivery = delivery;
        this.editorialSummary = editorialSummary;
        this.reviews = reviews;
        this.nationalPhoneNumber = nationalPhoneNumber;
        this.formattedAddress = formattedAddress;
        this.websiteUri = websiteUri;
        this.userRatingCount = userRatingCount;
        this.reservable = reservable;
        this.servesBreakfast = servesBreakfast;
        this.servesLunch = servesLunch;
        this.servesDinner = servesDinner;
        this.servesBeer = servesBeer;
        this.servesWine = servesWine;
        this.servesVegetarianFood = servesVegetarianFood;
    }

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
