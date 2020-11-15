package work.cxlm.security.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import work.cxlm.exception.AuthenticationException;
import work.cxlm.model.entity.User;
import work.cxlm.security.authentication.Authentication;
import work.cxlm.security.context.SecurityContextHolder;
import work.cxlm.security.support.UserDetail;

import java.util.Optional;

/**
 * created 2020/11/15 14:10
 *
 * @author johnniang
 * @author Chiru
 */
@Slf4j
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    public AuthenticationArgumentResolver() {
        log.debug("初始化登录凭证解析器");
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return Authentication.class.isAssignableFrom(parameterType)
                || UserDetail.class.isAssignableFrom(parameterType)
                || User.class.isAssignableFrom(parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        log.debug("解析并校验验证登录凭证");

        Class<?> parameterType = parameter.getParameterType();

        Authentication authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .orElseThrow(() -> new AuthenticationException("未登录"));

        if (Authentication.class.isAssignableFrom(parameterType)) {
            return authentication;
        } else if (UserDetail.class.isAssignableFrom(parameterType)) {
            return authentication.getUserDetail();
        } else if (User.class.isAssignableFrom(parameterType)) {
            return authentication.getUserDetail().getUser();
        }

        // Should never happen...
        throw new UnsupportedOperationException("未知的参数类型: " + parameterType);
    }
}
