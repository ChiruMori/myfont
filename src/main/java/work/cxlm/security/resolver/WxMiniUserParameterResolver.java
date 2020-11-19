package work.cxlm.security.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import work.cxlm.annotation.WxMiniUser;
import work.cxlm.exception.BadRequestException;
import work.cxlm.exception.ServiceException;
import work.cxlm.model.entity.User;
import work.cxlm.security.authentication.Authentication;
import work.cxlm.security.context.SecurityContextHolder;

/**
 * 解析 WxMiniUser 注解标记的 controller 方法参数
 * created 2020/11/17 22:26
 *
 * @author Chiru
 */
@Component
public class WxMiniUserParameterResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果该参数注解有 @WxMiniUser 且参数类型是 User
        return parameter.getParameterAnnotation(WxMiniUser.class) != null && parameter.getParameterType() == User.class;
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        WxMiniUser annotation = parameter.getParameterAnnotation(WxMiniUser.class);
        if (SecurityContextHolder.getContext().isAuthenticated()) {
            Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
            if (userAuthentication == null) {
                throw new ServiceException("从合法上下文接析出非法登录状态").setErrorData(SecurityContextHolder.getContext());
            }
            return userAuthentication.getUserDetail().getUser();
        }
        if (annotation != null && annotation.value()) {
            throw new BadRequestException("登录状态已过期，请重新登录");
        }
        return null;
    }
}
