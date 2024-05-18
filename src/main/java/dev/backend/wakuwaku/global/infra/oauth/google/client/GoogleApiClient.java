package dev.backend.wakuwaku.global.infra.oauth.google.client;

import dev.backend.wakuwaku.global.infra.oauth.google.dto.GoogleMemberResponse;
import dev.backend.wakuwaku.global.infra.oauth.google.dto.GoogleToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface GoogleApiClient {

    @PostMapping("${oauth.google.token-uri}")
    GoogleToken fetchToken(MultiValueMap<String, String> params);

    GoogleMemberResponse fetchMember(@RequestParam("access_token") String accessToken);


    /*
    @PostExchange(url = "${oauth.google.token-uri}")
    GoogleToken fetchToken(@RequestParam(name ="MultiValueMap") MultiValueMap<String, String> params);

    @GetExchange("${oauth.google.resource-uri}")
    GoogleMemberResponse fetchMember(@RequestParam(name = AUTHORIZATION) String access_token);
    */
}

