package dev.backend.wakuwaku.domain.oauth.controller;

import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/{oauthServerType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable(name = "oauthServerType") String oauthServerTypeStr,
            HttpServletResponse response
    ) throws IOException {

        OauthServerType oauthServerType = OauthServerType.fromName(oauthServerTypeStr);
        if (oauthServerType == null) {
            return ResponseEntity.notFound().build();
        }

        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        if ("google".equals(oauthServerTypeStr)) {
            // 스코프 파라미터를 추가하기 전에 이전에 포함된 스코프 파라미터를 제거
            redirectUrl = redirectUrl.replaceAll("&scope=[^&]*", "");
            redirectUrl += "&scope=profile%20email"; // 스페이스를 URL 인코딩하여 추가
        }

        response.sendRedirect(redirectUrl);

        System.out.println("1>>>" + oauthServerTypeStr);
        System.out.println("2>>>" + redirectUrl);
        System.out.println("3>>>" + oauthServerType);
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
