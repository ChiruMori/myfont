package work.cxlm.model.enums.converter;

import work.cxlm.model.enums.ValueEnum;

import javax.persistence.AttributeConverter;

/**
 * 泛型到实体的转换器
 * created 2020/11/20 17:35
 *
 * @param <E> enum generic
 * @param <V> value generic
 * @author johnniang
 * @author Chiru
 */
public abstract class AbstractConverter<E extends ValueEnum<V>, V> implements AttributeConverter<E, V> {

    private final Class<E> enumType;

    protected AbstractConverter(Class<E> enumType) {
        this.enumType = enumType;
    }

    @Override
    public V convertToDatabaseColumn(E attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public E convertToEntityAttribute(V dbData) {
        return dbData == null ? null : ValueEnum.valueToEnum(enumType, dbData);
    }
}
