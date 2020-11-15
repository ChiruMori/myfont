package work.cxlm.model.enums;

/**
 * created 2020/10/22 17:25
 *
 * @author cxlm
 */
public enum LogType implements ValueEnum<Integer> {

    /**
     * 系统完成初始化（安装）
     */
    SYSTEM_INSTALLED(99),

    /**
     * 发布了新的字
     */
    KANJI_PUBLISHED(0),

    /**
     * 删除了某个字
     */
    KANJI_DELETED(1),

    /**
     * 用户登录
     */
    LOGGED_IN(10),

    /**
     * 登录失败
     */
    LOGGED_FAILED(11),

    /**
     * 登出
     */
    LOGGED_OUT(12),

    /**
     * 修改用户密码
     */
    PWD_UPDATED(20),
    ;

    private final Integer value;

    LogType(Integer value) {
        this.value = value;
    }


    @Override
    public Integer getValue() {
        return value;
    }
}
