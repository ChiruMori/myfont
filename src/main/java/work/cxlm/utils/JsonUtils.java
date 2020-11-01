package work.cxlm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * created 2020/11/1 16:12
 *
 * @author johnniang
 * @author cxlm
 */
public class JsonUtils {

    public static final ObjectMapper DEFAULT_JSON_MAPPER = createDefaultJsonMapper();

    /**
     * 创建一个默认的 JsonMapper
     */
    private static ObjectMapper createDefaultJsonMapper() {
        return createDefaultJsonMapper(null);
    }

    /**
     * 根据指定的属性命名策略创建一个 ObjectMapper
     *
     * @param strategy 指定的属性命名策略
     */
    public static ObjectMapper createDefaultJsonMapper(@Nullable PropertyNamingStrategy strategy) {
        ObjectMapper mapper = new ObjectMapper();
        // 配置 mapper，遇到未知属性时直接抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 配置属性配置命名策略
        if (strategy != null) {
            mapper.setPropertyNamingStrategy(strategy);
        }
        return mapper;
    }

    /**
     * 将 json 字符串转为指定类型的实例
     *
     * @param json json 字符串
     * @param type 目标类型
     * @param <T>  目标类型泛型
     * @return 转化后的实例
     * @throws JsonProcessingException 如果转化出错则抛出
     */
    public static <T> T jsonToObject(@NonNull String json, @NonNull Class<T> type) throws JsonProcessingException {
        return jsonToObject(json, type, DEFAULT_JSON_MAPPER);
    }

    /**
     * 将 json 字符串转为指定类型的实例
     *
     * @param json         json 字符串
     * @param type         目标类型
     * @param <T>          目标类型泛型
     * @param objectMapper 转化时使用的 objectMapper
     * @return 转化后的实例
     * @throws JsonProcessingException 如果转化出错则抛出
     */
    public static <T> T jsonToObject(@NonNull String json, @NonNull Class<T> type, @NonNull ObjectMapper objectMapper) throws JsonProcessingException {
        Assert.hasText(json, "json 字符串不能为空或 null");
        Assert.notNull(type, "类型参数不能为 null");
        Assert.notNull(objectMapper, "ObjectMapper 不能为 null");

        return objectMapper.readValue(json, type);
    }

    /**
     * 将对象转化为 json 字符串
     *
     * @param src 源对象
     * @return 转化后的 json 字符串
     * @throws JsonProcessingException 转化失败时抛出
     */
    public static String objectToJson(@NonNull Object src) throws JsonProcessingException {
        return objectToJson(src, DEFAULT_JSON_MAPPER);
    }

    /**
     * 将对象转化为 json 字符串
     *
     * @param src          源对象
     * @param objectMapper 使用的 ObjectMapper 实例
     * @return 转化后的 json 字符串
     * @throws JsonProcessingException 转化失败时抛出
     */
    public static String objectToJson(@NonNull Object src, @NonNull ObjectMapper objectMapper) throws JsonProcessingException {
        Assert.notNull(src, "源对象不能为 null");
        Assert.notNull(objectMapper, "objectMapper 不能为 null");

        return objectMapper.writeValueAsString(src);
    }

    /**
     * 将一个 map 转化为指定类型的实例
     *
     * @param srcMap 源 Map
     * @param type   指定的类型（Class 实例）
     * @param <T>    指定类型（泛型）
     * @return 转化后的实例
     * @throws JsonProcessingException 转化失败时抛出
     */
    public static <T> T mapToObject(@NonNull Map<String, ?> srcMap, @NonNull Class<T> type) throws JsonProcessingException {
        return mapToObject(srcMap, type, DEFAULT_JSON_MAPPER);
    }

    /**
     * 将一个 map 转化为指定类型的实例
     *
     * @param srcMap 源 Map
     * @param type   指定的类型（Class 实例）
     * @param <T>    指定类型（泛型）
     * @param mapper 使用的 ObjectMapper 实例
     * @return 转化后的实例
     * @throws JsonProcessingException 转化失败时抛出
     */
    public static <T> T mapToObject(@NonNull Map<String, ?> srcMap, @NonNull Class<T> type, @NonNull ObjectMapper mapper) throws JsonProcessingException {
        Assert.notEmpty(srcMap, "源 map 必须包含元素");
        Assert.notNull(type, "目标类型不能为 null");
        Assert.notNull(mapper, "ObjectMapper 实例不能为 null");

        String json = objectToJson(srcMap, mapper);
        return jsonToObject(json, type, mapper);
    }

    /**
     * 将一个对象转化为 Map 实例
     *
     * @param src 源对象
     * @return 转化后的 Map 实例
     * @throws JsonProcessingException 转化失败时抛出
     */
    public static Map<?, ?> objectToMap(@NonNull Object src) throws JsonProcessingException {
        return objectToMap(src, DEFAULT_JSON_MAPPER);
    }

    /**
     * 将一个对象转化为 Map 实例
     *
     * @param src    源对象
     * @param mapper 转化时使用的 ObjectMapper 实例
     * @return 转化后的 Map 实例
     * @throws JsonProcessingException 转化失败时抛出
     */
    public static Map<?, ?> objectToMap(@NonNull Object src, @NonNull ObjectMapper mapper) throws JsonProcessingException {
        Assert.notNull(src, "源对象不能为 null");
        Assert.notNull(mapper, "ObjectMapper 实例不能为 null");

        String json = objectToJson(src, mapper);
        return jsonToObject(json, Map.class, mapper);
    }
}
