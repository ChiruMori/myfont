package work.cxlm.cache.lock;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存锁注解
 * created 2020/10/29 15:50
 *
 * @author johnniang
 * @author cxlm
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {

    /**
     * 缓存前缀
     */
    @AliasFor("value")
    String prefix() default "";

    /**
     * prefix 的别名
     */
    @AliasFor("prefix")
    String value() default "";

    /**
     * 缓存过期时间，默认 5
     */
    long expired() default 5;

    /**
     * 过期时间单位，默认秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 定界符
     */
    String delimiter() default ":";

    /**
     * 调用方法后是否自动清除缓存
     */
    boolean autoDelete() default true;

    /**
     * 是否回溯请求信息
     */
    boolean traceRequest() default false;
}
