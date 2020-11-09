package work.cxlm.security.context;

import org.springframework.lang.Nullable;

/**
 * 处理用户凭证上下文相关的业务，静态工具类
 * created 2020/11/9 9:12
 *
 * @author johnniang
 * @author Chiru
 */
public class SecurityContextHandler {

    private final static ThreadLocal<SecurityContext> CONTEXT_HOLDER = new ThreadLocal<>();

    private SecurityContextHandler() {
    }

    /**
     * 获取安全上下文
     */
    public static SecurityContext getContext() {
        SecurityContext context = CONTEXT_HOLDER.get();
        if (context == null) {
            context = createEmptyContext();
            CONTEXT_HOLDER.set(context);
        }
        return context;
    }

    /**
     * 设置安全上下文
     */
    public static void setContext(@Nullable SecurityContext context) {
        CONTEXT_HOLDER.set(context);
    }

    /**
     * 清除安全上下文
     */
    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 创建一个无效授权的安全上下文
     */
    private static SecurityContext createEmptyContext() {
        return new SecurityContextImpl(null);
    }
}
