package com.weison.consumer.authorization.interceptor;

import com.weison.consumer.authorization.annotation.Authorization;
import com.weison.consumer.authorization.constant.TokenConstant;
import com.weison.consumer.authorization.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，判断此次请求是否有权限
 *
 * @author xiaomalover <xiaomalover@gmail.com>
 * @see com.weison.consumer.authorization.annotation.Authorization
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "SpringAutowiredFieldsWarningInspection"})
    @Autowired
    private TokenManager manager;

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //从header中得到token
        String authorization = request.getHeader(TokenConstant.TOKEN_PARAM_NAME);

        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            //验证token
            int authUserId = manager.checkToken(authorization);
            if (authUserId == 0) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            //获取用户id，放入参数
            request.setAttribute(TokenConstant.TOKEN_USER_FIELD, authUserId);
        }

        return true;
    }
}
