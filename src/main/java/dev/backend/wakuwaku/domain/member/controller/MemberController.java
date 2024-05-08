package dev.backend.wakuwaku.domain.member.controller;


import dev.backend.wakuwaku.domain.member.dto.request.MemberRegisterRequest;
import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.dto.response.GetMemberResponse;
import dev.backend.wakuwaku.domain.member.dto.response.MemberIdResponse;
import dev.backend.wakuwaku.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wakuwaku/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberIdResponse> register(@RequestBody MemberRegisterRequest registerRequest) {
        Long id = memberService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MemberIdResponse(id));
    }

//    @GetMapping("/login")
//    public ResponseEntity<MemberIdResponse> login(@RequestBody MemberLoginRequest loginRequest) {
//        Long id = memberService.login(loginRequest);
//        return ResponseEntity.ok().body(new MemberIdResponse(id));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMemberResponse> findById(@PathVariable("id") Long id) {
        GetMemberResponse memberResponse = new GetMemberResponse(memberService.findById(id));
        return ResponseEntity.ok().body(memberResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberIdResponse> update(@PathVariable("id") Long id, @RequestBody MemberUpdateRequest updateRequest) {
        Long updatedId = memberService.update(id, updateRequest);
        return ResponseEntity.ok().body(new MemberIdResponse(updatedId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        memberService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<GetMemberResponse>> findAll() {
        List<GetMemberResponse> memberResponses = memberService.findAll().stream()
                .map(GetMemberResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(memberResponses);
    }
}