package work.cxlm.utils;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import javax.validation.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用于校验实体字段合法性的工具类
 * created 2020/10/27 15:02
 *
 * @author cxlm
 */
public class ValidationUtils {

    private static volatile Validator VALIDATOR;

    private ValidationUtils() {
    }

    /**
     * 双重校验锁单例，获取校验工具实例
     */
    @NonNull
    public static Validator getValidator() {
        if (VALIDATOR == null) {
            synchronized (ValidationUtils.class) {
                if (VALIDATOR == null) {
                    VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
                }
            }
        }

        return VALIDATOR;
    }

    /**
     * 手动校验 bean
     *
     * @param obj    需要校验的 bean
     * @param groups 校验类集合
     * @throws ConstraintViolationException 如果校验失败则抛出
     */
    public static void validate(Object obj, Class<?>... groups) {
        Validator validator = getValidator();

        if (obj instanceof Iterable) {
            validate((Iterable<?>) obj, groups);
        } else {
            final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj, groups);
            if (!CollectionUtils.isEmpty(constraintViolations)) { // 含有未通过校验的字段
                throw new ConstraintViolationException(constraintViolations);
            }
        }
    }

    /**
     * 校验可迭代类型
     *
     * @param objs   待校验的可迭代实例
     * @param groups 校验类集合
     */
    public static void validate(@Nullable Iterable<?> objs, @Nullable Class<?>... groups) {
        if (objs == null) {
            return;
        }

        Validator validator = getValidator();
        AtomicInteger wrappedIndex = new AtomicInteger(0);
        final Set<ConstraintViolation<?>> allViolations = new LinkedHashSet<>();
        objs.forEach(obj -> {
            int useIndex = wrappedIndex.getAndIncrement();
            Set<? extends ConstraintViolation<?>> violations = validator.validate(obj, groups);
            violations.forEach(v -> {
                // 为指定的验证设置迭代索引
                final Path path = v.getPropertyPath();
                if (path instanceof PathImpl) {
                    PathImpl path1 = (PathImpl) path;
                    path1.makeLeafNodeIterableAndSetIndex(useIndex);
                }
                allViolations.add(v);
            });
        });

        if (!CollectionUtils.isEmpty(allViolations)) {
            throw new ConstraintViolationException(allViolations);
        }
    }
}
