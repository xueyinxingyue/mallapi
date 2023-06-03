package com.xueyin.mallapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class MallapiApplicationTests {

    @Test
    void contextLoads() {
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(s);
    }

}
