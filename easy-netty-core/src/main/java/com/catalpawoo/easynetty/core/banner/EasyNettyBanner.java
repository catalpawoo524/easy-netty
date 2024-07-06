package com.catalpawoo.easynetty.core.banner;

import com.catalpawoo.easynetty.common.EasyNettyVersion;
import lombok.AllArgsConstructor;

import javax.annotation.PostConstruct;

/**
 * 横幅实现类
 *
 * @author wuzijing
 * @since 2024-07-04
 */
@AllArgsConstructor
public class EasyNettyBanner {

    /**
     * 横幅开关
     */
    private Boolean bannerSwitch;

    /**
     * 输出横幅
     */
    @PostConstruct
    public void consoleBanner() {
        if (!bannerSwitch) {
            return;
        }
        System.out.println(" _____ ____  ____ ___  _ _      _____ _____ _____ ___  _");
        System.out.println("/  __//  _ \\/ ___\\\\  \\/// \\  /|/  __//__ __Y__ __\\\\  \\//");
        System.out.println("|  \\  | / \\||    \\ \\  / | |\\ |||  \\    / \\   / \\   \\  / ");
        System.out.println("|  /_ | |-||\\___ | / /  | | \\|||  /_   | |   | |   / /  ");
        System.out.println("\\____\\\\_/ \\|\\____//_/   \\_/  \\|\\____\\  \\_/   \\_/  /_/   ");
        System.out.println("©catalpa :: " + EasyNettyVersion.getVersion());
    }

}
