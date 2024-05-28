package dev.backend.wakuwaku.global.infra.google.places.old.photo;

import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.request.TextSearchRequest;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.TextSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

import java.util.List;

import static dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.request.TextSearchRequest.TEXT_SEARCH_URL;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GooglePlacesPhotoServiceTest {
    @Autowired
    private GooglePlacesPhotoService googlePlacesPhotoService;

    @Value("${google-places}")
    private String apiKey;

    @Test
    @DisplayName("실제 이미지 URL을 반환하는지 테스트")
    void getPhotosURLByTextSearch() {
        //given
        String searchWord = "도쿄 맛집";

        TextSearchResponse textSearchResponse = RestClient.create().get()
                .uri(textSearchURI(searchWord))
                .retrieve()
                .body(TextSearchResponse.class);

        List<Result> results = textSearchResponse.getResults();

        // when
        String photosURL = googlePlacesPhotoService.getPhotosURL(results.get(0).getPhotos().get(0).getPhoto_reference(), apiKey);

        // then
        assertThat(photosURL).containsPattern("https.*s1600-w800");
    }

    private String textSearchURI(String searchWord) {
        TextSearchRequest textSearchRequest = new TextSearchRequest(searchWord);

        String newTextQuery = textSearchRequest.getTextQuery().replace(" ", "%20");

        return TEXT_SEARCH_URL + newTextQuery + "&key=" + apiKey;
    }
}
