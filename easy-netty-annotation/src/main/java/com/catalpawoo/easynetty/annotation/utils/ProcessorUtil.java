package com.catalpawoo.easynetty.annotation.utils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;

/**
 * 处理器工具类
 *
 * @author wuzijing
 * @since 2024-06-27
 */
public class ProcessorUtil {

    /**
     * 检查方法是否公共访问权限（否则输出异常）
     *
     * @param element       元素
     * @param processingEnv 处理器环境
     */
    public static void checkAccessPublic(Element element, ProcessingEnvironment processingEnv) {
        if (element.getKind() == ElementKind.METHOD) {
            ExecutableElement method = (ExecutableElement) element;
            // 检查方法是否为公共的，否则无法调用
            if (!method.getModifiers().contains(Modifier.PUBLIC)) {
                TypeElement classElement = (TypeElement) method.getEnclosingElement();
                String className = classElement.getQualifiedName().toString();
                String methodName = method.getSimpleName().toString();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "[" + className + "] " + methodName + " methods annotated with EasyNetty must be public.", element);
            }
        }
    }

}
