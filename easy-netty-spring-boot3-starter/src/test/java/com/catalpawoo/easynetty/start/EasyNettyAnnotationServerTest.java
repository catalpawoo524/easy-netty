package com.catalpawoo.easynetty.start;

import com.catalpawoo.easynetty.core.creator.EasyNettyServerBuilder;
import com.catalpawoo.easynetty.core.creator.EasyNettyServerCreator;
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
 */
@Slf4j
@SpringBootTest(classes = EasyNettyServerApplication.class, webEnvironment = RANDOM_PORT)
public class EasyNettyAnnotationServerTest implements DisposableBean {

    @Autowired
    private EasyNettyHelper easyNettyHelper;

    @Autowired
    private EasyNettyServerBaseHandler easyNettyServerBaseHandler;

    @Test
    public void testEasyNettyServer() {
        // 帮助类快速构建
        easyNettyHelper.startNettyServer(easyNettyServerBaseHandler, "/socket", 8082, 1).asShortText();
        // 构造器构建
        EasyNettyServerCreator buildCreator = new EasyNettyServerBuilder()
                .setPort(8083)
                .setPath("/socket")
                .setBossThreadNum(1)
                .setHandler(new EasyNettyServerBaseHandler())
                .build();
        // 维护到帮助类中
        easyNettyHelper.putServer(buildCreator);
    }

    @Override
    public void destroy() {
        int num = easyNettyHelper.shutdownNettyServer();
        log.info("easy-netty destroyed quantity: {}", num);
    }

}

@SpringBootApplication
class EasyNettyServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyNettyServerApplication.class, args);
    }
}
