package com.catalpawoo.easynetty.annotation.processor;

import com.catalpawoo.easynetty.annotation.EnAdd;
import com.catalpawoo.easynetty.annotation.utils.ProcessorUtil;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * 服务端新增连接注解处理器
 *
 * @author wuzijing
 * @since 2024-06-27
 */
@SupportedAnnotationTypes("com.catalpawoo.easynetty.annotation.EnAdd")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EnAddProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(EnAdd.class)) {
            ProcessorUtil.checkAccessPublic(element, processingEnv);
        }
        return true;
    }

}