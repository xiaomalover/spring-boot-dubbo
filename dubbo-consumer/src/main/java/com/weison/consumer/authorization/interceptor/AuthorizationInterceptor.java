package com.weison.consumer.authorization.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.weison.consumer.authorization.annotation.Authorization;
import com.weison.consumer.authorization.constant.TokenConstant;
import com.weison.consumer.authorization.manager.TokenManager;
import com.weison.consumer.authorization.model.TokenModel;
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
        //验证token
        System.out.println(authorization);
        TokenModel model = manager.getTokenModel(authorization);
        if (ObjectUtil.isNotNull(model)) {
            System.out.println(model.getClass());
            if (manager.checkToken(model)) {
                //如果token验证成功，将token对应的用户id存在request中，便于之后注入
                request.setAttribute(TokenConstant.TOKEN_USER_FIELD, model.getUserId());
                return true;
            }
        }

        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
