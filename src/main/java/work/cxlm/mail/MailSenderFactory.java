package work.cxlm.mail;

import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Properties;

/**
 * created 2020/11/13 16:48
 *
 * @author johnniang
 * @author Chiru
 */
public class MailSenderFactory {

    /**
     * 通过给定的参数创造 MailSender 实例
     */
    public static JavaMailSender getMailSender(@NonNull MailProperties mailProperties) {
        Assert.notNull(mailProperties, "邮箱配置参数对象不能为 null");
        // 邮件发送对象
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // 设置参数
        setProperties(mailSender, mailProperties);

        return mailSender;
    }

    private static void setProperties(@NonNull JavaMailSenderImpl javaMailSender, @NonNull MailProperties mailProperties) {
        javaMailSender.setHost(mailProperties.getHost());
        javaMailSender.setPort(mailProperties.getPort());
        javaMailSender.setUsername(mailProperties.getUsername());
        javaMailSender.setPassword(mailProperties.getPassword());
        javaMailSender.setProtocol(mailProperties.getProtocol());
        javaMailSender.setDefaultEncoding(mailProperties.getDefaultEncoding().name());
        if (!CollectionUtils.isEmpty(mailProperties.getProperties())) {
            Properties properties = new Properties();
            properties.putAll(mailProperties.getProperties());
            javaMailSender.setJavaMailProperties(properties);
        }
    }

}
