package dev.backend.wakuwaku.global.infra.oauth.oauthcode;

import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OauthCodeRequestUrlProviderCompositeTest {

    @Mock
    private OauthCodeRequestUrlProvider googleProvider;

    private OauthCodeRequestUrlProviderComposite composite;

    @BeforeEach
    void setUp() {
        // Create a set of OauthCodeRequestUrlProvider instances
        Set<OauthCodeRequestUrlProvider> providers = Set.of(googleProvider);

        // Mock behavior for googleProvider
        when(googleProvider.supportServer()).thenReturn(OauthServerType.GOOGLE);
        when(googleProvider.provide()).thenReturn("https://google.com/oauth");

        // Instantiate the composite with the set of providers
        composite = new OauthCodeRequestUrlProviderComposite(providers);
    }

    @Test
    @DisplayName("제공되는 URL 일치 테스트")
    void testProvide() {
        // Test the provide method
        String providedUrl = composite.provide(OauthServerType.GOOGLE);
        assertEquals("https://google.com/oauth", providedUrl);
    }
}
