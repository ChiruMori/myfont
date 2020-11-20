package work.cxlm.model.enums.converter;

import work.cxlm.model.enums.UserRole;

import javax.persistence.Converter;

/**
 * UserRole 泛型转换器
 * created 2020/11/20 19:00
 *
 * @author Chiru
 */
@Converter(autoApply = true)
public class UserRoleConverter extends AbstractConverter<UserRole, Integer> {

    public UserRoleConverter() {
        super(UserRole.class);
    }
}
