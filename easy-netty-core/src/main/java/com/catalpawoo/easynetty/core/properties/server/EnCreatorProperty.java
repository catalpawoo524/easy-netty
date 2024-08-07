package com.catalpawoo.easynetty.core.properties.server;

import com.catalpawoo.easynetty.common.constants.IntegerConstant;
import com.catalpawoo.easynetty.core.properties.common.CommonCreatorProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建器配置参数类
 *
 * @author wuzijing
 * @since 2024-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EnCreatorProperty extends CommonCreatorProperty {

    /**
     * 连接请求等待队列大小
     */
    private Integer soBacklog = IntegerConstant.ONE_THOUSAND_TWENTY_FOUR;

}
