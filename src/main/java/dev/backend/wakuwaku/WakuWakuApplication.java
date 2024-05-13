package dev.backend.wakuwaku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
/* @ConfigurationProperties를 사용하기 위해서는 @ConfigurationPropertiesScan이 필요 */
public class WakuWakuApplication {

	public static void main(String[] args) {
		SpringApplication.run(WakuWakuApplication.class, args);
	}
}
