package dev.backend.wakuwaku.domain.member.controller;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

//@SpringBootTest
//@AutoConfigureMockMvc
// 웹 관련 테스트만 실행 (test속도 up)
@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest {
    /*
    @Autowired
    private WebApplicationContext wac;

    // 메서드 실행할 때, 각각 실행됨.
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac) // MockMvc 인스턴스를 생성하며, 웹 애플리케이션 컨텍스트를 설정한다. 이를 통해 컨트롤러와 같은 웹 계층의 컴포넌트를 테스트할 수 있다.
                .addFilter(new CharacterEncodingFilter("UTF-8", true)) // 요청과 응답에 대한 문자 인코딩 필터를 추가한다. 이 필터는 UTF-8 인코딩을 사용하며, 모든 요청에 대해 적용된다.
                .alwaysDo(print()) // 모든 요청/응답을 콘솔에 출력한다.
                .build(); // 설정된 내용을 바탕으로 MockMvc 인스턴스를 생성한다.
    }

    //@Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    //가짜 memberService 등록
    @MockBean
    private MemberService memberService;
    private final String URL_PATH = "/wakuwaku/v1/members";

    @Test
    @DisplayName("회원 가입")
    public void testRegister() throws Exception {
        MemberRegisterRequest registerRequest = new MemberRegisterRequest();
        // 아이디, 비밀번호 설정 - 테스트
        registerRequest.setMemberId("testMemberId");
        registerRequest.setMemberPassword("testMemberPassword");
        // save 엔드포인트에 post요청 보냄
        mockMvc.perform(MockMvcRequestBuilders.post(URL_PATH + "/save")
                        // json 문자열로 변환
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                // 상태코드 201인지 확인
                .andExpect(MockMvcResultMatchers.status().isCreated())
                // 응답 id 필드 존재하는지 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("회원 아이디 검색")
    public void testFindById() throws Exception {
        Member memberEntity = new Member();
        memberEntity.setId(1L);
        memberEntity.setMemberId("testMember");
        // memberService.findById() 메서드 호출 -> 반환값 설정
        when(memberService.findById(1L)).thenReturn(memberEntity);
        // GET 요청으로 해당 엔드포인트에 접근하여 테스트 수행
        mockMvc.perform(get(URL_PATH + "/1"))
                .andExpect(status().isOk())
                // 반환된 JSON에서 memberId 필드의 값 확인
                .andExpect(jsonPath("$.memberId").value("testMember"))
                // 반환된 JSON에서 memberEmail 필드가 존재하지 않음을 확인
                .andExpect(jsonPath("$.memberEmail").doesNotExist())
                // 반환된 JSON에서 memberName 필드가 존재하지 않음을 확인
                .andExpect(jsonPath("$.memberName").doesNotExist());
    }

    @Test
    @DisplayName("회원 정보 수정")
    public void testUpdate() throws Exception {
        Long id = 1L;
        // 업데이트할 회원의 ID와 새로운 비밀번호 및 이름 생성
        String newPassword = "newPassword123";
        String newName = "newName123";
        // PUT 요청에 사용할 MemberUpdateRequest 객체 생성
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest();
        memberUpdateRequest.setMemberPassword(newPassword);
        memberUpdateRequest.setMemberName(newName);
        // memberService.findById() 메서드 호출 -> 반환값 설정
        when(memberService.update(id, memberUpdateRequest)).thenReturn(id);
        // PUT 요청으로 해당 엔드포인트에 회원 정보 업데이트 요청을 보냄
        mockMvc.perform(MockMvcRequestBuilders.put(URL_PATH + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 업데이트된 회원 엔터티의 내용을 확인하기 위해 반환된 JSON의 필드와 값 확인
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("회원 정보 삭제")
    public void testDelete() throws Exception {
        Long memberIdToDelete = 1L;
        // 가짜 MemberService가 deleteById 메소드가 호출되면 아무것도 하지 않도록 설정
        doNothing().when(memberService).deleteById(memberIdToDelete);
        // DELETE 요청을 보내어 해당 엔드포인트에 대한 테스트 수행
        mockMvc.perform(delete(URL_PATH + "/{id}", memberIdToDelete))
                .andExpect(status().isOk());
        // MemberService의 deleteById 메소드가 한 번 호출되었는지 검증
        verify(memberService, times(1)).deleteById(memberIdToDelete);
    }

    @Test
    @DisplayName("회원 리스트")
    public void testFindAll() throws Exception {
        // 가짜 MemberService가 findAll 메소드 호출 시 빈 리스트 반환하도록 설정
        List<Member> mockMemberList = new ArrayList<>();
        when(memberService.findAll()).thenReturn(mockMemberList);
        // GET 요청을 보내어 해당 엔드포인트에 대한 테스트 수행
        mockMvc.perform(get(URL_PATH + "/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
    */
}
