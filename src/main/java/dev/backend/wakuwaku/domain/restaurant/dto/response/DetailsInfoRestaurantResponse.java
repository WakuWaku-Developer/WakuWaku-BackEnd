package dev.backend.wakuwaku.domain.restaurant.dto.response;

import dev.backend.wakuwaku.global.infra.google.places.Places;
import dev.backend.wakuwaku.global.infra.google.places.dto.Photo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DetailsInfoRestaurantResponse {
    private String name;

    private String editorialSummary;

    private List<String> photos;

    private boolean dineIn;

    private boolean delivery;

    private boolean takeout;

    private String website;

    private String formattedPhoneNumber;

    private String formattedAddress;

    private boolean reservable;

    private boolean servesBreakfast;

    private boolean servesLunch;

    private boolean servesDinner;

    private boolean servesBeer;

    private boolean servesWine;

    private boolean servesVegetarianFood;

    private Number userRatingsTotal;

    private Number rating;

    private List<ReviewResponse> reviews;

    private boolean openNow;

    private List<String> weekdayText;

    public DetailsInfoRestaurantResponse(Places places) {
        this.name = places.getDisplayName().getText();
        this.editorialSummary = places.getEditorialSummary().getText();
        this.photos = places.getPhotos().stream()
                .map(Photo::getPhotoUrl)
                .toList();
        this.dineIn = places.isDineIn();
        this.delivery = places.isDelivery();
        this.takeout = places.isTakeout();
        this.website = places.getWebsiteUri();
        this.formattedPhoneNumber = places.getNationalPhoneNumber();
        this.formattedAddress = places.getFormattedAddress();
        this.reservable = places.isReservable();
        this.servesBreakfast = places.isServesBreakfast();
        this.servesLunch = places.isServesLunch();
        this.servesDinner = places.isServesDinner();
        this.servesBeer = places.isServesBeer();
        this.servesWine = places.isServesWine();
        this.servesVegetarianFood = places.isServesVegetarianFood();
        this.userRatingsTotal = places.getUserRatingCount();
        this.rating = places.getRating();
        this.reviews = places.getReviews().stream()
                .map(ReviewResponse::new)
                .toList();
        this.openNow = places.getCurrentOpeningHours().isOpenNow();
        this.weekdayText = places.getCurrentOpeningHours().getWeekdayDescriptions();
    }
}
