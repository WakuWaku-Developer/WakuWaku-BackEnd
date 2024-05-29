package dev.backend.wakuwaku.global.infra.google.places.old;

import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class Result {
    String name;

    String place_id;

    PlaceEditorialSummary editorial_summary = new PlaceEditorialSummary();

    Number user_ratings_total;

    Number rating;

    List<PlacePhoto> photos = new ArrayList<>();

    Geometry geometry = new Geometry();

    boolean dine_in;

    boolean delivery;

    boolean takeout;

    String website;

    String formatted_phone_number;

    String formatted_address;

    boolean reservable;

    boolean serves_breakfast;

    boolean serves_lunch;

    boolean serves_dinner;

    boolean serves_beer;

    boolean serves_wine;

    boolean serves_vegetarianFood;

    List<PlaceReview> reviews = new ArrayList<>();

    PlaceOpeningHours current_opening_hours = new PlaceOpeningHours();

    @Builder
    public Result(String name, String place_id, PlaceEditorialSummary editorial_summary, Number user_ratings_total, Number rating, List<PlacePhoto> photos, Geometry geometry, boolean dine_in, boolean delivery, boolean takeout, String website, String formatted_phone_number, String formatted_address, boolean reservable, boolean serves_breakfast, boolean serves_lunch, boolean serves_dinner, boolean serves_beer, boolean serves_wine, boolean serves_vegetarianFood, List<PlaceReview> reviews, PlaceOpeningHours current_opening_hours) {
        this.name = name;
        this.place_id = place_id;
        this.editorial_summary = editorial_summary;
        this.user_ratings_total = user_ratings_total;
        this.rating = rating;
        this.photos = photos;
        this.geometry = geometry;
        this.dine_in = dine_in;
        this.delivery = delivery;
        this.takeout = takeout;
        this.website = website;
        this.formatted_phone_number = formatted_phone_number;
        this.formatted_address = formatted_address;
        this.reservable = reservable;
        this.serves_breakfast = serves_breakfast;
        this.serves_lunch = serves_lunch;
        this.serves_dinner = serves_dinner;
        this.serves_beer = serves_beer;
        this.serves_wine = serves_wine;
        this.serves_vegetarianFood = serves_vegetarianFood;
        this.reviews = reviews;
        this.current_opening_hours = current_opening_hours;
    }
}
