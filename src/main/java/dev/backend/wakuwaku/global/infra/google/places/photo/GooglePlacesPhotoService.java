package dev.backend.wakuwaku.global.infra.google.places.photo;

import dev.backend.wakuwaku.global.infra.google.places.dto.Photo;
import dev.backend.wakuwaku.global.infra.google.places.photo.dto.response.PhotoResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static dev.backend.wakuwaku.global.exception.WakuWakuException.NONE_PHOTO_URL;
import static dev.backend.wakuwaku.global.infra.google.places.photo.constant.PhotoConstant.*;

@Service
public class GooglePlacesPhotoService {
    private final RestClient restClient;

    public GooglePlacesPhotoService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String getActualPhotoUrl(Photo photo, String apiKey) {
        PhotoResponse photoResult = restClient.get()
                                              .uri(getPhotoUrl(photo, apiKey))
                                              .accept(MediaType.APPLICATION_JSON)
                                              .retrieve()
                                              .body(PhotoResponse.class);

        if (photoResult == null || photoResult.getPhotoUri() == null || photoResult.getPhotoUri().isEmpty() || photoResult.getPhotoUri().isBlank()) {
            throw NONE_PHOTO_URL;
        }

        return photoResult.getPhotoUri();
    }

    private String getPhotoUrl(Photo photo, String apiKey) {
        return PHOTO_PLACE_ID_URL + photo.getName() + PHOTO_MEDIA_KEY_URL + apiKey + PHOTO_SKIP_HTTP_REDIRECT + PHOTO_MAX_HEIGHT_WIDTH_PX;
    }
}
