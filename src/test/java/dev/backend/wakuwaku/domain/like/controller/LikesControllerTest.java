package dev.backend.wakuwaku.domain.like.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import dev.backend.wakuwaku.domain.likes.controller.LikesController;
import dev.backend.wakuwaku.domain.likes.service.LikesService;
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

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
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

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("찜하기 테스트")
    void pushLike() throws Exception {
        /*// given
        Long memberId = 1L;
        Long restaurantId = 1L;
        given(likesService.addLike(memberId, restaurantId)).willReturn();

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URL + "/push")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"memberId\": 1, \"restaurantId\": 1 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))  // 응답 코드 확인
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다."))  // 응답 메시지 확인
                .andExpect(jsonPath("$.data").value("찜하기 성공"))  // 실제로 찜하기 성공 메시지를 확인
                .andDo(MockMvcRestDocumentationWrapper.document("push-like",
                        resource(ResourceSnippetParameters.builder()
                                .tag("Likes")
                                .description("찜하기 요청")
                                .requestFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("restaurantId").type(JsonFieldType.NUMBER).description("레스토랑 ID")
                                )
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("data").type(JsonFieldType.STRING).description("결과 메시지")
                                )
                                .build()
                        )
                ));*/
    }



    @Test
    @DisplayName("찜 삭제 테스트")
    void deleteLike() throws Exception {
        // given
        Long memberId = 1L;
        Long restaurantId = 1L;
        given(likesService.deleteLikes(memberId, restaurantId)).willReturn(true);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.delete(BASE_URL + "/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"memberId\": 1, \"restaurantId\": 1 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))  // 응답 코드 확인
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다."))  // 응답 메시지 확인
                .andExpect(jsonPath("$.data").value("찜 삭제 성공"))  // 실제로 삭제 성공 메시지를 확인
                .andDo(MockMvcRestDocumentationWrapper.document("delete-like",
                        resource(ResourceSnippetParameters.builder()
                                .tag("Likes")
                                .description("찜 삭제 요청")
                                .requestFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("restaurantId").type(JsonFieldType.NUMBER).description("레스토랑 ID")
                                )
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("data").type(JsonFieldType.STRING).description("결과 메시지")
                                )
                                .build()
                        )
                ));
    }

}

