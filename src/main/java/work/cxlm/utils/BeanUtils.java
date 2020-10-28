package work.cxlm.utils;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import work.cxlm.exception.BeanUtilsException;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Bean 工具类，静态类
 * created 2020/10/22 13:49
 *
 * @author johnniang
 * @author cxlm
 */
public class BeanUtils {

    private BeanUtils() {
    }

    /**
     * 从当前对象生成目标类型的对象，仅拷贝属性
     *
     * @param source      源对象
     * @param targetClass 目标类对象，不能为 null
     * @param <T>         目标类类型参数
     * @return 从源对象生成的实例，如果源对象为 null 则返回 null
     * @throws BeanUtilsException 创建过程中遇到错误时抛出
     */
    @Nullable
    public static <T> T transformFrom(@Nullable Object source, @NonNull Class<T> targetClass) {
        Assert.notNull(targetClass, "目标类对象不能为 null");

        if (source == null) {
            return null;
        }

        // 构建实例
        try {
            // 从目标类对象创建一个实例
            T targetInstance = targetClass.newInstance();
            // 拷贝属性
            org.springframework.beans.BeanUtils.copyProperties(source, targetInstance, getNullPropertyNames(source));
            // 返回构造的实例
            return targetInstance;
        } catch (Exception e) {
            throw new BeanUtilsException("创建 " + targetClass.getName() + " 的实例或拷贝属性失败", e);
        }
    }

    /**
     * 更新属性
     *
     * @param source 源对象，不能为 null
     * @param target 目标对象，不能为 null
     * @throws BeanUtilsException 拷贝过程出现错误时抛出
     */
    public static void updateProperties(@NonNull Object source, @NonNull Object target) {
        Assert.notNull(source, "源对象不能为 null");
        Assert.notNull(target, "目标对象不能为 null");

        // 将非空属性从源对象拷贝到目标对象
        try {
            org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        } catch (BeansException e) {
            throw new BeanUtilsException("拷贝失败", e);
        }
    }

    /**
     * 获取空值属性名数组
     *
     * @param source 源对象，不能为 null
     */
    @NonNull
    private static String[] getNullPropertyNames(@NonNull Object source) {
        return getNullPropertyNameSet(source).toArray(new String[0]);
    }

    /**
     * 获取空值属性名集合
     *
     * @param source 源对象，不能为 null
     */
    @NonNull
    private static Set<String> getNullPropertyNameSet(@NonNull Object source) {

        Assert.notNull(source, "源对象不能为 null");
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            // if property value is equal to null, add it to empty name set
            if (propertyValue == null) {
                emptyNames.add(propertyName);
            }
        }

        return emptyNames;
    }
}
