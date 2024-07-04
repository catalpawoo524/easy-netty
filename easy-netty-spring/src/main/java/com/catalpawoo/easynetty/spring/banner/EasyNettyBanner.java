package com.catalpawoo.easynetty.spring.banner;

import com.catalpawoo.easynetty.common.EasyNettyVersion;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * 横幅实现类
 * TODO: YAML禁用输出
 * @author wuzijing
 * @since 2024-07-04
 */
public class EasyNettyBanner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(" _____ ____  ____ ___  _ _      _____ _____ _____ ___  _");
        System.out.println("/  __//  _ \\/ ___\\\\  \\/// \\  /|/  __//__ __Y__ __\\\\  \\//");
        System.out.println("|  \\  | / \\||    \\ \\  / | |\\ |||  \\    / \\   / \\   \\  / ");
        System.out.println("|  /_ | |-||\\___ | / /  | | \\|||  /_   | |   | |   / /  ");
        System.out.println("\\____\\\\_/ \\|\\____//_/   \\_/  \\|\\____\\  \\_/   \\_/  /_/   ");
        System.out.println("©catalpa :: " + EasyNettyVersion.getVersion());
    }

}
