package com.catalpawoo.easynetty.start;

import com.catalpawoo.easynetty.start.handler.EasyNettyServerHandler;
import com.catalpawoo.easynetty.starter.handler.EasyNettyHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Easy-Netty服务端测试类
 */
@Slf4j
@SpringBootTest(classes = EasyNettyServerApplication.class, webEnvironment = RANDOM_PORT)
public class EasyNettyServerTest {

    @Autowired
    private EasyNettyHandler easyNettyHandler;

    @Autowired
    private EasyNettyServerHandler easyNettyServerHandler;

    @Test
    public void testEasyNettyServer() {
        easyNettyHandler.startNettyServer(easyNettyServerHandler, "/socket", 9000, 1).asShortText();
    }

}

@SpringBootApplication
class EasyNettyServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyNettyServerApplication.class, args);
    }
}
