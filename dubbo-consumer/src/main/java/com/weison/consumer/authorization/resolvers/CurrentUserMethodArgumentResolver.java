package com.weison.consumer.authorization.resolvers;

import cn.hutool.core.util.ObjectUtil;
import com.weison.base.api.UserService;
import com.weison.base.po.User;
import com.weison.consumer.authorization.annotation.CurrentUser;
import com.weison.consumer.authorization.constant.TokenConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 * @see com.weison.consumer.authorization.annotation.CurrentUser
 * @author xiaomalover <xiaomalover@gmail.com>
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @SuppressWarnings({"SpringJavaAutowiringInspection", "SpringAutowiredFieldsWarningInspection"})
    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是User并且有CurrentUser注解则支持
        return parameter.getParameterType().isAssignableFrom(User.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) throws Exception {
        int currentUserId = (int) webRequest.getAttribute(TokenConstant.TOKEN_USER_FIELD, RequestAttributes.SCOPE_REQUEST);
        if (ObjectUtil.isNotNull(currentUserId)) {
            //从数据库中查询并返回

            return userService.findById(currentUserId);
        }
        throw new MissingServletRequestPartException(TokenConstant.TOKEN_USER_FIELD);
    }
}
