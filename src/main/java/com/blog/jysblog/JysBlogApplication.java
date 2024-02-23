package com.blog.jysblog;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class JysBlogApplication implements WebMvcConfigurer {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.tomcat.threads.max:200}")
    private String threadLimit;

    @Value("${server.tomcat.accept-count:100}")
    private String connectionLimit;

    public static void main(String[] args) {
        SpringApplication.run(JysBlogApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins("*") // 모든 도메인을 허용
                .allowedHeaders("*") // 모든 헤더를 허용
                .allowedMethods("GET", "POST", "DELETE", "PUT");

    }

    @PostConstruct
    private void initialize() {
        Locale.setDefault(Locale.KOREA); // 시스템 로케일을 한국으로 설정
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul")); // 시스템 시간을 한국 시간으로 설정
        System.setProperty("aws.ec2.metadata.disabled", "true"); // AWS SDK가 EC2 인스턴스 메타데이터를 조회하지 않도록 설정
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReadyEvent() {
        log.info("Application name ::: {}", applicationName);
        log.info("Tomcat thread limit ::: {}", threadLimit);
        log.info("Tomcat connection limit ::: {}", connectionLimit);
        log.info("Current locale ::: {}", Locale.getDefault());
        log.info("Current time zone ::: {}", TimeZone.getDefault());
    }
}
