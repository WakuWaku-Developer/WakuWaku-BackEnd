package dev.backend.wakuwaku.global.infra.google.places.details;

import com.google.gson.Gson;
import dev.backend.wakuwaku.global.exception.WakuWakuException;
import dev.backend.wakuwaku.global.infra.google.places.dto.Places;
import dev.backend.wakuwaku.global.infra.google.places.photo.GooglePlacesPhotoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static dev.backend.wakuwaku.global.exception.ExceptionStatus.NOT_EXISTED_DETAILS_RESPONSE;
import static dev.backend.wakuwaku.global.exception.ExceptionStatus.NOT_EXISTED_PLACE_ID;
import static dev.backend.wakuwaku.global.infra.google.places.details.constant.DetailsConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(value = GooglePlacesDetailsService.class)
class GooglePlacesDetailsServiceTest {
    @Autowired
    private GooglePlacesDetailsService googlePlacesDetailsService;

    @MockBean
    private GooglePlacesPhotoService googlePlacesPhotoService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Value("${google-places}")
    private String apiKey;

    private static final String PLACE_ID = "ChIJubHyMNaMGGARFRSq4A7C3Uo";

    @DisplayName("식당의 세부 정보를 얻는 테스트")
    @Test
    void getRestaurantByDetailsSearch() {
        // given
        String response = "{\"nationalPhoneNumber\":\"03-5389-1077\",\"formattedAddress\":\"일본〒160-0023Tokyo,ShinjukuCity,Nishishinjuku,7-chōme−9−１５ダイカンプラザビジネス清田ビル１階\",\"rating\":3.8,\"websiteUri\":\"https://tokyo-mentsudan.com/\",\"businessStatus\":\"OPERATIONAL\",\"userRatingCount\":1299,\"displayName\":{\"text\":\"사누키우동타이시도쿄멘츠단\"},\"takeout\":true,\"delivery\":true,\"dineIn\":true,\"reservable\":true,\"servesBreakfast\":true,\"servesLunch\":true,\"servesDinner\":true,\"servesBeer\":true,\"servesWine\":false,\"servesVegetarianFood\":false,\"currentOpeningHours\":{\"openNow\":false,\"weekdayDescriptions\":[\"월요일:오전11:00~오후10:30\",\"화요일:오전11:00~오후10:30\",\"수요일:오전11:00~오후10:30\",\"목요일:오전11:00~오후10:30\",\"금요일:오전11:00~오후10:30\",\"토요일:오전11:00~오후10:30\",\"일요일:오전11:00~오후10:30\"]},\"reviews\":[{\"relativePublishTimeDescription\":\"2달전\",\"rating\":4,\"text\":{\"text\":\"신주쿠역근처우동맛집이라고해야할까?\\n아무튼\\n입구에다양한메뉴가있고\\n출입문을들어서면제면사가면을뽑고\\n주문한면과추가사항을처리하여고객에게제공한다.\\n그면을들고우측으로이동하여필요한부분을추가하고\\n계산한뒤식사를한다.\\n면은정말좋다.\\n사실이런면빨먹을려고그곳까지찾아간곳이다.\"},\"authorAttribution\":{\"displayName\":\"장덕규\",\"photoUri\":\"https://lh3.googleusercontent.com/a-/ALV-UjWjgkL7RdA-g7-1so1hlOLfSP3ZNHQIY1KtdGLCHOtNHQy6Z06DtA=s128-c0x00000000-cc-rp-mo-ba6\"}},{\"relativePublishTimeDescription\":\"1달전\",\"rating\":4,\"text\":{\"text\":\"가성비탁월한우동맛집.먹는동안우동면이육수에오래담겨져있어도끝까지퍼지지않고쫄깃거렸다.밀가루냄새를싫어해서한국에선우동안먹는데여기선한그릇다먹음\"},\"authorAttribution\":{\"displayName\":\"pulzip\",\"photoUri\":\"https://lh3.googleusercontent.com/a-/ALV-UjVpW7dscLJCNTYYkc2B0KC03v7_kXqSuSVFJ8v0pnm50Z8GhCA=s128-c0x00000000-cc-rp-mo\"}},{\"relativePublishTimeDescription\":\"3달전\",\"rating\":5,\"text\":{\"text\":\"사장님이정말친절하십니다우동양이상당히많아서주의하세요\"},\"authorAttribution\":{\"displayName\":\"김민규\",\"photoUri\":\"https://lh3.googleusercontent.com/a/ACg8ocLdclSn5OCX-ISL24BHGncWjJ165_t1M_VHCc2nZHLUtHUs2g=s128-c0x00000000-cc-rp-mo\"}},{\"relativePublishTimeDescription\":\"1년전\",\"rating\":5,\"text\":{\"text\":\"처음에주문하기가좀어려웠다.입구들어가고옆에서주문하면면만나오는데,그대로벽따라들고가서국물받고결제하면된다.소중대사이즈가있었는데입이짧아서소사이즈도충분했다.일단가격이싸길래찾아간거였는데면발이맛있었다.\"},\"authorAttribution\":{\"displayName\":\"Flynn\",\"photoUri\":\"https://lh3.googleusercontent.com/a-/ALV-UjUMvA4NZ54ADbIPCLEKLzGkWfHbRXfisjroobB2kBI3alvzg0RN=s128-c0x00000000-cc-rp-mo-ba5\"}},{\"relativePublishTimeDescription\":\"1년전\",\"rating\":5,\"text\":{\"text\":\"한번먹어도되는우동집\\n일본전통우동맛을\\n즐길수있어요.\\n소중대고를수있어요.\\n주문하면면만주지만당황하지말고가르쳐주는대로하면됩니다.\\n육수는따로받으셔야합니다.\\n일본전통우동을즐길수있습니다.\\n많이먹지못하면소자시키세요.\\n생각보다양이많아요.\"},\"authorAttribution\":{\"displayName\":\"MG\",\"photoUri\":\"https://lh3.googleusercontent.com/a-/ALV-UjV_PcIxRu2EPSJ3BuLNJTU5s5oGq4Nr7sIY1RGulaHsLu5n6Slc=s128-c0x00000000-cc-rp-mo\"}}],\"photos\":[{\"name\":\"places/ChIJubHyMNaMGGARFRSq4A7C3Uo/photos/AUc7tXU0hdFB90m-_6ettU4tR5xybiKqg48y0EwCYMtdWqLDDMx8jRiES_jCtP4-Ossk1MzhNU2ISK1b_GdvHyVGA6WARoa0056VZMGFIiQb-TTdJ_8GXCevxD5DXhgeDmRIQc2Djy6ukZVm1gizf1TAtDMJ0xt_WawYhVoC\",\"widthPx\":1280,\"heightPx\":720,\"authorAttributions\":[{\"displayName\":\"東京麺通団\",\"photoUri\":\"//lh3.googleusercontent.com/a-/ALV-UjUxZoxfjIU7NtZjvQYth-bF11CrxcA-PRPcAewrWbFtqpb0KKw=s100-p-k-no-mo\"}]},{\"name\":\"places/ChIJubHyMNaMGGARFRSq4A7C3Uo/photos/AUc7tXWIPOP4Cgf86OsNm8wSQ4YdWyhPkP1AtItvn6HwKsNwS2s6n-8CZROYDySTQaxi3s1aVDrAhWlwhqY4H7lve57LlKFRxiDpQR1KZslN1e78rzT_GoHUaYCQR0AfL7vQSU4JwRuBiUYwsG1bHXTZJIa9WO0Toj16fDVS\",\"widthPx\":1440,\"heightPx\":809,\"authorAttributions\":[{\"displayName\":\"東京麺通団\",\"photoUri\":\"//lh3.googleusercontent.com/a-/ALV-UjUxZoxfjIU7NtZjvQYth-bF11CrxcA-PRPcAewrWbFtqpb0KKw=s100-p-k-no-mo\"}]},{\"name\":\"places/ChIJubHyMNaMGGARFRSq4A7C3Uo/photos/AUc7tXUKs7wDu7Hs_DLWx8pJF3uKCba0F0ovqP5GrvQ7cSUx5-BXaM1rjRZ4FDV-K9N7-qMnV-JVAUX-2qrUm-kL7lVyKilOCuYQgWt3SxZ_7AMPukd3rgGcHXzirnGBEQllPheYU5Q-YVnJgHT_g0DkFBM7fpyMt8_vMAji\",\"widthPx\":4032,\"heightPx\":2268,\"authorAttributions\":[{\"displayName\":\"TakujiFujita\",\"photoUri\":\"//lh3.googleusercontent.com/a-/ALV-UjUXf_mvOpfURmtgsAvo51FvMY1WN9lanogD7Rmbf58y2xmlyvg_=s100-p-k-no-mo\"}]},{\"name\":\"places/ChIJubHyMNaMGGARFRSq4A7C3Uo/photos/AUc7tXXJgcie89JlmyW3bnjv7H08nhT8sn03pVAJitWk-WUNSFmYz_YcBSK2a-5Ia5gYNjpMyUB80IJ6ePjm1fvgs8gmUyX3dy5Y-gbdumypX0xw_cRSgmIfpZxzVMsmHrtGsAFUigpmG_ZEOK2YTVsyC9t4p7gIc9BGYRB9\",\"widthPx\":4000,\"heightPx\":3000,\"authorAttributions\":[{\"displayName\":\"ChiKitWong\",\"photoUri\":\"//lh3.googleusercontent.com/a-/ALV-UjUdXmOqTfrjsX-RLwyNaDsqfJFK6Mtq48haqbvzLd0w4Pvb3XFJ1g=s100-p-k-no-mo\"}]},{\"name\":\"places/ChIJubHyMNaMGGARFRSq4A7C3Uo/photos/AUc7tXUb1jzibrL_8KL7D_2PWDgQm1svgu0z7JSvkP-DRpdA1l2VI2SUlSJFIFia_YnRtjRllKyvJxBYgZAgR2_PqrKNeYXDPJwnOKvPWaP6C7tEm-s8PvFsJYs5O4Dur9MGFZADcnKSKlRLG4gElyGdV2t8Eo6UN-FqyRcG\",\"widthPx\":5712,\"heightPx\":4284,\"authorAttributions\":[{\"displayName\":\"SpencerHuey\",\"photoUri\":\"//lh3.googleusercontent.com/a-/ALV-UjVKHg1eAWew7uCebxOVcjJs70vmeMmF1sCWlfq-y2CuNFKydlJI=s100-p-k-no-mo\"}]},{\"name\":\"places/ChIJubHyMNaMGGARFRSq4A7C3Uo/photos/AUc7tXVSZarj2Fe2d-KnIXjwGV_c-FPh3SLgN0VADUpVR8hpAIuUXsaEAtoGNNpkfAVOjtVAkBGXsfZ2CRiwJXt0mWa4IPPHOkxd-64hLjxeLrxBNpF-AXfHw8syib47h2BLzXJ04uRmU5fl_NFO1pdifgRYWEfourn02MEM\",\"widthPx\":3024,\"heightPx\":4032,\"authorAttributions\":[{\"displayName\":\"るーくすかい\",\"photoUri\":\"//lh3.googleusercontent.com/a/ACg8ocIy4suip8Kn9z91Gzq7MYzHInzIom60PJVo0pM4SQuaMdAB5Q=s100-p-k-no-mo\"}]},{\"name\":\"places/ChIJubHyMNaMGGARFRSq4A7C3Uo/photos/AUc7tXUG8ZL37t5HwhO6tWi1zPcC2hlnxtXKonhZCUgvNE_tqi3gUb_ui1rZvjOcZ1FMq3_j7gvBIAB29K2OZSOyBwUEX9UGz5edrdN5X37WqmVu39kvpHwYefUHOsEQi5rtZoq1RgzssH3ffUNRAtUnyRSbo9B-2ToY8Mig\",\"widthPx\":584,\"heightPx\":438,\"authorAttributions\":[{\"displayName\":\"二代目こうめ\",\"photoUri\":\"//lh3.googleusercontent.com/a-/ALV-UjUKw8HrxlpbK5qFKl912gj_WPAGRVpLHYpdzOegB4GGq5NA8SQObg=s100-p-k-no-mo\"}]},{\"name\":\"places/ChIJubHyMNaMGGARFRSq4A7C3Uo/photos/AUc7tXWmpaSxWYS5RfY6unkCZ2pyzDRIXHq62rkxZGXhpVGZW7Vs_qLeiT_4SV1v4bn32N8MWqmWCJxVR8PgDRh1t8Zw5I_ly1UX-wLoteV1Br9zhwjnZejQuTMRhQ_hxjGczRXPJvKIsvJ1zerWwgsutNT8QhSnJkoNOrXM\",\"widthPx\":4031,\"heightPx\":3023,\"authorAttributions\":[{\"displayName\":\"Jenny\",\"photoUri\":\"//lh3.googleusercontent.com/a/ACg8ocKT7YS0KpzAFudUpLo9S-8itpQeAa-qFBQtZPaxnE2syzo4zg=s100-p-k-no-mo\"}]},{\"name\":\"places/ChIJubHyMNaMGGARFRSq4A7C3Uo/photos/AUc7tXWaxfR8_a1Jn7nUUCc5dTuVZbQfzgs82HZbcxINJMh7rF00Sl5KXrzmHQ2Bi3DHppl95Qkmxhy7rNAcA8b0inA0T3YvdBVFFxVGrNZNyAplnlOtaiRrNfHM-7jqt5g1Fu6wdC_-G2gHh9JkElhTsVZeIDt9YZFEoZB2\",\"widthPx\":4032,\"heightPx\":3024,\"authorAttributions\":[{\"displayName\":\"大熊（BigBear）\",\"photoUri\":\"//lh3.googleusercontent.com/a-/ALV-UjWixp6m_jI2svhbS54qUTL0JHrrfiY9_nhKEgHxjUVW-XvMrzxl=s100-p-k-no-mo\"}]},{\"name\":\"places/ChIJubHyMNaMGGARFRSq4A7C3Uo/photos/AUc7tXUL4YDAl5ADbDqxIQuSHI-E7kg8ZiSSm6x22jqEVQfMRZHIrBZWfYu-_2j0nvqpZwuPWFAxsVN8qifVegs0F7uPQTcoOS2JY3IIUy0XKO17Mo_IptWg-DgV4_09isCF6guaPohX9LikyRUi6kTNMfWT3Kq0SM-QuHJF\",\"widthPx\":4032,\"heightPx\":3024,\"authorAttributions\":[{\"displayName\":\"大熊（BigBear）\",\"photoUri\":\"//lh3.googleusercontent.com/a-/ALV-UjWixp6m_jI2svhbS54qUTL0JHrrfiY9_nhKEgHxjUVW-XvMrzxl=s100-p-k-no-mo\"}]}]}";

        mockServer.expect(requestTo(getDetailsUrl())
                )
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        // when
        Places place = googlePlacesDetailsService.getRestaurantByDetailsSearch(PLACE_ID);

        // then
        mockServer.verify();

        assertThat(place.getDisplayName()).isNotNull();
        assertThat(place.getRating()).isNotNaN();
        assertThat(place.getPhotos()).hasSizeLessThanOrEqualTo(10);
        assertThat(place.getUserRatingCount()).isNotNegative();
        assertThat(place.getCurrentOpeningHours()).isNotNull();
        assertThat(place.getEditorialSummary()).isNotNull();
        assertThat(place.getReviews()).isNotNull();
        assertThat(place.getNationalPhoneNumber()).isNotNull();
        assertThat(place.getFormattedAddress()).isNotNull();
        assertThat(place.getWebsiteUri()).isNotNull();
        assertThat(place.isDineIn()).isTrue();
        assertThat(place.isTakeout()).isTrue();
        assertThat(place.isDelivery()).isTrue();
        assertThat(place.isReservable()).isTrue();
        assertThat(place.isServesBreakfast()).isTrue();
        assertThat(place.isServesLunch()).isTrue();
        assertThat(place.isServesDinner()).isTrue();
        assertThat(place.isServesBeer()).isTrue();
        assertThat(place.isServesWine()).isFalse();
        assertThat(place.isServesVegetarianFood()).isFalse();
    }

    @Test
    @DisplayName("place id가 비어있으면 NOT_EXISTED_PLACE_ID 예외가 발생해야 한다.")
    void failByPlaceIdIsEmpty() {
        // when & then
        thenThrownBy(
                () -> googlePlacesDetailsService.getRestaurantByDetailsSearch("")
        )
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(NOT_EXISTED_PLACE_ID);
    }

    @Test
    @DisplayName("place id가 null 이면 NOT_EXISTED_PLACE_ID 예외가 발생해야 한다.")
    void failByPlaceIdIsNull() {
        // when & then
        thenThrownBy(
                () -> googlePlacesDetailsService.getRestaurantByDetailsSearch(null)
        )
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(NOT_EXISTED_PLACE_ID);
    }

    @Test
    @DisplayName("응답 값이 null 이면 NOT_EXISTED_DETAILS_RESPONSE 예외가 발생해야 한다.")
    void failByDetailsResponseIsNull() {
        // given
        Gson gson = new Gson();
        String content = gson.toJson(null);

        mockServer.expect(requestTo(getDetailsUrl())
                )
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(content, MediaType.APPLICATION_JSON));

        // when & then
        thenThrownBy(
                () -> googlePlacesDetailsService.getRestaurantByDetailsSearch(PLACE_ID)
        )
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(NOT_EXISTED_DETAILS_RESPONSE);

        mockServer.verify();
    }

    private String getDetailsUrl() {
        return DETAILS_URL + PLACE_ID + DETAILS_RESPONSE_FIELDS + DETAILS_LANGUAGE_CODE + "&key=" + apiKey;
    }
}