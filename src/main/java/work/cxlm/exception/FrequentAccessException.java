package work.cxlm.exception;

/**
 * 访问过于频繁
 * created 2020/11/3 23:04
 *
 * @author johnniang
 * @author cxlm
 */
public class FrequentAccessException extends BadRequestException {
    public FrequentAccessException(String message) {
        super(message);
    }

    public FrequentAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
