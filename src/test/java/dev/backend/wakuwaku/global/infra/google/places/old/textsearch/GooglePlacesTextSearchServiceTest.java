package dev.backend.wakuwaku.global.infra.google.places.old.textsearch;

import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.photo.GooglePlacesPhotoService;
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
import static dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.PlaceDetailsStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GooglePlacesTextSearchServiceTest {
    @Autowired
    private GooglePlacesTextSearchService googlePlacesTextSearchService;

    @Autowired
    private GooglePlacesPhotoService googlePlacesPhotoService;

    @Value("${google-places}")
    private String apiKey;

    @DisplayName("최초 요청 테스트")
    @Test
    void textSearch() {
        // given
        String searchWord = "도쿄 맛집";

        // when
        List<Result> results = googlePlacesTextSearchService.textSearch(searchWord);

        // then
        for (Result result : results) {
            assertThat(result.getName()).isNotNull();
            assertThat(result.getPlace_id()).isNotNull();
            assertThat(result.getUser_ratings_total()).isNotNull();
            assertThat(result.getRating()).isNotNull();
            assertThat(result.getGeometry().getLocation()).isNotNull();
            assertThat(result.getPhotos()).hasSizeLessThanOrEqualTo(1);
        }

        assertThat(results.size()).isNotZero();
        assertThat(results).hasSizeLessThanOrEqualTo(20);
    }

    @DisplayName("next page token 값을 활용한 요청 테스트")
    @Test
    void textSearchByNextPageToken() {
        // given
        String searchWord = "도쿄 맛집";

        TextSearchResponse textSearchResponse = RestClient.create().get()
                .uri(textSearchURI(searchWord))
                .retrieve()
                .body(TextSearchResponse.class);

        // when
        TextSearchResponse searchByNextPageToken = googlePlacesTextSearchService.textSearchByNextPageToken(textSearchResponse);

        List<Result> results = searchByNextPageToken.getResults();

        // then
        assertThat(textSearchResponse.getStatus()).isEqualTo(OK);
        assertThat(textSearchResponse.getNext_page_token()).isNotNull();

        for (Result result : searchByNextPageToken.getResults()) {
            assertThat(result.getName()).isNotNull();
            assertThat(result.getPlace_id()).isNotNull();
            assertThat(result.getUser_ratings_total()).isNotNull();
            assertThat(result.getRating()).isNotNull();
            assertThat(result.getGeometry().getLocation()).isNotNull();
            assertThat(result.getPhotos()).hasSizeLessThanOrEqualTo(1);
        }

        assertThat(results.size()).isNotZero();
        assertThat(results).hasSizeLessThanOrEqualTo(20);
    }

    private String textSearchURI(String searchWord) {
        TextSearchRequest textSearchRequest = new TextSearchRequest(searchWord);

        String newTextQuery = textSearchRequest.getTextQuery().replace(" ", "%20");

        return TEXT_SEARCH_URL + newTextQuery + "&key=" + apiKey;
    }
}