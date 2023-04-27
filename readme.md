# 需求

@EnableLogRecord 注解不写在启动类上，同时自定义的 Bean 可以覆盖默认的 Bean

# 探究原理

## @ConditionalOnMissingBean

@ConditionalOnMissingBean 原理是根据当前 spring context 对应的 BeanDefinition 是否存在。
若存在，则跳过当前 Bean 的创建，不存在则创建  
见 org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod

## BeanDefinition 的加载

spring 扫描类及加载 BeanDefinition 的源码  
org.springframework.context.annotation.ConfigurationClassPostProcessor#processConfigBeanDefinitions

# 解决过程

因此需要解决自定义 Bean 的 BeanDefinition 先注册到 spring context 或类更先被 spring 扫描到（先扫描到，会先注册
BeanDefinition）

## 使用 @Order

无效，启动报错 Bean 重复定义。  
@Order 只会影响扫描类的条件的顺序，而不会在 spring 扫描类及加载 BeanDefinition 后进行排序

## 使用 @DependsOn

无效，启动报错 Bean 重复定义。

> 暂未找到原因。推测 @DependsOn 仅会影响 Bean 的创建顺序，而不会影响 BeanDefinition 的加载顺序

## 使用 @Primary

有效，满足需求。  
需要注意的是要修改自定义 Bean 的 BeanName，不能与库注册的 BeanName 相同，同时添加 @Primary。  
实际上这种方法存在一定的弊端
* 库默认注册的 Bean 会被创建，这些 Bean 是无效的
* 只能覆盖未使用 BeanName 注入的依赖

## 自定义 ApplicationContextInitializer，BeanDefinitionRegistryPostProcessor

有效，满足需求。
在 BeanDefinitionRegistryPostProcessor 中手动注册自定义配置类，并在自定义配置类添加 @Order 以影响 spring 扫描类的条件的顺序，从而实现更先扫描到类及加载
BeanDefinition 的顺序
