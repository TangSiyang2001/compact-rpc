package com.tsy.rpc.spring.extension;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * 自定义包扫描器，可通过构造函数指定扫描的注解类型
 *
 * @author Steven.T
 * @date 2022/3/22
 */
public class PackageScanner extends ClassPathBeanDefinitionScanner {

    public PackageScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> annotatedType) {
        super(registry);
        super.addIncludeFilter(new AnnotationTypeFilter(annotatedType));
    }

}
