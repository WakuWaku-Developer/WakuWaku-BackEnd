package dev.backend.wakuwaku.domain.restaurant.dto.response;

import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.PlacePhoto;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.PlaceReview;
import lombok.Data;

import java.util.List;

@Data
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

    private List<PlaceReview> review;

    private boolean openNow;

    private List<String> weekdayText;

    public DetailsInfoRestaurantResponse(Result result) {
        this.name = result.getName();
        this.editorialSummary = result.getEditorial_summary().getOverview();
        this.photos = result.getPhotos().stream()
                .map(PlacePhoto::getPhoto_reference)
                .toList();
        this.dineIn = result.isDine_in();
        this.delivery = result.isDelivery();
        this.takeout = result.isTakeout();
        this.website = result.getWebsite();
        this.formattedPhoneNumber = result.getFormatted_phone_number();
        this.formattedAddress = result.getFormatted_address();
        this.reservable = result.isReservable();
        this.servesBreakfast = result.isServes_breakfast();
        this.servesLunch = result.isServes_lunch();
        this.servesDinner = result.isServes_dinner();
        this.servesBeer = result.isServes_beer();
        this.servesWine = result.isServes_wine();
        this.servesVegetarianFood = result.isServes_vegetarianFood();
        this.userRatingsTotal = result.getUser_ratings_total();
        this.rating = result.getRating();
        this.review = result.getReviews();
        this.openNow = result.getCurrent_opening_hours().isOpen_now();
        this.weekdayText = result.getCurrent_opening_hours().getWeekday_text();
    }
}
