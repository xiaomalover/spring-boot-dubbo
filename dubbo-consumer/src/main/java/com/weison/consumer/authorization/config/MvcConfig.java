package com.weison.consumer.authorization.config;

import com.weison.consumer.authorization.interceptor.AuthorizationInterceptor;
import com.weison.consumer.authorization.resolvers.CurrentUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.util.List;

/**
 * 配置类，增加自定义拦截器和解析器
 * @see com.weison.consumer.authorization.resolvers.CurrentUserMethodArgumentResolver
 * @see com.weison.consumer.authorization.interceptor.AuthorizationInterceptor
 * @author xiaomalover <xiaomalover@gmail.com>
 */
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }
}
