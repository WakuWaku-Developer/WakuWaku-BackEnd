package dev.backend.wakuwaku.global.infra.google.places.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TextSearchRequest {
    public final String textQuery;

    public static final String TEXT_SEARCH_URL = "https://places.googleapis.com/v1/places:searchText";

    public static final String TEXT_SEARCH_RESPONSE_FIELDS = "places.displayName,places.location,places.rating,places.id";
}
