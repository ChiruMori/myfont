package work.cxlm.model.enums;

/**
 * created 2020/11/18 20:12
 *
 * @author Chiru
 */
public enum UserRole implements ValueEnum<Integer> {

    /**
     * 普通用户
     */
    NORMAL(0),

    /**
     * 社团管理员
     */
    CLUB_ADMIN(1),

    /**
     * 系统管理员
     */
    SYSTEM_ADMIN(2);

    private final Integer value;

    UserRole(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * 判断当前角色是否为管理员角色
     */
    public boolean isAdminRole() {
        return this == CLUB_ADMIN || this == SYSTEM_ADMIN;
    }
}
