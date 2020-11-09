package work.cxlm.utils;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 供 Service 使用的静态工具类
 * created 2020/11/9 22:12
 *
 * @author johnniang
 * @author Chiru
 */
public class ServiceUtils {

    private ServiceUtils() {
    }

    /**
     * 提取一组数据的 ID 到集合中
     *
     * @param entities        原始数据
     * @param convertFunction 从原始数据中提取 ID 的函数
     * @param <ID>            ID 类型参数
     * @param <T>             原始数据类型参数
     */
    @NonNull
    public static <ID, T> Set<ID> fetchIdOfDataCollections(Collection<T> entities, @NonNull Function<T, ID> convertFunction) {
        Assert.notNull(convertFunction, "ID 转换函数不能为 null");
        return CollectionUtils.isEmpty(entities) ?
                Collections.emptySet() :
                entities.stream().map(convertFunction).collect(Collectors.toSet());
    }

    /**
     * 将列表中的数据转化为 ID 映射的 Map
     *
     * @param ids             id 集合，确保在结果 Map 中，集合内的 ID 一定对应着 List，注意：当 entities 中没有对应的 ID 时，对应的 List 是一个空列表
     * @param entities        被整理的实例列表
     * @param convertFunction 将实例转化为 ID 的函数
     * @param <ID>            ID 类型参数
     * @param <ENTITY>        实例的类型函数
     * @return （每个 ID 都会对应一个只有一个元素的列表）的 Map
     */
    @NonNull
    public static <ID, ENTITY> Map<ID, List<ENTITY>> convertToListMap(Collection<ID> ids, List<ENTITY> entities,
                                                                      @NonNull Function<ENTITY, ID> convertFunction) {
        Assert.notNull(convertFunction, "ID 转换函数不能为 null");
        if (CollectionUtils.isEmpty(ids) || CollectionUtils.isEmpty(entities)) {
            return Collections.emptyMap();
        }

        Map<ID, List<ENTITY>> res = new HashMap<>();
        // 整理数据，每个 ID 都会对应一个只有一个元素的列表
        entities.forEach(e -> res.computeIfAbsent(convertFunction.apply(e), id -> new LinkedList<>())
                .add(e));
        // 为确保所有的 id 都对应有元素，将不存在 entity 的 id 的值设置为空列表
        ids.forEach(id -> res.putIfAbsent(id, Collections.emptyList()));

        return res;
    }

    // TODO: ConvertToMap 及之后的工具方法

}
