package dev.backend.wakuwaku.domain.like.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.google.gson.Gson;
import dev.backend.wakuwaku.domain.likes.controller.LikesController;
import dev.backend.wakuwaku.domain.likes.dto.LikesStatusType;
import dev.backend.wakuwaku.domain.likes.dto.request.LikesRestaurantRequest;
import dev.backend.wakuwaku.domain.likes.dto.request.PushLikesRequest;
import dev.backend.wakuwaku.domain.likes.dto.response.AllLikesResponse;
import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.likes.service.LikesService;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = LikesController.class)
class LikesControllerTest {
    @MockBean
    private LikesService likesService;

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/wakuwaku/v1/likes";

    private Member testMember;

    private Restaurant testRestaurant;

    private Likes testLikes;

    private static final Long MEMBER_ID = 1L;

    private static final Long LIKES_ID = 1L;

    private static final String PLACE_ID = "test-placeId";

    private static final String NAME = "test-name";

    private static final double LAT = 99.99;

    private static final double LNG = 11.11;

    private static final String PHOTO_URL = "test-photo-url";

    private static final int USER_RATINGS_TOTAL = 999;

    private static final double RATING = 4.35;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                      .apply(documentationConfiguration(restDocumentation))
                                      .alwaysDo(MockMvcResultHandlers.print())
                                      .addFilters(new CharacterEncodingFilter("UTF-8", true))
                                      .build();

        // Mock 객체 설정
        testMember = Member.builder()
                           .email("test@test.com")
                           .build();

        testRestaurant = new Restaurant();

        testLikes = Likes.builder()
                         .member(testMember)
                         .restaurant(testRestaurant)
                         .likesStatus(LikesStatusType.Y)
                         .build();
    }

    @DisplayName("찜 목록 조회")
    @Test
    void getLikes() throws Exception {
        // given
        Likes likes1 = createLikes(createMember(1), createRestaurant(1), LikesStatusType.Y);
        ReflectionTestUtils.setField(likes1, "id", 1L);

        Likes likes2 = createLikes(createMember(1), createRestaurant(2), LikesStatusType.N);
        ReflectionTestUtils.setField(likes2, "id", 2L);

        Likes likes3 = createLikes(createMember(1), createRestaurant(3), LikesStatusType.Y);
        ReflectionTestUtils.setField(likes3, "id", 3L);

        Likes likes4 = createLikes(createMember(1), createRestaurant(4), LikesStatusType.N);
        ReflectionTestUtils.setField(likes4, "id", 4L);

        Likes likes5 = createLikes(createMember(1), createRestaurant(5), LikesStatusType.Y);
        ReflectionTestUtils.setField(likes5, "id", 5L);

        Likes likes6 = createLikes(createMember(1), createRestaurant(6), LikesStatusType.N);
        ReflectionTestUtils.setField(likes6, "id", 6L);

        Likes likes7 = createLikes(createMember(1), createRestaurant(7), LikesStatusType.Y);
        ReflectionTestUtils.setField(likes7, "id", 7L);

        List<Likes> likesList = List.of(likes1, likes2, likes3, likes4, likes5, likes6, likes7);

        AllLikesResponse allLikesResponse = AllLikesResponse.builder()
                                                            .likesList(likesList)
                                                            .totalPages(10)
                                                            .build();

        Pageable pageable = PageRequest.of(0, 10);

        given(likesService.getPaginatedLikesList(MEMBER_ID, pageable)).willReturn(allLikesResponse);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get(BASE_URL + "/{memberId}", MEMBER_ID)
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.likesRestaurants[*].likesId").exists())
                .andExpect(jsonPath("data.likesRestaurants[*].placeId").exists())
                .andExpect(jsonPath("data.likesRestaurants[*].name").exists())
                .andExpect(jsonPath("data.likesRestaurants[*].rating").exists())
                .andExpect(jsonPath("data.likesRestaurants[*].userRatingsTotal").exists())
                .andExpect(jsonPath("data.likesRestaurants[*].lat").exists())
                .andExpect(jsonPath("data.likesRestaurants[*].lng").exists())
                .andExpect(jsonPath("data.likesRestaurants[*].photo").exists())
                .andExpect(jsonPath("data.totalPages").value(10))
                .andDo(MockMvcRestDocumentationWrapper.document("get paginated likes list",
                        resource(ResourceSnippetParameters.builder()
                                .tag("Likes")
                                .summary("회원의 찜 목록 조회")
                                .description("요청 페이지에 대한 회원의 찜 목록 조회")
                                .pathParameters(
                                        parameterWithName("memberId").type(SimpleType.NUMBER).description("찜 목록 요청한 회원의 Id")
                                )
                                .queryParameters(
                                        parameterWithName("page").type(SimpleType.INTEGER).description("요청할 페이지 번호(0부터 시작함!!)"),
                                        parameterWithName("size").type(SimpleType.INTEGER).description("한 페이지에 가져올 찜 데이터 개수").defaultValue(10).optional()
                                )
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 시 반환되는 code 값"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 시 반환되는 메시지"),
                                        fieldWithPath("data.likesRestaurants").type(JsonFieldType.ARRAY).description("요청한 페이지에 해당하는 회원의 찜 목록 식당"),
                                        fieldWithPath("data.likesRestaurants[].likesId").type(JsonFieldType.NUMBER).description("해당 찜의 ID 값"),
                                        fieldWithPath("data.likesRestaurants[].placeId").type(JsonFieldType.STRING).description("식당의 PlaceId"),
                                        fieldWithPath("data.likesRestaurants[].name").type(JsonFieldType.STRING).description("식당 이름"),
                                        fieldWithPath("data.likesRestaurants[].lat").type(JsonFieldType.NUMBER).description("식당의 위도"),
                                        fieldWithPath("data.likesRestaurants[].lng").type(JsonFieldType.NUMBER).description("식당의 경도"),
                                        fieldWithPath("data.likesRestaurants[].photo").type(JsonFieldType.STRING).description("식당 대표 사진"),
                                        fieldWithPath("data.likesRestaurants[].userRatingsTotal").type(JsonFieldType.NUMBER).description("식당의 리뷰 개수"),
                                        fieldWithPath("data.likesRestaurants[].rating").type(JsonFieldType.NUMBER).description("식당 평점"),
                                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("회원의 찜 목록 페이지에 필요한 총 페이지 수")
                                ).build()
                        )
                ));
    }

    @Test
    @DisplayName("찜하기 테스트")
    void pushLike() throws Exception {
        // given
        LikesRestaurantRequest likesRestaurantRequest = LikesRestaurantRequest.builder()
                                                                              .placeId(PLACE_ID)
                                                                              .name(NAME)
                                                                              .lat(LAT)
                                                                              .lng(LNG)
                                                                              .photo(PHOTO_URL)
                                                                              .userRatingsTotal(USER_RATINGS_TOTAL)
                                                                              .rating(RATING)
                                                                              .build();

        PushLikesRequest pushLikesRequest = new PushLikesRequest(MEMBER_ID, likesRestaurantRequest);

        Member member = createMember(1);
        Restaurant restaurant = createRestaurant(1);

        Likes likes = Likes.builder()
                           .member(member)
                           .restaurant(restaurant)
                           .likesStatus(LikesStatusType.Y)
                           .build();

        ReflectionTestUtils.setField(likes, "id", LIKES_ID);

        String requestJson = new Gson().toJson(pushLikesRequest);

        given(likesService.addLikes(eq(pushLikesRequest.getMemberId()), any(LikesRestaurantRequest.class))).willReturn(likes);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .post(BASE_URL + "/push")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))  // 응답 코드 확인
                .andExpect(jsonPath("message").value("요청에 성공하였습니다."))  // 응답 메시지 확인
                .andExpect(jsonPath("data.likesId").value(LIKES_ID))  // LikesResponse의 likeId 필드 확인
                .andDo(MockMvcRestDocumentationWrapper.document("push-likes",
                        resource(ResourceSnippetParameters.builder()
                                .tag("Likes")
                                .description("찜하기 요청")
                                .requestFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("restaurantInfo").type(JsonFieldType.OBJECT).description("찜할 식당의 정보"),
                                        fieldWithPath("restaurantInfo.placeId").type(JsonFieldType.STRING).description("찜할 식당의 PlaceId"),
                                        fieldWithPath("restaurantInfo.name").type(JsonFieldType.STRING).description("찜할 식당의 이름"),
                                        fieldWithPath("restaurantInfo.lat").type(JsonFieldType.NUMBER).description("찜할 식당의 위도"),
                                        fieldWithPath("restaurantInfo.lng").type(JsonFieldType.NUMBER).description("찜할 식당의 경도"),
                                        fieldWithPath("restaurantInfo.photo").type(JsonFieldType.STRING).description("찜할 식당의 대표 사진 URL").optional(),
                                        fieldWithPath("restaurantInfo.userRatingsTotal").type(JsonFieldType.NUMBER).description("찜할 식당의 리뷰 개수").optional(),
                                        fieldWithPath("restaurantInfo.rating").type(JsonFieldType.NUMBER).description("찜할 식당의 평점").optional()
                                )
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("data.likesId").type(JsonFieldType.NUMBER).description("찜 ID")  // likeId 필드 추가
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("찜 삭제 테스트")
    void deleteLike() throws Exception {
        // given
        willDoNothing().given(likesService).deleteLikes(LIKES_ID);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .delete(BASE_URL + "/delete" + "/{likesId}", LIKES_ID)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))  // 응답 코드 확인
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다."))  // 응답 메시지 확인
                .andDo(MockMvcRestDocumentationWrapper.document("delete-like",
                        resource(ResourceSnippetParameters.builder()
                                .tag("Likes")
                                .description("찜 삭제 요청")
                                .pathParameters(
                                        parameterWithName("likesId").type(SimpleType.NUMBER).description("Likes Id")
                                )
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                                )
                                .build()
                        )
                ));
    }

    private Member createMember(int number) {
        return Member.builder()
                     .oauthServerId("test-server-id")
                     .email("test@test.com")
                     .role(Role.USER)
                     .birthday("test-birthday")
                     .nickname("test-nickname" + number)
                     .profileImageUrl("test-proflie-img-url")
                     .build();
    }

    private Restaurant createRestaurant(int number) {
        return Restaurant.builder()
                         .placeId(PLACE_ID + number)
                         .name(NAME)
                         .lat(LAT)
                         .lng(LNG)
                         .photo(PHOTO_URL)
                         .userRatingsTotal(USER_RATINGS_TOTAL)
                         .rating(RATING)
                         .build();
    }

    private Likes createLikes(Member member, Restaurant restaurant, LikesStatusType likesStatus) {
        return Likes.builder()
                    .member(member)
                    .restaurant(restaurant)
                    .likesStatus(likesStatus)
                    .build();
    }
}
