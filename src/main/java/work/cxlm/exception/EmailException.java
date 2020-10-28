package work.cxlm.exception;

/**
 * @author cxlm
 * created 2020/10/19 14:28
 *
 * 邮件相关异常，没有特殊处理
 */
public class EmailException extends ServiceException {
    public EmailException(String msg) {
        super(msg);
    }

    public EmailException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
