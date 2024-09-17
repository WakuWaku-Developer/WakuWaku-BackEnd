package dev.backend.wakuwaku.global.infra.oauth.client;

import dev.backend.wakuwaku.domain.oauth.dto.OauthMember;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.global.exception.ExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class OauthMemberClientComposite {
    private final Map<OauthServerType, OauthMemberClient> mapping;

    public OauthMemberClientComposite(Set<OauthMemberClient> clients) {
        mapping = clients.stream()
                         .collect(toMap(
                                 OauthMemberClient::supportServer,
                                 identity()
                         ));
    }

    public OauthMember fetch(OauthServerType oauthServerType, String authCode) {
        OauthMember oauthMember = getClient(oauthServerType).fetch(authCode);

        log.info("oauthMember.getEmail() = {}", oauthMember.getEmail());

        return oauthMember;
    }

    private OauthMemberClient getClient(OauthServerType oauthServerType) {
        OauthMemberClient oAuthMemberClient = Optional.ofNullable(mapping.get(oauthServerType))
                                                      .orElseThrow(
                                                              () -> new RuntimeException(ExceptionStatus.NOT_EXISTED_SOCIAL_TYPE.getMessage())
                                                      );

        log.info("(oAuthMemberClient == null) = {}", oAuthMemberClient == null);

        return oAuthMemberClient;
    }
}