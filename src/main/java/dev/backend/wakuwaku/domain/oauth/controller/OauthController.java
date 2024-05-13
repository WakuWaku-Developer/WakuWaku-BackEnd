package dev.backend.wakuwaku.domain.oauth.controller;

import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 *
 * @PathVariable을 통해 /oauth/kakao 등의 요청에서 kakao를 OauthServerType로 변환하여 받아옵니다.
 * 이는 Converter를 등록해 주어야 하는데, 조금 뒤에 살펴보고 일단 로직의 흐름을 살펴보겠습니다.
 * 사용자가 프론트엔드를 통해 /oauth/kakao로 접속하면 위의 메서드가 실행됩니다.
 * 이때 kakao는 OauthServerType.KAKAO로 변환될 것입니다.
 * 이제 위에서 구현한 Service를 통해 KAKAO에서 Auth Code를 받아오기 위한 URL을 생성하고,
 * 여기서 생성된 URL로 사용자를 Redirect 시킵니다.
 */
@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OauthController {

    private final OauthService oauthService;

    /*
    @GetMapping("/{oauthServerType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable(name = "oauthServerType") String oauthServerTypeStr,
            HttpServletResponse response
    ) throws IOException {
        OauthServerType oauthServerType = OauthServerType.fromName(oauthServerTypeStr);
        if (oauthServerType == null) {
            // 잘못된 경로 변수 값에 대한 처리
            return ResponseEntity.notFound().build();
        }
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }


     */

    @GetMapping("/{oauthServerType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable(name = "oauthServerType") String oauthServerTypeStr,
            HttpServletResponse response
    ) throws IOException {
        OauthServerType oauthServerType = OauthServerType.fromName(oauthServerTypeStr);
        if (oauthServerType == null) {
            // 잘못된 경로 변수 값에 대한 처리
            return ResponseEntity.notFound().build();
        }
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{oauthServerType}")
    ResponseEntity<Long> login(
            @PathVariable(name = "oauthServerType") OauthServerType oauthServerType,
            @RequestParam(name = "code") String code) {
        Long login = oauthService.login(oauthServerType, code);
        return ResponseEntity.ok(login);
    }
}