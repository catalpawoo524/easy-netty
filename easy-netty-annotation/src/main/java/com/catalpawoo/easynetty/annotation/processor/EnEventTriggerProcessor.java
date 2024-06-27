package com.catalpawoo.easynetty.annotation.processor;

import com.catalpawoo.easynetty.annotation.EnEventTrigger;
import com.catalpawoo.easynetty.annotation.utils.ProcessorUtil;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.Set;

/**
 * 服务端事件触发注解处理器
 *
 * @author wuzijing
 * @since 2024-06-27
 */
@SupportedAnnotationTypes("com.catalpawoo.easynetty.annotation.EnEventTrigger")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EnEventTriggerProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(EnEventTrigger.class)) {
            ProcessorUtil.checkAccessPublic(element, processingEnv);
        }
        return true;
    }

}