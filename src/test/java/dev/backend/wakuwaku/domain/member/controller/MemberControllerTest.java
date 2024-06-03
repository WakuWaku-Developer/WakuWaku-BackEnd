package dev.backend.wakuwaku.domain.member.controller;


import dev.backend.wakuwaku.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

//@SpringBootTest
//@AutoConfigureMockMvc
// 웹 관련 테스트만 실행 (test속도 up)
@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest {
    @MockBean
    private MemberService memberService;

    private MockMvc mockMvc;

    private static final String BASE_URL = "/wakuwaku/v1/member";

    @BeforeEach
    void setUp(final WebApplicationContext context,
               final RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }
}
