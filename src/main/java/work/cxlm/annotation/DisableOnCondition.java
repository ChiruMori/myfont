package work.cxlm.annotation;

import org.springframework.core.annotation.AliasFor;
import work.cxlm.model.enums.Mode;

import java.lang.annotation.*;

/**
 * 该注解可以限制某些条件下禁止访问api
 * created 2020/11/15 9:28
 *
 * @author guqing
 * @author Chiru
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DisableOnCondition {

    @AliasFor("mode")
    Mode value() default Mode.DEMO;
    @AliasFor("value")
    Mode mode() default Mode.DEMO;
}
