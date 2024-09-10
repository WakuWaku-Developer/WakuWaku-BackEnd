package dev.backend.wakuwaku.global.infra.google.places.photo;

import com.google.gson.Gson;
import dev.backend.wakuwaku.global.exception.ExceptionStatus;
import dev.backend.wakuwaku.global.exception.WakuWakuException;
import dev.backend.wakuwaku.global.infra.google.places.dto.Photo;
import dev.backend.wakuwaku.global.infra.google.places.photo.dto.response.PhotoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static dev.backend.wakuwaku.global.infra.google.places.photo.constant.PhotoConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(value = GooglePlacesPhotoService.class)
class GooglePlacesPhotoServiceTest {
    @Autowired
    private GooglePlacesPhotoService googlePlacesPhotoService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Value("${google-places}")
    private String apiKey;

    private Photo photo;

    private static final String PHOTO_REFERENCE_NAME = "places/ChIJU-JXDf2LGGARHsancfqkHyE/photos/AUc7tXWA_nwK4L276trNA2bHejO3ki9Vm8hpiBxOSwkdDLZ-5eJg2UyczDZxv0LZ2CKfX54oUhLeGj9HRcnCUp0HatKdLf_-ok8p8AQNAjE-CxleJifVLFPZTB4MLCxwwzRQZJYzcsuNNa1AZrDIV3ERHK-ggFhnL789T9Ja";

    @BeforeEach
    void setUp() {
        photo = Photo.builder()
                     .name(PHOTO_REFERENCE_NAME)
                     .build();
    }

    @Test
    @DisplayName("실제 이미지 URL을 반환하는지 테스트")
    void getActualPhotoUrl() {
        //given
        String photoUrl = "https://lh3.googleusercontent.com/places/ANXAkqFXiQwTzHSU5Nnn_DnP8EZOYttDRQcgZqq1vW6WEONrR5hpkTNZQF_j5FCW1FO3aOmkWwDcjaMwJEUVPFCi4pEXn-hftoEyRzE=s4800-w3616-h3024";

        PhotoResponse photoResponse = new PhotoResponse(photoUrl);

        Gson gson = new Gson();
        String photoResponseToJson = gson.toJson(photoResponse);

        mockServer.expect(requestTo(getPhotoUrl(photo ,apiKey))
                )
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(photoResponseToJson, MediaType.APPLICATION_JSON));

        // when
        String photosURL = googlePlacesPhotoService.getActualPhotoUrl(photo, apiKey);

        // then
        mockServer.verify();

        assertThat(photosURL).isEqualTo(photoResponse.getPhotoUri());
    }

    @Test
    @DisplayName("응답 값이 null 이면 NONE_PHOTO_URL 예외가 발생해야 한다.")
    void failByPhotoResultIsNull() {
        //given
        Gson gson = new Gson();

        String photoResponseToJson = gson.toJson((Object) null);

        mockServer.expect(requestTo(getPhotoUrl(photo ,apiKey))
                )
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(photoResponseToJson, MediaType.APPLICATION_JSON));

        // when & then
        thenThrownBy(
                () -> googlePlacesPhotoService.getActualPhotoUrl(photo, apiKey)
        )
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(ExceptionStatus.NONE_PHOTO_URL);

        mockServer.verify();
    }

    @Test
    @DisplayName("getPhotoUri().isEmpty() 가 참이면 NONE_PHOTO_URL 예외가 발생해야 한다.")
    void failByPhotoUriIsEmpty() {
        //given
        PhotoResponse photoResponse = new PhotoResponse("");

        Gson gson = new Gson();

        String photoResponseToJson = gson.toJson(photoResponse);

        mockServer.expect(requestTo(getPhotoUrl(photo ,apiKey))
                )
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(photoResponseToJson, MediaType.APPLICATION_JSON));

        // when & then
        thenThrownBy(
                () -> googlePlacesPhotoService.getActualPhotoUrl(photo, apiKey)
        )
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(ExceptionStatus.NONE_PHOTO_URL);

        mockServer.verify();
    }

    @Test
    @DisplayName("getPhotoUri().isBlank() 가 참이면 NONE_PHOTO_URL 예외가 발생해야 한다.")
    void failByPhotoUriIsBlank() {
        //given
        PhotoResponse photoResponse = new PhotoResponse("         ");

        Gson gson = new Gson();

        String photoResponseToJson = gson.toJson(photoResponse);

        mockServer.expect(requestTo(getPhotoUrl(photo ,apiKey))
                )
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(photoResponseToJson, MediaType.APPLICATION_JSON));

        // when & then
        thenThrownBy(
                () -> googlePlacesPhotoService.getActualPhotoUrl(photo, apiKey)
        )
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(ExceptionStatus.NONE_PHOTO_URL);

        mockServer.verify();
    }

    @Test
    @DisplayName("Photo UrI 값이 null 이면 NONE_PHOTO_URL 예외가 발생해야 한다.")
    void failByPhotoUriIsNull() {
        //given
        PhotoResponse photoResponse = new PhotoResponse(null);

        Gson gson = new Gson();

        String photoResponseToJson = gson.toJson(photoResponse);

        mockServer.expect(requestTo(getPhotoUrl(photo ,apiKey))
                )
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(photoResponseToJson, MediaType.APPLICATION_JSON));

        // when & then
        thenThrownBy(
                () -> googlePlacesPhotoService.getActualPhotoUrl(photo, apiKey)
        )
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(ExceptionStatus.NONE_PHOTO_URL);

        mockServer.verify();
    }

    private String getPhotoUrl(Photo photo, String apiKey) {
        return PHOTO_PLACE_ID_URL + photo.getName() + PHOTO_MEDIA_KEY_URL + apiKey + PHOTO_SKIP_HTTP_REDIRECT + PHOTO_MAX_HEIGHT_WIDTH_PX;
    }
}