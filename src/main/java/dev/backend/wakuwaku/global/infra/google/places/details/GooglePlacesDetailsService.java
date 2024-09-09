package dev.backend.wakuwaku.global.infra.google.places.details;

import dev.backend.wakuwaku.global.infra.google.places.dto.Places;
import dev.backend.wakuwaku.global.infra.google.places.dto.Photo;
import dev.backend.wakuwaku.global.infra.google.places.photo.GooglePlacesPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static dev.backend.wakuwaku.global.exception.WakuWakuException.NOT_EXISTED_DETAILS_RESPONSE;
import static dev.backend.wakuwaku.global.exception.WakuWakuException.NOT_EXISTED_PLACE_ID;
import static dev.backend.wakuwaku.global.infra.google.places.details.constant.DetailsConstant.*;

@Service
public class GooglePlacesDetailsService {
    private final RestClient restClient;

    private final GooglePlacesPhotoService googlePlacesPhotoService;

    @Value("${google-places}")
    private String apiKey;

    public GooglePlacesDetailsService(RestClient.Builder restClientBuilder, @Autowired GooglePlacesPhotoService googlePlacesPhotoService) {
        this.restClient = restClientBuilder.build();
        this.googlePlacesPhotoService = googlePlacesPhotoService;
    }

    public Places getRestaurantByDetailsSearch(String placeId) {
        if (placeId == null || placeId.isEmpty()) {
            throw NOT_EXISTED_PLACE_ID;
        }

        Places detailsResponse = restClient.get()
                                           .uri(getDetailsUrl(placeId))
                                           .retrieve()
                                           .body(Places.class);

        return getDetailsResponse(detailsResponse);
    }

    private Places getDetailsResponse(Places detailsResponse) {
        if (detailsResponse == null) {
            throw NOT_EXISTED_DETAILS_RESPONSE;
        }

        return getUsableDetailsResponse(detailsResponse);
    }

    private Places getUsableDetailsResponse(Places detailsResponse) {
        for (Photo photo : detailsResponse.getPhotos()) {
            if (photo == null || photo.getName() == null || photo.getName().isEmpty() || photo.getName().isBlank()) {
                continue;
            }

            String actualPhotoUrl = googlePlacesPhotoService.getActualPhotoUrl(photo, apiKey);
            photo.createPhotoURL(actualPhotoUrl);
        }

        return detailsResponse;
    }

    private String getDetailsUrl(String placeId) {
        return DETAILS_URL + placeId + DETAILS_RESPONSE_FIELDS + DETAILS_LANGUAGE_CODE + "&key=" + apiKey;
    }
}
