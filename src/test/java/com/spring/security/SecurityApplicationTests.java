package com.spring.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

//@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SecurityApplicationTests {

    @Test
    public void contextLoads() {
        log.info("=====================> start");
    }

    @Test
    public void contextLoads1() {
        Integer a = 26365;//这个数字是你的米
        BigDecimal mi = new BigDecimal(a);
        BigDecimal divide = mi.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
        System.out.println(divide);
    }

}
