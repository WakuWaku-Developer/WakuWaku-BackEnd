package dev.backend.wakuwaku.domain.like.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import dev.backend.wakuwaku.domain.likes.controller.LikesController;
import dev.backend.wakuwaku.domain.likes.dto.LikesStatusType;
import dev.backend.wakuwaku.domain.likes.dto.request.LikesRequest;
import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.likes.service.LikesService;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();

        // Mock 객체 설정
        testMember = Member.builder().email("test@test.com").build();
        testRestaurant = new Restaurant();
        testLikes = Likes.builder()
                .member(testMember)
                .restaurant(testRestaurant)
                .likesStatus(LikesStatusType.Y)
                .build();
    }

    @Test
    @DisplayName("찜하기 테스트")
    void pushLike() throws Exception {
        // given
        Long memberId = 1L;
        Long restaurantId = 1L;
        Long likesId = 1L;

        Likes mockLikes = new Likes(testMember, testRestaurant, LikesStatusType.Y);
        given(likesService.addLikes(memberId, restaurantId)).willReturn(mockLikes);
        mockLikes.createId(1L);

        LikesRequest requestDto = new LikesRequest(memberId, restaurantId);
        String requestJson = new Gson().toJson(requestDto);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URL + "/push")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))  // 응답 코드 확인
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다."))  // 응답 메시지 확인
                .andExpect(jsonPath("$.data.likeId").value(likesId))  // LikesResponse의 likeId 필드 확인
                .andDo(MockMvcRestDocumentationWrapper.document("push-like",
                        ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                                .tag("Likes")
                                .description("찜하기 요청")
                                .requestFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("restaurantId").type(JsonFieldType.NUMBER).description("레스토랑 ID")
                                )
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("data.likeId").type(JsonFieldType.NUMBER).description("찜 ID")  // likeId 필드 추가
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("찜 삭제 테스트")
    void deleteLike() throws Exception {
        // given
        Long memberId = 1L;
        Long restaurantId = 1L;
        willDoNothing().given(likesService).deleteLikes(memberId, restaurantId);

        LikesRequest requestDto = new LikesRequest(memberId, restaurantId);
        String requestJson = new Gson().toJson(requestDto);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.delete(BASE_URL + "/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))  // 응답 코드 확인
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다."))  // 응답 메시지 확인
                .andDo(MockMvcRestDocumentationWrapper.document("delete-like",
                        ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                                .tag("Likes")
                                .description("찜 삭제 요청")
                                .requestFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("restaurantId").type(JsonFieldType.NUMBER).description("레스토랑 ID")
                                )
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                                )
                                .build()
                        )
                ));
    }
}
