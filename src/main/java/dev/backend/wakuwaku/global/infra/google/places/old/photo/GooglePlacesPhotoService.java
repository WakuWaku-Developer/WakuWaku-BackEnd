package dev.backend.wakuwaku.global.infra.google.places.old.photo;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dev.backend.wakuwaku.global.exception.WakuWakuException.INVALID_PHOTO_REFERENCE;
import static dev.backend.wakuwaku.global.infra.google.places.old.photo.dto.request.PhotoRequest.PHOTO_URL;

@Service
public class GooglePlacesPhotoService {
    private final RestClient restClient = RestClient.create();

    public String getPhotosURL(String photoReference, String apiKey) {
        String photoResult = restClient.get()
                .uri(photoApiKeyURI(photoReference, apiKey))
                .accept(MediaType.IMAGE_JPEG)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .body(String.class);

        return getActualPhotoURL(photoResult);
    }

    private String photoApiKeyURI(String photoReference, String apiKey) {
        return PHOTO_URL + photoReference + "&key=" + apiKey;
    }

    public String getActualPhotoURL(String photoResult) {
        // 정규 표현식 컴파일
        Pattern pattern = Pattern.compile("<A HREF=\"([^\"]+)\">");
        Matcher matcher = pattern.matcher(photoResult);

        if (matcher.find()) {
            return matcher.group(1);
        }

        throw INVALID_PHOTO_REFERENCE;
    }
}
