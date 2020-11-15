package work.cxlm.model.enums.support;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

/**
 * created 2020/11/15 14:58
 *
 * @author Chiru
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {
    @Override
    @NonNull
    @SuppressWarnings("rawtypes, unchecked")
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter(targetType);
    }

    private static class StringToEnumConverter<T extends Enum<T>> implements Converter<String, T> {

        private final Class<T> enumType;

        StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            return Enum.valueOf(enumType, source.toUpperCase());
        }
    }
}
