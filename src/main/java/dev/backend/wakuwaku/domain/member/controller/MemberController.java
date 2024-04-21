package dev.backend.wakuwaku.domain.member.controller;


import dev.backend.wakuwaku.domain.member.dto.request.MemberLoginRequestDto;
import dev.backend.wakuwaku.domain.member.dto.request.MemberRegisterRequestDto;
import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequestDto;
import dev.backend.wakuwaku.domain.member.dto.response.GetMemberResponseDto;
import dev.backend.wakuwaku.domain.member.dto.response.MemberIdResponseDto;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/save")
    public ResponseEntity<MemberIdResponseDto> register(@RequestBody MemberRegisterRequestDto registerRequest) {
        Long id = memberService.register(registerRequest.toMemberEntity());

        return ResponseEntity.status(HttpStatus.CREATED).body(new MemberIdResponseDto(id));
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetMemberResponseDto> findById(@PathVariable("id") Long id) {
        Member memberEntity = memberService.findById(id);

        return ResponseEntity.ok().body(new GetMemberResponseDto(memberEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberIdResponseDto> update(@PathVariable("id") Long id, @RequestBody MemberUpdateRequestDto memberUpdateRequest) {
        Long dbId = memberService.update(id, memberUpdateRequest);

        return ResponseEntity.ok().body(new MemberIdResponseDto(dbId));
    }

    @DeleteMapping("/{id}") // 삭제
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<GetMemberResponseDto>> findAll() {
        List<Member> memberList = memberService.findAll();

        return ResponseEntity.ok().body(memberList.stream()
                .map(GetMemberResponseDto::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberIdResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequest) {
        Long id = memberService.login(memberLoginRequest.getMemberId(), memberLoginRequest.getMemberPassword());

        return ResponseEntity.ok().body(new MemberIdResponseDto(id));
    }

//    @GetMapping("/logout")
//    public ResponseEntity<Void> logout(HttpSession session) {
//        session.invalidate();
//        return ResponseEntity.ok().build();
//    }
}
