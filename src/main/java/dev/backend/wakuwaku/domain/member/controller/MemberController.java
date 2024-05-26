package dev.backend.wakuwaku.domain.member.controller;


import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.dto.response.GetMemberResponse;
import dev.backend.wakuwaku.domain.member.dto.response.MemberIdResponse;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wakuwaku/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;


    /*
    기능: 회원가입
    ※ @Valid: 유효성 검사 거쳐야 함 / @RequestBody: 요청 body 데이터 자바 객체로 변환
     */
    /*
    @PostMapping("/save")
    public ResponseEntity<MemberIdResponse> register(@RequestBody MemberRegisterRequest registerRequest) {
        Long id = memberService.register(registerRequest.toMemberEntity());

        return ResponseEntity.status(HttpStatus.CREATED).body(new MemberIdResponse(id));
    }
 */

    @GetMapping("/{id}")
    public ResponseEntity<GetMemberResponse> findById(@PathVariable("id") Long id) {
        Member memberEntity = memberService.findById(id);

        return ResponseEntity.ok().body(new GetMemberResponse(memberEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberIdResponse> update(@PathVariable("id") Long id, @RequestBody MemberUpdateRequest memberUpdateRequest) {
        Long dbId = memberService.update(id, memberUpdateRequest);

        return ResponseEntity.ok().body(new MemberIdResponse(dbId));
    }
    @DeleteMapping("/{id}") // 삭제
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<GetMemberResponse>> findAll() {
        List<Member> memberList = memberService.findAll();

        return ResponseEntity.ok().body(memberList.stream()
                .map(GetMemberResponse::new)
                .collect(Collectors.toList()));
    }
/*
    @PostMapping("/login")
    public ResponseEntity<MemberIdResponse> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        Long id = memberService.login(memberLoginRequest.getMemberId(), memberLoginRequest.getMemberPassword());

        return ResponseEntity.ok().body(new MemberIdResponse(id));
    }
    */


//    @GetMapping("/logout")
//    public ResponseEntity<Void> logout(HttpSession session) {
//        session.invalidate();
//        return ResponseEntity.ok().build();
//    }
}
