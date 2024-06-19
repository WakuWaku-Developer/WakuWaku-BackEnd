package dev.backend.wakuwaku.global.infra.google.places.textsearch.constant;

public class TextSearchConstant {
    public static final String TEXT_SEARCH_REQUEST_API_KEY_HEADER = "X-Goog-Api-Key";
    public static final String TEXT_SEARCH_RESPONSE_FIELDS_HEADER = "X-Goog-FieldMask";
    public static final String TEXT_SEARCH_URL = "https://places.googleapis.com/v1/places:searchText";
    public static final String TEXT_SEARCH_RESPONSE_FIELDS =
            "places.displayName.text,places.location,places.rating,places.id" +
                    ",places.photos.name,places.photos.widthPx,places.photos.heightPx,places.photos.authorAttributions.displayName,places.photos.authorAttributions.photoUri" +
                    ",places.userRatingCount,places.businessStatus,nextPageToken";
    public static final String TEXT_SEARCH_LANGUAGE_CODE = "ko";
}
