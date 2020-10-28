package work.cxlm.utils;

import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * created 2020/10/28 15:32
 *
 * @author johnniang
 * @author cxlm
 */
public class ServletUtils {
    private ServletUtils() {
    }

    /**
     * 获取当前的 Http 请求实体
     */
    public static Optional<HttpServletRequest> getCurrentRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> requestAttributes instanceof ServletRequestAttributes)
                .map(requestAttributes -> (ServletRequestAttributes) requestAttributes)
                .map(ServletRequestAttributes::getRequest);
    }

    /**
     * 获取当前请求的 IP
     *
     * @return 当前请求的 IP，或者 null
     */
    @Nullable
    public static String getRequestIp() {
        return getCurrentRequest().map(ServletUtil::getClientIP).orElse(null);
    }

    /**
     * 获取当前请求头中的某个值
     *
     * @return 指定的请求头值，可能为 null
     */
    @Nullable
    public static String getRequestHeader(String headerKey) {
        return getCurrentRequest().map(req -> ServletUtil.getHeaderIgnoreCase(req, headerKey)).orElse(null);
    }
}
