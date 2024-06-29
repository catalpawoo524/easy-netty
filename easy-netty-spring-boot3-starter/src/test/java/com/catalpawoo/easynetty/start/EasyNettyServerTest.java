package com.catalpawoo.easynetty.start;

import com.catalpawoo.easynetty.core.EasyNettyServerBuilder;
import com.catalpawoo.easynetty.core.EasyNettyServerCreator;
import com.catalpawoo.easynetty.start.handler.EasyNettyServerBaseHandler;
import com.catalpawoo.easynetty.starter.helper.EasyNettyHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Easy-Netty服务端测试类
 *
 * @author wuzijing
 * @since 2024-06-22
 */
@Slf4j
@SpringBootTest(classes = EasyNettyServerApplication.class, webEnvironment = RANDOM_PORT)
public class EasyNettyServerTest implements DisposableBean {

    @Autowired
    private EasyNettyHelper easyNettyHelper;

    @Autowired
    private EasyNettyServerBaseHandler easyNettyServerBaseHandler;

    @Test
    public void testCreateEasyNettyServer() {
        // 帮助类快速构建
        easyNettyHelper.startServer(easyNettyServerBaseHandler, "/socket", 8082, 1).asShortText();
        // 构造器构建
        EasyNettyServerCreator buildCreator = new EasyNettyServerBuilder()
                .setPort(8083)
                .setPath("/socket")
                .setBossThreadNum(1)
                .setHandler(new EasyNettyServerBaseHandler())
                .build();
    }

    @Override
    public void destroy() {
        int num = easyNettyHelper.shutdownServer();
        log.info("easy-netty destroyed quantity: {}", num);
    }

}

@SpringBootApplication
class EasyNettyServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyNettyServerApplication.class, args);
    }
}