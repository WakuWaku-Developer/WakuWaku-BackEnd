package dev.backend.wakuwaku.global.infra.google.places.old.photo;

import dev.backend.wakuwaku.global.exception.ExceptionStatus;
import dev.backend.wakuwaku.global.exception.WakuWakuException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static dev.backend.wakuwaku.global.infra.google.places.old.photo.dto.request.PhotoRequest.PHOTO_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(value = {GooglePlacesPhotoService.class})
class GooglePlacesPhotoServiceTest {
    @Autowired
    private GooglePlacesPhotoService googlePlacesPhotoService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private RestClient.Builder restClient = RestClient.builder();

    @Value("${google-places}")
    private String apiKey;

    private static final String PHOTO_REFERENCE = "AUGGfZm5Sf_CjR-pokgGs4UwMib89F84SlbBx8rqZ9eZ1fTVlkonUdRXPBZUVPzrSlZz8Ato3iY6RKkE3gPos-2Bo5ffcv73RD0GZnrcVjPeuTbsBI1tR2fbhZA3Sk59fJ58OoyhnM7GAHKdHJdpCpDjo_aIghIugwriAA41BDRqLWdVA-5n";

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.bindTo(restClient).build();
    }

    @Test
    @DisplayName("실제 이미지 URL을 반환하는지 테스트")
    void getPhotosURLByTextSearch() {
        //given
        String response = "https://lh3.googleusercontent.com/places/ANXAkqFVKvKKJF9PvO5CRH_QqzNwhk3fVw7fet05L49Zt2OFwMvPzX1wwC2qdXs3x2zO4x08fFsJojhgvga3GYWbb16PRO471kMleWY=s1600-w800";

        mockServer.expect(requestTo(PHOTO_URL + PHOTO_REFERENCE + "&key=" + apiKey))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.TEXT_PLAIN));

        // when
        String photosURL = googlePlacesPhotoService.getPhotosURL(PHOTO_REFERENCE, apiKey);

        // then
        assertThat(photosURL).containsPattern("https.*s1600-w800");
    }

    @Test
    @DisplayName("이미지 URL을 파싱하지 못하면 INVALID_PHOTO_REFERENCE 예외가 발생해야 한다.")
    void failGetPhotosURLByTextSearch() {
        //given
        String failPhotoReference = "fail";

        // when & then
        thenThrownBy(
                () -> googlePlacesPhotoService.getActualPhotoURL(failPhotoReference)
        )
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(ExceptionStatus.INVALID_PHOTO_REFERENCE);
    }
}
