package com.blog.jysblog;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class JysBlogApplication {

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${server.tomcat.threads.max:200}")
	private String threadLimit;

	@Value("${server.tomcat.accept-count:100}")
	private String connectionLimit;

	public static void main(String[] args) {
		SpringApplication.run(JysBlogApplication.class, args);
	}

	@PostConstruct
	private void initialize() {
		Locale.setDefault(Locale.KOREA); // 시스템 로케일을 한국으로 설정
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul")); // 시스템 시간을 한국 시간으로 설정
		System.setProperty("aws.ec2.metadata.disabled", "true"); // AWS SDK가 EC2 인스턴스 메타데이터를 조회하지 않도록 설정
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationReadyEvent() {
		log.info("Application name ::: {}" ,applicationName);
		log.info("Tomcat thread limit ::: {}", threadLimit);
		log.info("Tomcat connection limit ::: {}", connectionLimit);
		log.info("Current locale ::: {}", Locale.getDefault());
		log.info("Current time zone ::: {}", TimeZone.getDefault());
	}
}
