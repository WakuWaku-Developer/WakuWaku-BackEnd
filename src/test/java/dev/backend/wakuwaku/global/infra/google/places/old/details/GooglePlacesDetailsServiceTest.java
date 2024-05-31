package dev.backend.wakuwaku.global.infra.google.places.old.details;

import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.photo.GooglePlacesPhotoService;
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

import static dev.backend.wakuwaku.global.infra.google.places.old.details.dto.request.DetailsRequest.DETAILS_FIELDS;
import static dev.backend.wakuwaku.global.infra.google.places.old.details.dto.request.DetailsRequest.DETAILS_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(value = {GooglePlacesDetailsService.class, GooglePlacesPhotoService.class})
class GooglePlacesDetailsServiceTest {
    @Autowired
    private GooglePlacesDetailsService googlePlacesDetailsService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Value("${google-places}")
    private String apiKey;

    private static final String PLACE_ID = "ChIJAQCl79GMGGARZheneHqgIUs";

    @BeforeEach
    void setUp() {
        RestClient.Builder restClient = RestClient.builder();
        mockServer = MockRestServiceServer.bindTo(restClient).build();
    }

    @Test
    @DisplayName("식당의 세부 정보를 얻는 테스트")
    void detailsSearch() {
        // given
        String response = "{\"name\":\"우동신\",\"editorialSummary\":\"다양한반주와함께바삭한튀김과수제우동을맛볼수있는아담한식당입니다.\",\"photos\":[\"https://lh3.googleusercontent.com/places/ANXAkqHyQyDrB5ynmyox-kdMI-WkaoqErahaKoOrCOCnPzlzs4pFR30Ot4NVRBHKWyFXJf1UI5kmYt86Fv-CB5qky9fKMYNL5JuoHjs=s1600-w800\",\"https://lh3.googleusercontent.com/places/ANXAkqFpGVBEwXZ-mvddlBgawTJpuuQuDcK2-3pWI6MCHJUPowi_Bazh_xuTyYo_R_LYQVGhTJf8uji0PfwrcPGAClj-vHgV__kqzgU=s1600-w800\",\"https://lh3.googleusercontent.com/places/ANXAkqHV79svP4zS1YASKkRhAN84DEPG6hiZ79nwG0CvNfPHyO9FHYUIoF_tWcyzcY70Q0ZKOVVZTVZApTF7m3uiWilHvY1pAzfoBSY=s1600-w800\",\"https://lh3.googleusercontent.com/places/ANXAkqE0aXXnukEoyVr7JxGbiZcyQVug5Fhv-xjepgjUY85YpWr1DnqKPCb05kJivccgXDmxD3pSKNkto8Tx8NtHIAE7NPqgnp5hhoQ=s1600-w800\",\"https://lh3.googleusercontent.com/places/ANXAkqEcy8zSM2s5epi1e08FKSujy6o7OJzm5cBuYasUlBHpdw5M08DpDzm2TftDBUlV8wVtFuRa3eiQmtwz8shTlnAoznfBjtWSIgY=s1600-w800\",\"https://lh3.googleusercontent.com/places/ANXAkqH8kw85VGSJS9zvTDcGXIJCfIbTNHEkZV2Ln_24axRAlG9W9W9IPm8NyOhwZQhWapRZyHFdbddIHnLIm1lgiOBDUg0NulQJNZU=s1600-w800\",\"https://lh3.googleusercontent.com/places/ANXAkqHHqlzEaEYuWNj2iHkFfebukfKBviU6z94OLNLp-3Y7oLQRZ1s_Tw53o5p8Jg4GkOpJIfhqsT8a7ALDTUHIz0919pa41si2qBs=s1600-w800\",\"https://lh3.googleusercontent.com/places/ANXAkqElQ_u8MJ-449rOw2lWnuChiLJI3hF84VLEiyAP93rB1RsdcVHn8-slq9CsfGdWs5vef0Z60I-O3mmyxylTiaoFzjPAd4DlTCs=s1600-w800\",\"https://lh3.googleusercontent.com/places/ANXAkqGYZOIQiToHXgYjdDELicETVUp95boPyfvdPkUbYjMr5DzDhb7XgHWIAg02G1lZSnMAFrUJq4QMVXcBI5s-7ryGw51jk6qwkkg=s1600-w800\",\"https://lh3.googleusercontent.com/places/ANXAkqGqyQwvsgKwqSWPTENE-IqfjFCeD5xdkexOU5f5r0EB3SDrOHBCu3aCB6YpnhudbSDqvLgVLCBxZbSapT2WCexGREYP_s_Xh8U=s1600-w800\"],\"dineIn\":true,\"delivery\":false,\"takeout\":false,\"website\":\"http://www.udonshin.com/\",\"formattedPhoneNumber\":null,\"formattedAddress\":\"일본〒151-0053Tokyo,ShibuyaCity,Yoyogi,2-chōme−20−１６相馬ビル１F\",\"reservable\":true,\"servesBreakfast\":false,\"servesLunch\":true,\"servesDinner\":true,\"servesBeer\":true,\"servesWine\":false,\"servesVegetarianFood\":false,\"userRatingsTotal\":3929,\"rating\":4.1,\"review\":[{\"author_name\":\"촉촉한볼따구\",\"rating\":3,\"relative_time_description\":\"3주전\",\"profile_photo_url\":\"https://lh3.googleusercontent.com/a-/ALV-UjVoB_ILlpMn32pQwKbd5R99vuCX8mSb5T4rUzTRBBRts8CQolD9=s128-c0x00000000-cc-rp-mo\",\"text\":\"붓카게우동과자루소바주문\\n직원매우친절함.의외로튀김맛있다\\n우동자체는꽤맛있긴하지만이게3시간을기다릴맛인가?이가격값을하는가?하면별점3점인맛\\n웨이팅이아예없고근처에있다면갈만한집이긴한데절대기다려서먹을맛아님!!!!\\n면발이탱글하다는데개인적으로오사카에서먹은다른우동집이오백만배탱글하고맛있었음\\n손님도로컬거의없고대부분외국인임\\n우연히내가그근처를지나가고있는데웨이팅이없다?할때만가십쇼\",\"translated\":false},{\"author_name\":\"브랜드매니저\",\"rating\":5,\"relative_time_description\":\"1주전\",\"profile_photo_url\":\"https://lh3.googleusercontent.com/a-/ALV-UjWEj0obxB9w9oaVav09hmHjYtqvJYEOeoEYpi4APJIHyt6FWwg=s128-c0x00000000-cc-rp-mo-ba3\",\"text\":\"사람들이엄청나게줄서서먹는곳.손님들에게서는한국어만들린다.대부분80%는자루우동을주문한다.\\n\\n솔직히나는우동면을좋아하지않는다.튀김도자주먹지않는다.내입맛기준으로는80점정도이다.이정도면엄청나게훌륭한점수이다.국물의가스오부시맛은은하니좋다.\\n\\n나는집에인스턴트라면사놓으면유통기한이지나버린다.면을좋아하는사람들에게는초초강추하는식당이다\",\"translated\":false},{\"author_name\":\"Doyoung“도리”Kim\",\"rating\":2,\"relative_time_description\":\"3주전\",\"profile_photo_url\":\"https://lh3.googleusercontent.com/a/ACg8ocI1UA418FRg7ztQh2oSwsoMC3VkZRnERGkZH_TEia57a3HHUg=s128-c0x00000000-cc-rp-mo\",\"text\":\"테이블체크통해예약이가능해서예약하고다녀왔어요.직원이추천해준21번강추메뉴는평소짜게먹는제입맛에도너무짰어요.이게왜강추메뉴인지의문이드는..\\n자루소바는튀김도,면발도맛있었습니다.\\n\\n하지만문제는..사전에예약금으로결제한4,000엔을돌려주지않더군요.음식값에서차감되는것도아니고정말오로지예약을해주는수수료였어요.어이가없어서몇번이나물어봤는데도맞다고하더라고요.결론적으로우동네그릇먹은값을지불한셈이되었고기분이매우상했습니다.예약하고방문하시려했던분들꼭주의하셔요~!\",\"translated\":false},{\"author_name\":\"이현지\",\"rating\":2,\"relative_time_description\":\"2달전\",\"profile_photo_url\":\"https://lh3.googleusercontent.com/a/ACg8ocLmfj8uE92tdK4zB5Oms34Bd_HbjWQmlD4oN_xlVDg--YNIiA=s128-c0x00000000-cc-rp-mo\",\"text\":\"밑에비추하는의견에동감합니다.\\n3시간30분웨이팅이주는기대감에못미쳐서일수도있겠지만그냥그랬고튀김이눅눅하고간도너무짰습니다.무엇보다가격이우동두그릇에4만원..?맛에비해납득이안가는가격인것같아요면발은쫄깃하고맛있어요\\n이번계기로앞으로장시간웨이팅하는가게는피하기로결정했습니다.\",\"translated\":false},{\"author_name\":\"이서연\",\"rating\":5,\"relative_time_description\":\"3달전\",\"profile_photo_url\":\"https://lh3.googleusercontent.com/a-/ALV-UjVH04UuuT0zdDlrtaqN5GFUr9g7CYdx1aeZq1wGf3BTJOQhmlkEUQ=s128-c0x00000000-cc-rp-mo-ba3\",\"text\":\"평일저녁웨이팅은30분정도였으며대기할때직원이선주문을받아회전률이좋습니다\\n\\n여자직원은친절했으며내부는좀많이좁은편이었습니다약10명정도들어갈수있는듯?\\n\\n면발미쳤습니다쫄깃하고먹어본적없는우동면이었습니다\\n근데국물이랑버터어쩌고를시켰는데양념은가쓰오부시맛이나는편이고고기국물이좋았습니다\\n한국인이생각하는우동은아니었지만한번쯤?…경험해볼만맛입니다\\n\\n버터무슨비빔은면발의장점을더잘느낄수있는우동인데사실양념이후추맛…에그냥조금질척거리는식감이었습니다\\n\\n튀김은더시킬걸후회했습니다굿굿존맛\",\"translated\":false}],\"openNow\":false,\"weekdayText\":[\"월요일:오전11:00~오후10:00\",\"화요일:오전11:00~오후10:00\",\"수요일:오전11:00~오후10:00\",\"목요일:오전11:00~오후10:00\",\"금요일:오전11:00~오후10:00\",\"토요일:오전11:00~오후10:00\",\"일요일:오전11:00~오후10:00\"]}";

        mockServer
                .expect(requestTo(DETAILS_URL + DETAILS_FIELDS + "&place_id=" + PLACE_ID + "&key=" + apiKey))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        // when
        Result result = googlePlacesDetailsService.detailsSearch(PLACE_ID);

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
