package dev.backend.wakuwaku.global.infra.google.places.old.details;

import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.details.dto.request.DetailsRequest;
import dev.backend.wakuwaku.global.infra.google.places.old.details.dto.response.DetailsResponse;
import dev.backend.wakuwaku.global.infra.google.places.old.photo.GooglePlacesPhotoService;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.PlacePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static dev.backend.wakuwaku.global.infra.google.places.old.details.dto.request.DetailsRequest.DETAILS_FIELDS;
import static dev.backend.wakuwaku.global.infra.google.places.old.details.dto.request.DetailsRequest.DETAILS_URL;

@Service
public class GooglePlacesDetailsService {
    private final GooglePlacesPhotoService photoService;
    private final RestClient restClient = RestClient.create();
    private final String apiKey;

    @Autowired
    public GooglePlacesDetailsService(GooglePlacesPhotoService photoService, @Value("${google-places}") String apiKey) {
        this.photoService = photoService;
        this.apiKey = apiKey;
    }

    public Result detailsSearch(String placeId) {
        DetailsResponse detailsResponse = restClient.get()
                .uri(detailsURI(placeId))
                .retrieve()
                .body(DetailsResponse.class);

        return resultByDetailsSearch(detailsResponse);
    }

    private String detailsURI(String placeId) {
        DetailsRequest detailsRequest = new DetailsRequest(placeId);

        return DETAILS_URL + DETAILS_FIELDS + "&place_id=" + detailsRequest.getPlaceId() + "&key=" + apiKey;
    }

    private Result resultByDetailsSearch(DetailsResponse detailsResponse) {
        if (detailsResponse == null || detailsResponse.getResult() == null) {
            return null;
        }

        Result detailsResult = detailsResponse.getResult();

        for (PlacePhoto photo : detailsResult.getPhotos()) {
            String photoUrl = photoService.getPhotosURL(photo.getPhoto_reference(), apiKey);
            photo.changeToUsablePhotoURL(photoUrl);
        }

        return detailsResult;
    }
}