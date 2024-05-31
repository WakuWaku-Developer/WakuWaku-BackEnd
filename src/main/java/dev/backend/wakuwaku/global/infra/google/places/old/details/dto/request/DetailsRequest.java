package dev.backend.wakuwaku.global.infra.google.places.old.details.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DetailsRequest {
    public static final String DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?language=ko";
    public static final String DETAILS_FIELDS = "&fields=name,photos/photo_reference,rating,current_opening_hours/open_now,current_opening_hours/weekday_text,dine_in,delivery,takeout,reviews/author_name,reviews/rating,reviews/relative_time_description,reviews/profile_photo_url,reviews/text,reviews/translated,website,formatted_phone_number,formatted_address,reservable,serves_breakfast,serves_lunch,serves_dinner,serves_beer,serves_wine,serves_vegetarian_food,user_ratings_total,editorial_summary";

    public final String placeId;
}
