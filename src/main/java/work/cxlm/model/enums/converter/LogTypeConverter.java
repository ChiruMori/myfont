package work.cxlm.model.enums.converter;

import work.cxlm.model.enums.LogType;

import javax.persistence.Converter;

/**
 * LogType 的转换器
 * created 2020/11/20 18:56
 *
 * @author johnniang
 * @author Chiru
 */
@Converter(autoApply = true)
public class LogTypeConverter extends AbstractConverter<LogType, Integer> {

    public LogTypeConverter() {
        super(LogType.class);
    }
}
