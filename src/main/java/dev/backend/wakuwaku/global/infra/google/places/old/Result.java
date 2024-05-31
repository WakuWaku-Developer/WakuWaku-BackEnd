package dev.backend.wakuwaku.global.infra.google.places.old;

import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
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
}
