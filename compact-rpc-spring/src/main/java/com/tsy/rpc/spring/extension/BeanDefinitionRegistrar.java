package com.tsy.rpc.spring.extension;

import com.tsy.rpc.annotation.RpcServiceImpl;
import com.tsy.rpc.spring.annotation.RpcScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

import java.util.Map;

/**
 * @author Steven.T
 * @date 2022/3/22
 */
public class BeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private static final String BASE_PACKAGES_ATTRIBUTE = "basePackages";

    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        final String annotationName = RpcScan.class.getName();
        //从注解元信息中获取包含注解的所有字段名和字段值的Map
        final Map<String, Object> annotationAttributesMap = importingClassMetadata.getAnnotationAttributes(annotationName);
        final AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationAttributesMap);
        String[] basePackages = new String[0];
        if (annotationAttributes != null) {
            //从注解信息中获取相应字段名对应的字符串数组
            basePackages = annotationAttributes.getStringArray(BASE_PACKAGES_ATTRIBUTE);
        }
        if (basePackages.length == 0) {
            //说明未配置包扫描路径，默认选用该注解标注的类同级的包路径
            final String packageName = ((StandardAnnotationMetadata) importingClassMetadata)
                    .getIntrospectedClass()
                    .getPackageName();
            basePackages = new String[]{packageName};
        }
        //使用自定义包扫描器进行服务实现类的扫描
        final PackageScanner implScanner = new PackageScanner(registry, RpcServiceImpl.class);
        if (resourceLoader != null) {
            implScanner.setResourceLoader(resourceLoader);
        }
        implScanner.scan(basePackages);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
