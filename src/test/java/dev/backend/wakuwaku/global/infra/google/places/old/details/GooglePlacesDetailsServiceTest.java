package dev.backend.wakuwaku.global.infra.google.places.old.details;

import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.GooglePlacesTextSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GooglePlacesDetailsServiceTest {
    @Autowired
    private GooglePlacesTextSearchService googlePlacesTextSearchService;

    @Autowired
    private GooglePlacesDetailsService googlePlacesDetailsService;

    @Value("${google-places}")
    private String apiKey;

    @Test
    @DisplayName("식당의 세부 정보를 얻는 테스트")
    void detailsSearch() {
        // given
        String searchWord = "도쿄 맛집";

        List<Result> results = googlePlacesTextSearchService.textSearch(searchWord);
        String testPlaceId = results.get(0).getPlace_id();

        // when
        Result result = googlePlacesDetailsService.detailsSearch(testPlaceId);

        // then
        assertThat(result.getName()).isNotNull();
        assertThat(result.getUser_ratings_total()).isNotNull();
        assertThat(result.getRating()).isNotNull();
        assertThat(result.getPhotos()).hasSizeLessThanOrEqualTo(10);
        assertThat(result.getCurrent_opening_hours()).isNotNull();
        assertThat(result.getFormatted_address()).isNotNull();
        assertThat(result.getReviews()).hasSizeLessThanOrEqualTo(5);
    }
}
