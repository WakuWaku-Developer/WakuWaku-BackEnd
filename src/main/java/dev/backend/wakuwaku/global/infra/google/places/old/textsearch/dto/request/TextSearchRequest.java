package dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TextSearchRequest {
    public static final String TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?language=ko&query=";
    public static final String NEXT_PAGE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?pagetoken=";

    public final String textQuery;
}
