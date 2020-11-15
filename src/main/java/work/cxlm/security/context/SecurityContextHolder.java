package work.cxlm.security.context;

import org.springframework.lang.Nullable;

/**
 * created 2020/11/15 14:14
 *
 * @author johnniang
 * @author Chiru
 */
public class SecurityContextHolder {

    private final static ThreadLocal<SecurityContext> CONTEXT_HOLDER = new ThreadLocal<>();

    private SecurityContextHolder(){}

    public static SecurityContext getContext() {
        SecurityContext context = CONTEXT_HOLDER.get();
        if (context == null) {
            context = createEmptyContext();
            CONTEXT_HOLDER.set(context);
        }
        return context;
    }

    public static void setContext(@Nullable SecurityContext context) {
        CONTEXT_HOLDER.set(context);
    }

    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    private static SecurityContext createEmptyContext() {
        return new SecurityContextImpl(null);
    }
}
