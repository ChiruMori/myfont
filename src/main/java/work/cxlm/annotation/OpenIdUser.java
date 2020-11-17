package work.cxlm.annotation;

import java.lang.annotation.*;

/**
 * 仅限 Controller 类方法参数使用，添加本注解的 User 参数，会自动根据请求头中的 openId 参数解析用户
 * created 2020/11/17 22:11
 *
 * @author Chiru
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenIdUser {
}
