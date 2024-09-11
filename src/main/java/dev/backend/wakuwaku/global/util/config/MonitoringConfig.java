package dev.backend.wakuwaku.global.util.config;

import dev.backend.wakuwaku.global.infra.redis.service.RedisService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitoringConfig {
    @Bean
    public MeterBinder bindTo(RedisService redisService) {
        return registry -> Gauge.builder("redis.cache.count", redisService, RedisService::getCacheSize)
                                .register(registry);
    }
}
