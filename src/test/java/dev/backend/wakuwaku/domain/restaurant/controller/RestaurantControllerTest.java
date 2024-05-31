package dev.backend.wakuwaku.domain.restaurant.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.service.RestaurantService;
import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = RestaurantController.class)
class RestaurantControllerTest {
    @MockBean
    private RestaurantService restaurantService;

    private MockMvc mockMvc;

    private static final String BASE_URL = "/wakuwaku/v1/restaurant";

    private static final String PLACE_ID = "ChIJAQCl79GMGGARZheneHqgIUs";

    private static final String SEARCH_WORD = "도쿄 근처 맛집";

    private static final String NAME = "우동신";

    private final List<PlacePhoto> photos = new ArrayList<>();

    private final List<PlaceReview> placeReviews = new ArrayList<>();

    private Result result;

    @BeforeEach
    void setUp (final WebApplicationContext context,
               final RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();

        LatLngLiteral latLngLiteral = new LatLngLiteral(35.6864899, 139.6969979);

        PlacePhoto placePhoto = new PlacePhoto("https://lh3.googleusercontent.com/places/ANXAkqFVKvKKJF9PvO5CRH_QqzNwhk3fVw7fet05L49Zt2OFwMvPzX1wwC2qdXs3x2zO4x08fFsJojhgvga3GYWbb16PRO471kMleWY=s1600-w800");
        photos.add(placePhoto);

        Geometry geometry = new Geometry(latLngLiteral);

        List<String> weekdayText = new ArrayList<>();
        weekdayText.add("월요일:오전11:00~오후10:00,화요일:오전11:00~오후10:00,수요일:오전11:00~오후10:00,목요일:오전11:00~오후10:00,금요일:오전11:00~오후10:00,토요일:오전11:00~오후10:00,일요일:오전11:00~오후10:00");

        PlaceOpeningHours placeOpeningHours = new PlaceOpeningHours(true, weekdayText);

        PlaceEditorialSummary placeEditorialSummary = new PlaceEditorialSummary("다양한 반주와 함께 바삭한 튀김과 수제 우동을 맛 볼 수 있는 아담한 식당입니다.");

        PlaceReview placeReview = new PlaceReview("촉촉한볼따구", 3, "3주 전", "https://lh3.googleusercontent.com/a-/ALV-UjVoB_ILlpMn32pQwKbd5R99vuCX8mSb5T4rUzTRBBRts8CQolD9=s128-c0x00000000-cc-rp-mo", "붓카게 우동과 자루소바 주문\\n직원 매우 친절함.의외로 튀김 맛있다\\n우동 자체는 꽤 맛있긴 하지만 이게 3시간을 기다릴 맛인가?이 가격 값을 하는가? 하면 별점 3점인 맛\\n웨이팅이 아예 없고 근처에 있다면 갈만한 집이긴 한데 절대 기다려서 먹을 맛 아님!!!!\\n면발이 탱글하다는데 개인적으로 오사카에서 먹은 다른 우동 집이 오백만배 탱글하고 맛있었음\\n손님도 로컬 거의 없고 대부분 외국인임\\n우연히 내가 그 근처를 지나가고 있는데 웨이팅이 없다? 할 때만 가십쇼", false);
        placeReviews.add(placeReview);

        result = Result.builder()
                .place_id(PLACE_ID)
                .name(NAME)
                .rating(4.1)
                .user_ratings_total(3929)
                .geometry(geometry)
                .photos(photos)
                .current_opening_hours(placeOpeningHours)
                .delivery(true)
                .dine_in(true)
                .reservable(true)
                .serves_beer(true)
                .serves_dinner(true)
                .serves_lunch(true)
                .takeout(true)
                .serves_breakfast(true)
                .serves_wine(true)
                .serves_vegetarianFood(true)
                .editorial_summary(placeEditorialSummary)
                .formatted_address("일본〒151-0053Tokyo,ShibuyaCity,Yoyogi,2-chōme−20−１６相馬ビル１F")
                .formatted_phone_number(null)
                .website(null)
                .reviews(placeReviews).build();
    }

    @Test
    @DisplayName("Simple Info 조회. 즉, 사용자가 검색하여 얻는 데이터를 반환")
    void getSimpleInfoRestaurants() throws Exception {
        // given
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(result));

        given(restaurantService.getSimpleRestaurants(anyString())).willReturn(restaurants);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders
                    .get(BASE_URL)
                    .queryParam("search", SEARCH_WORD)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].placeId").exists())
                .andExpect(jsonPath("$[*].name").exists())
                .andExpect(jsonPath("$[*].rating").exists())
                .andExpect(jsonPath("$[*].userRatingsTotal").exists())
                .andExpect(jsonPath("$[*].lat").exists())
                .andExpect(jsonPath("$[*].lng").exists())
                .andExpect(jsonPath("$[*].photoUrl").exists())
                .andDo(MockMvcRestDocumentationWrapper.document("get simple info",
                        resource(ResourceSnippetParameters.builder()
                                .tag("Get Simple Info")
                                .description("Get Simple Info By Search Word")
                                .queryParameters(
                                        parameterWithName("search").description("검색어")
                                )
                                .responseFields(
                                        fieldWithPath("[].placeId").type(JsonFieldType.STRING).description("Details Info 에 활용할 식당의 Place Id"),
                                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("식당 이름"),
                                        fieldWithPath("[].rating").type(JsonFieldType.NUMBER).optional().description("식당 별점"),
                                        fieldWithPath("[].userRatingsTotal").type(JsonFieldType.NUMBER).optional().description("식당 총 리뷰 수"),
                                        fieldWithPath("[].lat").type(JsonFieldType.NUMBER).description("위도"),
                                        fieldWithPath("[].lng").type(JsonFieldType.NUMBER).description("경도"),
                                        fieldWithPath("[].photoUrl").type(JsonFieldType.ARRAY).optional().description("식당 대표 사진 URL"))
                                .build()
                        )
                       )
                );

        then(restaurantService).should().getSimpleRestaurants(anyString());
    }

    @Test
    @DisplayName("Details Info 조회. 즉, 사용자가 검색하여 얻는 데이터 중 하나의 데이터의 세부적인 정보를 반환")
    void getDetailsInfoRestaurant() throws Exception {
        // given
        given(restaurantService.getDetailsRestaurant(PLACE_ID)).willReturn(result);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders
                    .get(BASE_URL + "/{placeId}" + "/details", PLACE_ID)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.editorialSummary").exists())
                .andExpect(jsonPath("$..photos").exists())
                .andExpect(jsonPath("$.dineIn").exists())
                .andExpect(jsonPath("$.delivery").exists())
                .andExpect(jsonPath("$.takeout").exists())
                .andExpect(jsonPath("$.website").isEmpty())
                .andExpect(jsonPath("$.formattedPhoneNumber").isEmpty())
                .andExpect(jsonPath("$.formattedAddress").exists())
                .andExpect(jsonPath("$.reservable").exists())
                .andExpect(jsonPath("$.servesBreakfast").exists())
                .andExpect(jsonPath("$.servesLunch").exists())
                .andExpect(jsonPath("$.servesDinner").exists())
                .andExpect(jsonPath("$.servesBeer").exists())
                .andExpect(jsonPath("$.servesWine").exists())
                .andExpect(jsonPath("$.servesVegetarianFood").exists())
                .andExpect(jsonPath("$.userRatingsTotal").exists())
                .andExpect(jsonPath("$.rating").exists())
                .andExpect(jsonPath("$.review").exists())
                .andExpect(jsonPath("$.openNow").exists())
                .andExpect(jsonPath("$.weekdayText").exists())
                .andDo(MockMvcRestDocumentationWrapper.document("get details info",
                        resource(ResourceSnippetParameters.builder()
                                    .tag("Get Details Info")
                                    .description("Get Details Info By PlaceId")
                                    .pathParameters(
                                    parameterWithName("placeId").description("Details Info를 얻고 싶은 식당의 Place Id")
                                    )
                                    .responseFields(
                                            fieldWithPath("name").type(JsonFieldType.STRING).description("식당 이름"),
                                            fieldWithPath("editorialSummary").type(JsonFieldType.STRING).optional().description("식당에 대한 간단한 소개글"),
                                            fieldWithPath("photos").type(JsonFieldType.ARRAY).optional().description("식당 사진 (최대 10개)"),
                                            fieldWithPath("dineIn").type(JsonFieldType.BOOLEAN).optional().description("매장 내 식사 가능 여부"),
                                            fieldWithPath("delivery").type(JsonFieldType.BOOLEAN).optional().description("배달 가능 여부"),
                                            fieldWithPath("takeout").type(JsonFieldType.BOOLEAN).optional().description("포장 가능 여부"),
                                            fieldWithPath("website").type(JsonFieldType.STRING).optional().description("해당 식당의 웹 사이트 주소"),
                                            fieldWithPath("formattedPhoneNumber").type(JsonFieldType.STRING).optional().description("식당 전화번호"),
                                            fieldWithPath("formattedAddress").type(JsonFieldType.STRING).description("식당 주소"),
                                            fieldWithPath("reservable").type(JsonFieldType.BOOLEAN).optional().description("예약 가능 여부"),
                                            fieldWithPath("servesBreakfast").type(JsonFieldType.BOOLEAN).optional().description("아침 식사 가능 여부"),
                                            fieldWithPath("servesLunch").type(JsonFieldType.BOOLEAN).optional().description("점심 식사 가능 여부"),
                                            fieldWithPath("servesDinner").type(JsonFieldType.BOOLEAN).optional().description("저녁 식사 가능 여부"),
                                            fieldWithPath("servesBeer").type(JsonFieldType.BOOLEAN).optional().description("맥주 판매 여부"),
                                            fieldWithPath("servesWine").type(JsonFieldType.BOOLEAN).optional().description("와인 판매 여부"),
                                            fieldWithPath("servesVegetarianFood").type(JsonFieldType.BOOLEAN).optional().description("채식주의자를 위한 메뉴 유무"),
                                            fieldWithPath("userRatingsTotal").type(JsonFieldType.NUMBER).optional().description("식당의 총 리뷰 수"),
                                            fieldWithPath("rating").type(JsonFieldType.NUMBER).optional().description("식당 별점"),
                                            fieldWithPath("review[].author_name").type(JsonFieldType.STRING).description("리뷰 작성자 이름"),
                                            fieldWithPath("review[].rating").type(JsonFieldType.NUMBER).description("리뷰 작성자의 별점"),
                                            fieldWithPath("review[].relative_time_description").type(JsonFieldType.STRING).description("현 시간으로부터 식당 리뷰 작성한 날짜"),
                                            fieldWithPath("review[].profile_photo_url").type(JsonFieldType.STRING).description("리뷰 작성자의 프로필 사진 URL"),
                                            fieldWithPath("review[].text").type(JsonFieldType.STRING).description("리뷰 내용"),
                                            fieldWithPath("review[].translated").type(JsonFieldType.BOOLEAN).description("리뷰가 구글 번역기에 의해 번역이 된 것인지 여부"),
                                            fieldWithPath("openNow").type(JsonFieldType.BOOLEAN).optional().description("검색한 시점에서의 식당 오픈 여부"),
                                            fieldWithPath("weekdayText").type(JsonFieldType.ARRAY).optional().description("요일 별 식당 운영 시간")
                                    )
                                    .build()
                        )
                       )
                );

        then(restaurantService).should().getDetailsRestaurant(PLACE_ID);
    }
}