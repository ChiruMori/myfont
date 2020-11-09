package work.cxlm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import work.cxlm.model.OptionSimpleDTO;
import work.cxlm.model.dto.OptionDTO;
import work.cxlm.model.entity.Option;
import work.cxlm.model.enums.ValueEnum;
import work.cxlm.model.params.OptionParam;
import work.cxlm.model.params.OptionQuery;
import work.cxlm.model.properties.PropertyEnum;
import work.cxlm.service.base.CrudService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * created 2020/11/9 15:11
 * TODO: 本接口应该还有一些冗余的项和缺少的项，后续需完善
 *
 * @author johnniang
 * @author ryanwang
 * @author Chiru
 */
public interface OptionService extends CrudService<Option, Integer> {

    String OPTION_KEY = "options";

    /**
     * 批量保存配置项
     */
    @Transactional
    void save(@Nullable Map<String, Object> options);

    /**
     * 批量保存配置项
     */
    @Transactional
    void save(@Nullable List<OptionParam> optionParams);

    /**
     * 保存单个配置项
     */
    @Transactional
    void save(@Nullable OptionParam param);

    /**
     * 更新单个配置项
     */
    void update(@NonNull Integer optionId, @NonNull OptionParam optionParam);

    /**
     * 保存一个属性值
     */
    @Transactional
    void saveProperty(@NonNull PropertyEnum property, @Nullable String value);

    /**
     * 保存一组属性
     */
    void saveProperties(@NonNull Map<? extends PropertyEnum, String> properties);

    /**
     * 根据给出的 key 列表列出一组属性
     */
    Map<String, Object> listOptions(@Nullable List<String> keys);

    /**
     * 列出全部配置项的 DTO
     */
    List<OptionDTO> listDTOs();

    /**
     * 查询指定分页的配置项 DTO
     */
    Page<OptionSimpleDTO> pageDTOsBy(@NonNull Pageable pageable, OptionQuery optionQuery);

    /**
     * 永久移除配置项
     */
    @NonNull
    Option removePermanently(@NonNull Integer id);

    /**
     * 通过键寻找配置项，可能得到 null
     */
    @Nullable
    Object getByKeyOfNullable(@NonNull String key);

    /**
     * 通过键寻找配置项，不会得到 null
     */
    @NonNull
    Object getByKeyOfNonNull(@NonNull String key);

    /**
     * 通过属性查找配置项，可能得到 null
     */
    Object getByPropertyOfNullable(@NonNull PropertyEnum property);

    /**
     * 通过属性查找配置项，不会得到 null
     */
    Object getByPropertyOfNonNull(@NonNull PropertyEnum property);

    /**
     * 通过属性查找配置项，得到 Optional 包装的结果
     */
    Optional<Object> getByProperty(@NonNull PropertyEnum property);

    /**
     * 通过属性查找配置项，同时指定返回值类型，得到 Optional 包装的结果
     */
    <T> Optional<T> getByProperty(@NonNull PropertyEnum property, @NonNull Class<T> type);

    /**
     * 通过属性查找配置项，并指定返回值类型和默认值
     */
    <T> T getByPropertyOrDefault(@NonNull PropertyEnum property, @NonNull Class<T> type, T defaultValue);

    /**
     * 通过属性查找配置项，并指定返回值类型，使用内置的默认值
     */
    <T> T getByPropertyOrDefault(@NonNull PropertyEnum property, @NonNull Class<T> type);

    /**
     * 通过键查找配置项的值，同时指定默认值
     */
    <T> T getByKeyOrDefault(@NonNull String key, @NonNull Class<T> valueType, T defaultValue);

    /**
     * 通过键查找配置项的值，同时指定返回值类型，得到 Optional 包装的结果
     */
    <T> Optional<T> getByKey(@NonNull String key, @NonNull Class<T> valueType);

    /**
     * 通过键查找枚举类型配置项的值，同时指定返回值类型，得到 Optional 包装的结果
     */
    <T extends Enum<T>> Optional<T> getEnumByProperty(@NonNull PropertyEnum property, @NonNull Class<T> valueType);

    /**
     * 通过键查找枚举类型配置项的值，同时指定返回值类型、默认值，得到 Optional 包装的结果
     */
    <T extends Enum<T>> T getEnumByPropertyOrDefault(@NonNull PropertyEnum property, @NonNull Class<T> valueType, T defaultValue);

    /**
     * 通过键查找枚举类型配置项的值（ValueEnum 类实例），同时指定返回值类型、枚举类类型，得到 Optional 包装的结果
     */
    <T, E extends ValueEnum<T>> Optional<E> getValueEnumByPropertyOrDefault(@NonNull PropertyEnum property, @NonNull Class <T> valueType, @NonNull Class<E> enumType);

    int getPostPageSize();

    int getArchivesPageSize();

    int getResPageSize();

}
