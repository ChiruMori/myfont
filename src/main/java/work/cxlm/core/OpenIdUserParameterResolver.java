package work.cxlm.core;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import work.cxlm.annotation.OpenIdUser;
import work.cxlm.cache.AbstractStringCacheStore;
import work.cxlm.model.entity.User;

import javax.servlet.http.HttpServletRequest;

import static work.cxlm.model.support.QfzsConst.USER_CACHE_PREFIX;

/**
 * 解析 OpenIdUser 注解标记的 controller 方法参数
 * created 2020/11/17 22:26
 *
 * @author Chiru
 */
@Component
public class OpenIdUserParameterResolver implements HandlerMethodArgumentResolver {

    private final AbstractStringCacheStore cacheStore;

    public OpenIdUserParameterResolver(AbstractStringCacheStore cacheStore) {
        this.cacheStore = cacheStore;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果该参数注解有 @OpenIdUser 且参数类型是 User
        return parameter.getParameterAnnotation(OpenIdUser.class) != null && parameter.getParameterType() == User.class;
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        // 取得 HttpServletRequest
        HttpServletRequest request= (HttpServletRequest) webRequest.getNativeRequest();
        String openId = request.getParameter("openId");
        if (StringUtils.isEmpty(openId)) {
            return null;
        }
        return cacheStore.getAny(USER_CACHE_PREFIX + openId, User.class).orElse(null);
    }
}
