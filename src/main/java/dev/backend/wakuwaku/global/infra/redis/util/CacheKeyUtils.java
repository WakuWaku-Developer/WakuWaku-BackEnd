package dev.backend.wakuwaku.global.infra.redis.util;

import org.springframework.stereotype.Component;

/**
 * 캐시에 저장 시 Key 값을 사용자의 검색어로 하는데, 검색어의 공백을 제거하여 단어는 같지만 공백으로 인해 중복 캐싱되는 것을 방지하기 위한 클래스
 */
@Component
public class CacheKeyUtils {
    public String removeWhitespace(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("\\s+", "");
    }
}
