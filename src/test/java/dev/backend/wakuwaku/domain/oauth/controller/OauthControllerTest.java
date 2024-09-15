package dev.backend.wakuwaku.domain.oauth.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import dev.backend.wakuwaku.domain.likes.service.LikesService;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import dev.backend.wakuwaku.domain.oauth.dto.request.LoginRequest;
import dev.backend.wakuwaku.domain.oauth.service.OauthService;
import jakarta.persistence.EntityManager;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = OauthController.class)
class OauthControllerTest {
    @MockBean
    private OauthService oauthService;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private LikesService likesService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(final WebApplicationContext context, final RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                      .apply(documentationConfiguration(restDocumentation))
                                      .alwaysDo(MockMvcResultHandlers.print())
                                      .addFilters(new CharacterEncodingFilter("UTF-8", true))
                                      .build();
    }

    @Test
    @DisplayName("OAuth 로그인 테스트")
    void login() throws Exception {
        // given
        Long memberId = 1L;
        String code = "authorization_code";
        String testEmail = "test@test.com";
        String testNickname = "testNickname";
        String testAccessToken = "testAccessToken";
        String testProfileImgUrl = "test-img-url";
        String testBirthday = "2001.01.01";

        List<String> likesPlacesIds = new ArrayList<>();

        likesPlacesIds.add(createPlacesIds(1));
        likesPlacesIds.add(createPlacesIds(2));
        likesPlacesIds.add(createPlacesIds(3));
        likesPlacesIds.add(createPlacesIds(4));

        Member member = Member.builder()
                              .email(testEmail)
                              .nickname(testNickname)
                              .oauthServerId(testAccessToken)
                              .oauthServerType(OauthServerType.GOOGLE)
                              .profileImageUrl(testProfileImgUrl)
                              .birthday(testBirthday)
                              .role(Role.USER)
                              .build();
        ReflectionTestUtils.setField(member, "id", memberId);

        given(oauthService.login(OauthServerType.GOOGLE, code)).willReturn(member);
        given(likesService.getLikedRestaurantPlaceIds(member)).willReturn(likesPlacesIds);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.createOauthServerType(OauthServerType.GOOGLE);
        loginRequest.createCode(code);

        // Gson 객체 생성
        Gson gson = new Gson();
        String jsonContent = gson.toJson(loginRequest);

        // when & then
        mockMvc.perform(post("/oauth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(memberId))
                .andExpect(jsonPath("$.data.profileUrl").value(testProfileImgUrl))
                .andExpect(jsonPath("$.data.nickname").value(testNickname))
                .andExpect(jsonPath("$.data.accessToken").value(testAccessToken))
                .andExpect(jsonPath("$.data.likedRestaurantPlaceIds").value(likesPlacesIds))
                .andDo(MockMvcRestDocumentationWrapper.document("oauth-login",
                        resource(ResourceSnippetParameters.builder()
                                .tag("OAuth")
                                .description("OAuth 로그인.")
                                .requestFields(
                                        fieldWithPath("oauthServerType").type(JsonFieldType.STRING).description("OAuth 서버 타입: NAVER, KAKAO, GOOGLE"),
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("인증 코드")
                                )
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("data.profileUrl").type(JsonFieldType.STRING).description("회원의 프로필 이미지 URL"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("회원의 닉네임"),
                                        fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("회원의 Access TOken"),
                                        fieldWithPath("data.likedRestaurantPlaceIds").type(JsonFieldType.ARRAY).description("회원이 찜한 식당의 PlaceId")
                                )
                                .build()
                        )
                ));

        then(oauthService).should().login(OauthServerType.GOOGLE, code);
        then(likesService).should().getLikedRestaurantPlaceIds(member);
    }

    private String createPlacesIds(int number) {
        return "test-placesId-" + number;
    }
}
