package work.cxlm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import work.cxlm.cache.AbstractStringCacheStore;
import work.cxlm.config.MyFontProperties;
import work.cxlm.model.OptionSimpleDTO;
import work.cxlm.model.dto.OptionDTO;
import work.cxlm.model.entity.Option;
import work.cxlm.model.enums.ValueEnum;
import work.cxlm.model.params.OptionParam;
import work.cxlm.model.params.OptionQuery;
import work.cxlm.model.properties.PropertyEnum;
import work.cxlm.repository.BaseRepository;
import work.cxlm.repository.OptionRepository;
import work.cxlm.service.OptionService;
import work.cxlm.service.base.AbstractCrudService;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * created 2020/11/9 20:53
 *
 * @author Chiru
 */
@Slf4j
@Service
public class OptionServiceImpl extends AbstractCrudService<Option, Integer> implements OptionService {

    private final OptionRepository optionRepository;
    private final ApplicationContext applicationContext;
    private final AbstractStringCacheStore cacheStore;
    private final ApplicationEventPublisher eventPublisher;
    private final MyFontProperties myFontProperties;

    private final Map<String, PropertyEnum> propertyEnumMap;

    protected OptionServiceImpl(OptionRepository optionRepository,
                                ApplicationContext applicationContext,
                                AbstractStringCacheStore cacheStore,
                                ApplicationEventPublisher eventPublisher,
                                MyFontProperties myFontProperties) {
        super(optionRepository);
        this.optionRepository = optionRepository;
        this.applicationContext = applicationContext;
        this.cacheStore = cacheStore;
        this.eventPublisher = eventPublisher;
        this.myFontProperties = myFontProperties;

        propertyEnumMap = Collections.unmodifiableMap(PropertyEnum.getValuePropertyEnumType());
    }

    @Override
    @Transactional
    public void save(Map<String, Object> options) {
        if (CollectionUtils.isEmpty(options)) {
            return;
        }

        // FIXME: 使用 ServiceUtils 进行数据整理
        Map<String, Option> oldOptionMap = null;
    }

    @Override
    public void save(List<OptionParam> optionParams) {

    }

    @Override
    public void save(OptionParam param) {

    }

    @Override
    public void update(Integer optionId, OptionParam optionParam) {

    }

    @Override
    public void saveProperty(PropertyEnum property, String value) {

    }

    @Override
    public void saveProperties(Map<? extends PropertyEnum, String> properties) {

    }

    @Override
    public Map<String, Object> listOptions(List<String> keys) {
        return null;
    }

    @Override
    public List<OptionDTO> listDTOs() {
        return null;
    }

    @Override
    public Page<OptionSimpleDTO> pageDTOsBy(Pageable pageable, OptionQuery optionQuery) {
        return null;
    }

    @Override
    public Option removePermanently(Integer id) {
        return null;
    }

    @Override
    public Object getByKeyOfNullable(String key) {
        return null;
    }

    @Override
    public Object getByKeyOfNonNull(String key) {
        return null;
    }

    @Override
    public Object getByPropertyOfNullable(PropertyEnum property) {
        return null;
    }

    @Override
    public Object getByPropertyOfNonNull(PropertyEnum property) {
        return null;
    }

    @Override
    public Optional<Object> getByProperty(PropertyEnum property) {
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> getByProperty(PropertyEnum property, Class<T> type) {
        return Optional.empty();
    }

    @Override
    public <T> T getByPropertyOrDefault(PropertyEnum property, Class<T> type, T defaultValue) {
        return null;
    }

    @Override
    public <T> T getByPropertyOrDefault(PropertyEnum property, Class<T> type) {
        return null;
    }

    @Override
    public <T> T getByKeyOrDefault(String key, Class<T> valueType, T defaultValue) {
        return null;
    }

    @Override
    public <T> Optional<T> getByKey(String key, Class<T> valueType) {
        return Optional.empty();
    }

    @Override
    public <T extends Enum<T>> Optional<T> getEnumByProperty(PropertyEnum property, Class<T> valueType) {
        return Optional.empty();
    }

    @Override
    public <T extends Enum<T>> T getEnumByPropertyOrDefault(PropertyEnum property, Class<T> valueType, T defaultValue) {
        return null;
    }

    @Override
    public <T, E extends ValueEnum<T>> Optional<E> getValueEnumByPropertyOrDefault(PropertyEnum property, Class<T> valueType, Class<E> enumType) {
        return Optional.empty();
    }

    @Override
    public int getPostPageSize() {
        return 0;
    }

    @Override
    public int getArchivesPageSize() {
        return 0;
    }

    @Override
    public int getResPageSize() {
        return 0;
    }
}
