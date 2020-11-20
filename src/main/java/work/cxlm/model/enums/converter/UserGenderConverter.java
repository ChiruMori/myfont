package work.cxlm.model.enums.converter;

import work.cxlm.model.enums.UserGender;

import javax.persistence.Converter;

/**
 * UserGender 转换器
 * created 2020/11/20 18:59
 *
 * @author Chiru
 */
@Converter(autoApply = true)
public class UserGenderConverter extends AbstractConverter<UserGender, Integer> {

    public UserGenderConverter() {
        super(UserGender.class);
    }
}
