package com.blog.jysblog.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // application-test.yml 파일 불러오기
public class JasyptTest {

    @Value("${jasypt.encryptor.key}")
    String key;

    @Test
    void jasypt() {
        String secret = "abcd123";

        String encodingKey = jasyptEncoding(secret);
        String decodingKey = jasyptDecoding(encodingKey);

        System.out.printf("%s -> %s\n", secret, encodingKey);
        System.out.printf("%s -> %s\n", encodingKey, decodingKey);
    }

    public String jasyptEncoding(String value) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

    public String jasyptDecoding(String encryptedValue) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.decrypt(encryptedValue);
    }

}
