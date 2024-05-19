package dev.backend.wakuwaku.global.infra.google.places.old.photo.dto.request;

import static dev.backend.wakuwaku.global.infra.google.places.constant.GooglePlacesAPIRequestConstant.PHOTO_REQUEST_WIDTH;

public class PhotoRequest {
    public static final String PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + PHOTO_REQUEST_WIDTH +  "&photo_reference=";
}
