package dev.backend.wakuwaku.global.infra.google.places.old.textsearch;

import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.photo.GooglePlacesPhotoService;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.request.TextSearchRequest;
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

import java.util.List;

import static dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.request.TextSearchRequest.NEXT_PAGE_URL;
import static dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.request.TextSearchRequest.TEXT_SEARCH_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(value = {GooglePlacesTextSearchService.class, GooglePlacesPhotoService.class})
class GooglePlacesTextSearchServiceTest {
    @Autowired
    private GooglePlacesTextSearchService googlePlacesTextSearchService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Value("${google-places}")
    private String apiKey;

    private static final String NEXT_PAGE_TOKEN = "AUGGfZnrD961k1XVB-29VQuq8yEycKP9dKeuspH1UKEg9ey_f1B_B5Op_CY4jhuO7Paa3C5d5VUFd-3YKm_P_MjdfTi9pDNhJgcbjbr5iDGjL-iXnFA5wUVaMXPp03kSkTha12tYJw55VFwiTonLTmtzmJHZ_HTvbQc3CZuf4D_FHovKnAP3UnWW_URtjo3AEjD1cAOg91DJnirnN3sxyf8mrcnjZcx7WcmSrzIEYAcgMkn9LrQI8Y1ELGnH2Y-x1lCj_leoSvcgNhljeHnWhowB-ocPO4hQJrRg9uI7NyWQmiRISpVp3Q0JuiERG6ghcBoO3jiCFBljD1q4bFVI9Zc0Q42PwntGPQLh0Nq_wzAuBtsaVWZdQQktGNmVdUObWUM39PGPOImjal53AslqZkRHAqSKqrH5zuCd4ps3hA";

    @BeforeEach
    void setUp() {
        RestClient.Builder restClient = RestClient.builder();
        mockServer = MockRestServiceServer.bindTo(restClient).build();
    }

    @DisplayName("최초 요청 테스트")
    @Test
    void textSearch() {
        // given
        String searchWord = "도쿄 근처 맛집";
        String responseByTextSearch = "{\"html_attributions\":[],\"next_page_token\":\"AUGGfZnrD961k1XVB-29VQuq8yEycKP9dKeuspH1UKEg9ey_f1B_B5Op_CY4jhuO7Paa3C5d5VUFd-3YKm_P_MjdfTi9pDNhJgcbjbr5iDGjL-iXnFA5wUVaMXPp03kSkTha12tYJw55VFwiTonLTmtzmJHZ_HTvbQc3CZuf4D_FHovKnAP3UnWW_URtjo3AEjD1cAOg91DJnirnN3sxyf8mrcnjZcx7WcmSrzIEYAcgMkn9LrQI8Y1ELGnH2Y-x1lCj_leoSvcgNhljeHnWhowB-ocPO4hQJrRg9uI7NyWQmiRISpVp3Q0JuiERG6ghcBoO3jiCFBljD1q4bFVI9Zc0Q42PwntGPQLh0Nq_wzAuBtsaVWZdQQktGNmVdUObWUM39PGPOImjal53AslqZkRHAqSKqrH5zuCd4ps3hA\",\"results\":[{\"placeId\":\"ChIJAQCl79GMGGARZheneHqgIUs\",\"name\":\"우동신\",\"rating\":4.1,\"userRatingsTotal\":3929,\"lat\":35.6864899,\"lng\":139.6969979,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqFVKvKKJF9PvO5CRH_QqzNwhk3fVw7fet05L49Zt2OFwMvPzX1wwC2qdXs3x2zO4x08fFsJojhgvga3GYWbb16PRO471kMleWY=s1600-w800\"]},{\"placeId\":\"ChIJubHyMNaMGGARFRSq4A7C3Uo\",\"name\":\"사누키우동타이시도쿄멘츠단\",\"rating\":3.8,\"userRatingsTotal\":1294,\"lat\":35.6947874,\"lng\":139.6981912,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqHjraz-otXJyyHP264qDMocOjvNTwzxN5tO-BgJiGAhfNArGipvyOYf654EMewDw3GJ8przgQb0TpEAQ2mDpaq5a_2fmov2Bm4=s1600-w800\"]},{\"placeId\":\"ChIJLZrOfdeMGGARdcjAP55yY00\",\"name\":\"테판베이비\",\"rating\":4.5,\"userRatingsTotal\":1213,\"lat\":35.6940479,\"lng\":139.7012389,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqHCkHvUAIozMxYxg28csEkq-nfYpV5-LDKx3ZTwKNkpXsmePWIAtHnOacYeA4S0OEdy4Zmz23F-EG48NofuHB6VGQK03nIOqqk=s1600-w800\"]},{\"placeId\":\"ChIJ4fL0W9GMGGARnAmRzZR-LGo\",\"name\":\"아부라소바니시신주쿠구미\",\"rating\":4.2,\"userRatingsTotal\":1002,\"lat\":35.6894063,\"lng\":139.696655,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqFWF_cp_ud2RFAmWXqiPr8-pq1N37EoquQxt1Bv_J23lapLzoFblnSG5MTH4xZrEp2r385rlnQ126o8AgHfrrgP1GbjpENMgbg=s1600-w800\"]},{\"placeId\":\"ChIJEwlemnqLGGARhwMuRpwpkBI\",\"name\":\"곤파치니시아자부\",\"rating\":4.1,\"userRatingsTotal\":4675,\"lat\":35.6601742,\"lng\":139.7235797,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqEDT0-_kdSfmuA0DqlDwf5ireQuPYENoaEdfz3kF0XiTYsQ7oZvBPIysiMiCSYhX4avWM-0R42mIQYXfhUNqwadoxPFoTCJtRY=s1600-w800\"]},{\"placeId\":\"ChIJ5xSueNeMGGARtHP0Aq9QWHM\",\"name\":\"야키니쿠돈돈신주쿠가부키초점\",\"rating\":4.1,\"userRatingsTotal\":663,\"lat\":35.6942515,\"lng\":139.7010514,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqH0MF-hGFl_PyNUnW13hcen7IkY6z63wyP6OD947apvB3rGDSRlVykVMSvz-R1_i2EDwghNCMXPaZgEM1fjUYi2BGHbneJh9kQ=s1600-w800\"]},{\"placeId\":\"ChIJMQelNFuLGGARA0crD67eTS0\",\"name\":\"日本焼肉別停銀座\",\"rating\":0,\"userRatingsTotal\":0,\"lat\":35.6729979,\"lng\":139.7657472,\"photoUrl\":[]},{\"placeId\":\"ChIJYyEsRdGMGGARH6RVyBKbjjA\",\"name\":\"톤친칸\",\"rating\":4,\"userRatingsTotal\":2620,\"lat\":35.6895624,\"lng\":139.6967792,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqG53lVBKF4jpdFsgnsk9qBfXgh9taEjTazLw_9rwC9ZnrpMQbddgQiSfIMse0ouSMBezqQFEiXudyrL4d4YiYYkF_EEIdAFvqY=s1600-w800\"]},{\"placeId\":\"ChIJU-JXDf2LGGARHsancfqkHyE\",\"name\":\"츠지한\",\"rating\":4.4,\"userRatingsTotal\":3749,\"lat\":35.6807167,\"lng\":139.7715771,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqEMiIJqn75SPdhPj5S9mr4m0mFQaCjYl_3Lxf-HGQk4Cig44XIH7zKsltNq-X65GVkAkwVa7YvsTg7y62kwbzVrc6N1RzaLWC4=s1600-w800\"]},{\"placeId\":\"ChIJl6GzntyMGGARoXqNccukAtI\",\"name\":\"돈까츠와코타쿠미안이세탄신주쿠점\",\"rating\":4.3,\"userRatingsTotal\":546,\"lat\":35.6913226,\"lng\":139.7043261,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqH7uzwE3Vp9G3wNaZftvOMiNVuKjBTAbTwXHvHfedkmJ2oQhh1aXYzcF_eJ3Sk5AMbI69iI1t5aNF3pQjMALeGuqM-o1OFkOAg=s1600-w800\"]},{\"placeId\":\"ChIJp1Kx1dmMGGARvbZ_YYRx0vQ\",\"name\":\"이치란신주쿠카부키초점\",\"rating\":4.3,\"userRatingsTotal\":3065,\"lat\":35.6943949,\"lng\":139.7015417,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqHkraIwKrwMq7Xn-qrIwiApmK7xJtNqCve_AcwKN7w1wuEurjB9rU3DUh4sgFB305R6cHs7n2eCVM-AHJio1lqVQysH9TO1QgE=s1600-w800\"]},{\"placeId\":\"ChIJAQCxiNGMGGARKMm58AS8q0s\",\"name\":\"카레우동센키치신주쿠고슈가도점\",\"rating\":3.9,\"userRatingsTotal\":746,\"lat\":35.6873083,\"lng\":139.6965338,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqFesOfZmt1u2g5EZTO1RAw_AW8lDY7talK1ZpWLX7JMt2tMbi6UidZiRhuI0rpiK-erIpqLcN8Xb7pvDLPM1PJGR4xr1tRk718=s1600-w800\"]},{\"placeId\":\"ChIJ2ShDeFmLGGARoKtg8A32N6M\",\"name\":\"아부라소바시부야구미\",\"rating\":4.2,\"userRatingsTotal\":879,\"lat\":35.6570227,\"lng\":139.7040385,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqENMrp46jVl-9_OIZXE48ne_DYwYVLu3dHXRhRsCBhEhIyW-p5pUhnTsCYiagmpEHWltEtWlTjy-zLrSTZ0q7IlQSIGTSQCaiw=s1600-w800\"]},{\"placeId\":\"ChIJF6fcidiMGGARuxa5dZAC_CE\",\"name\":\"신주쿠류엔\",\"rating\":4.5,\"userRatingsTotal\":289,\"lat\":35.6970133,\"lng\":139.7038988,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqFgsfq1RXfXR7kP6xy5sDJTuHLq9-LiJyQl513W_CmTXGrj8x4FfollQG8K19aM4zPyiSIHsVsR9TO0pOoslNa6_Lu5im-kaac=s1600-w800\"]},{\"placeId\":\"ChIJAQCEc9uMGGARwBVWYa3flCw\",\"name\":\"타츠키치신주쿠본점\",\"rating\":4.6,\"userRatingsTotal\":510,\"lat\":35.6911577,\"lng\":139.7053976,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqGWmVzT7Wd1ziL7xuajezLw_dsFBAqAbF2J-g3bn_Gnexg3PEf6XHQ-O3OS_DGWKDi5ztJsMDZeMgpaxES06Bx1F6Vs0jHCFFA=s1600-w800\"]},{\"placeId\":\"ChIJfQVxq6mMGGARK0zIBxe0-Dw\",\"name\":\"이치란시부야스페인자카점\",\"rating\":4.4,\"userRatingsTotal\":2216,\"lat\":35.6609856,\"lng\":139.6987137,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqGFYG7urfanbcQKQd_0-dgiHDCPOky5GLeHNNi4EwsLta1S7Px4h7vFqr3PmKKAk_7w2lZclsd7xT7oKFEyeS1bWv57AK6d_DI=s1600-w800\"]},{\"placeId\":\"ChIJvzQfitmMGGARvBaUxrErJfc\",\"name\":\"오타코신주쿠점\",\"rating\":3.9,\"userRatingsTotal\":628,\"lat\":35.6928165,\"lng\":139.702869,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqHWr3N-uwzRBEYOkzS-pXiThBOt0OqQTcA7ZRCKtjogj6CEkxhKx6M4bgLXXMfXAiY_74H8xpx7k4E6grtHTpdu23l7Q-maOZE=s1600-w800\"]},{\"placeId\":\"ChIJ1URZRaqNGGAREW96qWaaWPo\",\"name\":\"야키니쿠니쿠노네\",\"rating\":4.8,\"userRatingsTotal\":563,\"lat\":35.6958268,\"lng\":139.7018815,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqF4xBkm8jKNwpPkwlRt4IakfnuvoizVY7GxPXswBJb5nf9GI9U5l95kuNEgKE8PpYqcxjK0cvQoXHtEs-LVqsBRagkeKU2jFmc=s1600-w570\"]},{\"placeId\":\"ChIJST2SJqqMGGAR_XMdpOnU4F4\",\"name\":\"도겐(예약추천)\",\"rating\":4.2,\"userRatingsTotal\":250,\"lat\":35.6595013,\"lng\":139.6959072,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqGs93dbV7T8d4K5CbsI7G0wMiUoBe3pFmBx4CPUcc6iaXzbWf7xhbkUbKWMcs7EYTDtJqsQROi5sy0qCNFlvKC3meai0jRhNUQ=s1600-w800\"]},{\"placeId\":\"ChIJoTcat9SMGGARXdBrwnARzuE\",\"name\":\"톤카츠이세도청점\",\"rating\":4.2,\"userRatingsTotal\":119,\"lat\":35.6895524,\"lng\":139.6931209,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqFW77wVNKbdnhtQQO4zlaK65HWnVwS8PDCqJzwAHX5bnMDxTZ3pIXKVb0XN54L5ZQT3dxc_LAEqmsiXSrNxLg_sZwcY3AqvDMs=s1600-w800\"]}],\"status\":\"OK\"}";

        mockServer
                .expect(requestTo(textSearchURI(searchWord)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseByTextSearch, MediaType.APPLICATION_JSON));

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
        String responseByNextPageToken = "{\"html_attributions\":[],\"next_page_token\":\"AUGGfZnm8OWyAidaQbPKWnqxCUz74nSKa5ISMN_TTesg7HZM72a0NVqKRH1-REbLghrO-bnmbWW0QBJgqmObplWOixTJF9uDQlYJPsvo5S1t49keJcYNBokPf4rk8gAj4Bz6huX6lSl4LwzZK8wb5O0AoBjysDSskQN0vJCr6OhnTfelkrpeuh9R5vti-hVPBXTG-xKBzBOQtZKpUhXXJlGPdCSyy6DiwMcR1603gFCHZXwTNNTc-VOtnt9MY8OnMfWhCnUqUvjbPXkOl-GxS3jct0UihOUMN3jxtI2om3m1OXm2JyT5WxjCCYQNlKjk79coqaYwgCS5yqxcQ8sVOixCwolpkuPhsE04E6uHmxUdZQvpqx7Gh7C7olmAVY6zve9Un65REYDm8BHU1Plo0tiYcGMXnQPu0NNp7lHdl1vGmmmDPAbfLymF-Ev8VDo0_f3f90h77SEWWYgkJbmUa92jbFZpLO5jSXHYmXWmQrEgiNSatLGES6F-1jztP5Xs7jLe19cbSZdODX-XbhdyHw0pUXLefq3yft4c4Sl6NoAyaLIS_d6gyX5D5t97p11w1UXiTZzCkQ1w0G-XCETSPKB3Wv1MMcqanG3wNlnCZiALxvlYn7ns2okTSb1-Bmo\",\"results\":[{\"placeId\":\"ChIJEf5lGaiMGGARB9Yc49Xisak\",\"name\":\"카츠미도리 회전초밥 시부야점\",\"rating\":4.2,\"userRatingsTotal\":3144,\"lat\":35.6602148,\"lng\":139.7005026,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqFVKvKKJF9PvO5CRH_QqzNwhk3fVw7fet05L49Zt2OFwMvPzX1wwC2qdXs3x2zO4x08fFsJojhgvga3GYWbb16PRO471kMleWY=s1600-w800\"]},{\"placeId\":\"ChIJu7r1bNGMGGAR-C6O8xERBNw\",\"name\":\"히마와리 스시 신도심점\",\"rating\":4.2,\"userRatingsTotal\":1621,\"lat\":35.6889788,\"lng\":139.6975038,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqHjraz-otXJyyHP264qDMocOjvNTwzxN5tO-BgJiGAhfNArGipvyOYf654EMewDw3GJ8przgQb0TpEAQ2mDpaq5a_2fmov2Bm4=s1600-w800\"]},{\"placeId\":\"ChIJCZkGEqyMGGAR72gGFgeNxEE\",\"name\":\"카츠동야 즈이초\",\"rating\":4.4,\"userRatingsTotal\":1551,\"lat\":35.662703,\"lng\":139.6953601,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqHCkHvUAIozMxYxg28csEkq-nfYpV5-LDKx3ZTwKNkpXsmePWIAtHnOacYeA4S0OEdy4Zmz23F-EG48NofuHB6VGQK03nIOqqk=s1600-w800\"]},{\"placeId\":\"ChIJGSLbtaqMGGARlDRp1kTJ1ds\",\"name\":\"이치노야\",\"rating\":4.1,\"userRatingsTotal\":381,\"lat\":35.6570615,\"lng\":139.6914132,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqFWF_cp_ud2RFAmWXqiPr8-pq1N37EoquQxt1Bv_J23lapLzoFblnSG5MTH4xZrEp2r385rlnQ126o8AgHfrrgP1GbjpENMgbg=s1600-w800\"]},{\"placeId\":\"ChIJRUBoyFeLGGARgpJFvL-gaXg\",\"name\":\"니이가타 카츠동 타레카츠 시부야점\",\"rating\":4.4,\"userRatingsTotal\":1045,\"lat\":35.6580557,\"lng\":139.6992802,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqEDT0-_kdSfmuA0DqlDwf5ireQuPYENoaEdfz3kF0XiTYsQ7oZvBPIysiMiCSYhX4avWM-0R42mIQYXfhUNqwadoxPFoTCJtRY=s1600-w800\"]},{\"placeId\":\"ChIJL5gxE9CMGGAR0Vtyrq_cFio\",\"name\":\"스시 도쿄 텐\",\"rating\":3.9,\"userRatingsTotal\":811,\"lat\":35.6884554,\"lng\":139.7005282,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqH0MF-hGFl_PyNUnW13hcen7IkY6z63wyP6OD947apvB3rGDSRlVykVMSvz-R1_i2EDwghNCMXPaZgEM1fjUYi2BGHbneJh9kQ=s1600-w800\"]},{\"placeId\":\"ChIJ_8_H76iMGGARzxoa9Y4Itk4\",\"name\":\"네기시 고엔도리점\",\"rating\":4.1,\"userRatingsTotal\":479,\"lat\":35.6622332,\"lng\":139.6995336,\"photoUrl\":[]},{\"placeId\":\"ChIJs9lswi3zGGAR_1RZoj4MS3s\",\"name\":\"Jojoen Tokyo Opera City\",\"rating\":4.4,\"userRatingsTotal\":684,\"lat\":35.6831007,\"lng\":139.6868049,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqG53lVBKF4jpdFsgnsk9qBfXgh9taEjTazLw_9rwC9ZnrpMQbddgQiSfIMse0ouSMBezqQFEiXudyrL4d4YiYYkF_EEIdAFvqY=s1600-w800\"]},{\"placeId\":\"ChIJb2dpcXiLGGARm_I0-dnFSsA\",\"name\":\"이마카츠 롯폰기 본점\",\"rating\":4.4,\"userRatingsTotal\":1235,\"lat\":35.6644501,\"lng\":139.7315786,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqEMiIJqn75SPdhPj5S9mr4m0mFQaCjYl_3Lxf-HGQk4Cig44XIH7zKsltNq-X65GVkAkwVa7YvsTg7y62kwbzVrc6N1RzaLWC4=s1600-w800\"]},{\"placeId\":\"ChIJsfDxPXTzGGARY0AIFJtTwFQ\",\"name\":\"스시노미도리 본점\",\"rating\":4.2,\"userRatingsTotal\":1912,\"lat\":35.655623,\"lng\":139.6541138,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqH7uzwE3Vp9G3wNaZftvOMiNVuKjBTAbTwXHvHfedkmJ2oQhh1aXYzcF_eJ3Sk5AMbI69iI1t5aNF3pQjMALeGuqM-o1OFkOAg=s1600-w800\"]},{\"placeId\":\"ChIJCSlcwsKMGGARBi3AfgAp8R4\",\"name\":\"CoCo Ichibanya Tokyo Metro Shinjuku Gyoenmae station shop\",\"rating\":4.2,\"userRatingsTotal\":433,\"lat\":35.6899313,\"lng\":139.7098941,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqHkraIwKrwMq7Xn-qrIwiApmK7xJtNqCve_AcwKN7w1wuEurjB9rU3DUh4sgFB305R6cHs7n2eCVM-AHJio1lqVQysH9TO1QgE=s1600-w800\"]},{\"placeId\":\"ChIJOWucdKiMGGARbppa4b4CKA8\",\"name\":\"이치란 시부야점\",\"rating\":4.4,\"userRatingsTotal\":3974,\"lat\":35.6611185,\"lng\":139.7009846,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqFesOfZmt1u2g5EZTO1RAw_AW8lDY7talK1ZpWLX7JMt2tMbi6UidZiRhuI0rpiK-erIpqLcN8Xb7pvDLPM1PJGR4xr1tRk718=s1600-w800\"]},{\"placeId\":\"ChIJVZkxi-iLGGART2QFR7yDqIg\",\"name\":\"돈카츠 아오키 긴자점\",\"rating\":4.4,\"userRatingsTotal\":1070,\"lat\":35.6682225,\"lng\":139.7614816,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqENMrp46jVl-9_OIZXE48ne_DYwYVLu3dHXRhRsCBhEhIyW-p5pUhnTsCYiagmpEHWltEtWlTjy-zLrSTZ0q7IlQSIGTSQCaiw=s1600-w800\"]},{\"placeId\":\"ChIJ5f7A0SmNGGARtLzoA9hzuYk\",\"name\":\"타츠노야 츠케멘(츠케멘)\",\"rating\":4.4,\"userRatingsTotal\":3105,\"lat\":35.6960593,\"lng\":139.6983577,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqFgsfq1RXfXR7kP6xy5sDJTuHLq9-LiJyQl513W_CmTXGrj8x4FfollQG8K19aM4zPyiSIHsVsR9TO0pOoslNa6_Lu5im-kaac=s1600-w800\"]},{\"placeId\":\"ChIJxf_AydGMGGARc8HXwbN2tks\",\"name\":\"아카사카 우마야 신주쿠\",\"rating\":4.1,\"userRatingsTotal\":635,\"lat\":35.6877042,\"lng\":139.698451,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqGWmVzT7Wd1ziL7xuajezLw_dsFBAqAbF2J-g3bn_Gnexg3PEf6XHQ-O3OS_DGWKDi5ztJsMDZeMgpaxES06Bx1F6Vs0jHCFFA=s1600-w800\"]},{\"placeId\":\"ChIJF3LEqNeMGGARJ-mBd1DgSgA\",\"name\":\"모토무라 규카츠 니시신주쿠점\",\"rating\":4.7,\"userRatingsTotal\":2192,\"lat\":35.69388,\"lng\":139.6992947,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqGFYG7urfanbcQKQd_0-dgiHDCPOky5GLeHNNi4EwsLta1S7Px4h7vFqr3PmKKAk_7w2lZclsd7xT7oKFEyeS1bWv57AK6d_DI=s1600-w800\"]},{\"placeId\":\"ChIJ5-iCYkGNGGARHebR8YabSAM\",\"name\":\"돈카츠 츠키우마\",\"rating\":4.3,\"userRatingsTotal\":337,\"lat\":35.6629193,\"lng\":139.6944599,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqHWr3N-uwzRBEYOkzS-pXiThBOt0OqQTcA7ZRCKtjogj6CEkxhKx6M4bgLXXMfXAiY_74H8xpx7k4E6grtHTpdu23l7Q-maOZE=s1600-w800\"]},{\"placeId\":\"ChIJu9W4zVeLGGARXn0xCjGINF8\",\"name\":\"스시노미도리 시부야점\",\"rating\":4.3,\"userRatingsTotal\":3378,\"lat\":35.6583453,\"lng\":139.6989875,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqF4xBkm8jKNwpPkwlRt4IakfnuvoizVY7GxPXswBJb5nf9GI9U5l95kuNEgKE8PpYqcxjK0cvQoXHtEs-LVqsBRagkeKU2jFmc=s1600-w570\"]},{\"placeId\":\"ChIJL9StcqmMGGARiaWOh_T89FI\",\"name\":\"나베조 시부야센터가이점\",\"rating\":4.8,\"userRatingsTotal\":2331,\"lat\":35.6613618,\"lng\":139.6976195,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqGs93dbV7T8d4K5CbsI7G0wMiUoBe3pFmBx4CPUcc6iaXzbWf7xhbkUbKWMcs7EYTDtJqsQROi5sy0qCNFlvKC3meai0jRhNUQ=s1600-w800\"]},{\"placeId\":\"ChIJdQKdWtGMGGARWkwkNI6Xf7o\",\"name\":\"산고쿠이치 니시구치점\",\"rating\":3.9,\"userRatingsTotal\":592,\"lat\":35.6899708,\"lng\":139.6966017,\"photoUrl\":[\"https://lh3.googleusercontent.com/places/ANXAkqFW77wVNKbdnhtQQO4zlaK65HWnVwS8PDCqJzwAHX5bnMDxTZ3pIXKVb0XN54L5ZQT3dxc_LAEqmsiXSrNxLg_sZwcY3AqvDMs=s1600-w800\"]}],\"status\":\"OK\"}";

        mockServer.expect(requestTo(NEXT_PAGE_URL + NEXT_PAGE_TOKEN + "&key=" + apiKey))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseByNextPageToken, MediaType.APPLICATION_JSON));

        // when
        List<Result> results = googlePlacesTextSearchService.textSearchByNextPageToken(NEXT_PAGE_TOKEN);

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

    private String textSearchURI(String searchWord) {
        TextSearchRequest textSearchRequest = new TextSearchRequest(searchWord);

        String newTextQuery = textSearchRequest.getTextQuery().replace(" ", "%20");

        return TEXT_SEARCH_URL + newTextQuery + "&key=" + apiKey;
    }
}