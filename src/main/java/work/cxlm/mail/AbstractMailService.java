package work.cxlm.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import work.cxlm.exception.EmailException;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Abstract mail service.
 *
 * @author johnniang
 * @author cxlm
 */
@Slf4j
public abstract class AbstractMailService implements MailService {

    private static final int DEFAULT_POOL_SIZE = 5;
    @Nullable
    private ExecutorService executorService;

    private JavaMailSender sender;

    @Value("${spring.mail.from}")
    private String from;

    @Resource
    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @NonNull
    public ExecutorService getExecutorService() {
        if (this.executorService == null) {
            this.executorService = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
        }
        return executorService;
    }

    /**
     * Test connection with email server.
     */
    @Override
    public void testConnection() {
        if (sender instanceof JavaMailSenderImpl) {
            JavaMailSenderImpl mailSender = (JavaMailSenderImpl) sender;
            try {
                mailSender.testConnection();
            } catch (MessagingException e) {
                throw new EmailException("无法连接到邮箱服务器，请检查邮箱配置.[" + e.getMessage() + "]", e);
            }
        }
    }

    /**
     * 发送模板邮件，需要同时传递回调函数
     *
     * @param callback mime message callback.
     */
    protected void sendMailTemplate(@Nullable Callback callback) {
        if (callback == null) {
            log.info("回调函数为空，终止发送");
            return;
        }

        // create mime message helper
        MimeMessageHelper messageHelper = new MimeMessageHelper(sender.createMimeMessage());

        try {
            // set from-name
            messageHelper.setFrom(getFromAddress());
            // handle message set separately
            callback.handle(messageHelper);

            // get mime message
            MimeMessage mimeMessage = messageHelper.getMimeMessage();
            // send email
            sender.send(mimeMessage);

            log.info("发送邮件到 [{}] 成功, 主题: [{}], 数据: [{}]",
                    Arrays.toString(mimeMessage.getAllRecipients()),
                    mimeMessage.getSubject(),
                    mimeMessage.getSentDate());
        } catch (Exception e) {
            throw new EmailException("邮件发送失败，请检查 SMTP 服务配置是否正确", e);
        }
    }

    private synchronized InternetAddress getFromAddress() throws UnsupportedEncodingException {
        if (sender instanceof JavaMailSenderImpl) {
            // get user name(email)
            JavaMailSenderImpl mailSender = (JavaMailSenderImpl) sender;

            // build internet address
            return new InternetAddress(mailSender.getUsername(), from, mailSender.getDefaultEncoding());
        }

        throw new UnsupportedOperationException("Unsupported java mail sender: " + sender.getClass().getName());
    }

    /**
     * 发送模板邮件
     *
     * @param callback   callback message handler
     * @param tryToAsync 是否使用线程池（并行发送）
     */
    protected void sendMailTemplate(boolean tryToAsync, @Nullable Callback callback) {
        ExecutorService executorService = getExecutorService();
        if (tryToAsync) {
            // send mail asynchronously
            executorService.execute(() -> sendMailTemplate(callback));
        } else {
            // send mail synchronously
            sendMailTemplate(callback);
        }
    }

    /**
     * Message callback.
     */
    protected interface Callback {
        /**
         * Handle message set.
         *
         * @param messageHelper mime message helper
         * @throws Exception if something goes wrong
         */
        void handle(@NonNull MimeMessageHelper messageHelper) throws Exception;
    }
}
