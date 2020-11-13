package work.cxlm.model.properties;

/**
 * 邮件相关配置
 * created 2020/11/13 17:14
 *
 * @author johnniang
 * @author Chiru
 */
public enum EmailProperties implements PropertyEnum {

    HOST("email_host", String.class, ""),  // 服务器
    PROTOCOL("email_protocol", String.class, "smtp"),  // 协议
    SSL_PORT("email_ssl_port", Integer.class, "465"),  // 端口
    USERNAME("email_username", String.class, ""),  // 邮件用户名
    PASSWORD("email_password", String.class, ""),  // 邮件密码
    FROM_NAME("email_from_name", String.class, "");  // 发件人

    private final String value;

    private final Class<?> type;

    private final String defaultValue;

    EmailProperties(String value, Class<?> type, String defaultValue) {
        this.defaultValue = defaultValue;
        if (!PropertyEnum.isSupportedType(type)) {
            throw new IllegalArgumentException("Unsupported blog property type: " + type);
        }

        this.value = value;
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String defaultValue() {
        return defaultValue;
    }

    @Override
    public String getValue() {
        return value;
    }
}
