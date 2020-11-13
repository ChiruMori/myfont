package work.cxlm.exception;

/**
 * created 2020/11/12 16:52
 *
 * @author johnniang
 * @author Chiru
 */
public class MissingPropertyException extends BadRequestException {

    public MissingPropertyException(String msg) {
        super(msg);
    }

    public MissingPropertyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
