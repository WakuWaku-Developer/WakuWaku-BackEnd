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

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("wakuwaku/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;


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
        memberService.deactivateById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<GetMemberResponse>> findAll() {
        List<Member> memberList = memberService.findAll();

        return ResponseEntity.ok().body(memberList.stream()
                .map(GetMemberResponse::new)
                .collect(toList()));
    }

}
