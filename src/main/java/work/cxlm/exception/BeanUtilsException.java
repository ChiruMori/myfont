package work.cxlm.exception;

import org.springframework.http.HttpStatus;

/**
 * BeanUtils 转换过程中的异常
 * created 2020/10/22 13:55
 *
 * @author johnniang
 * @author cxlm
 */
public class BeanUtilsException extends AbstractMyFontException {

    public BeanUtilsException(String message) {
        super(message);
    }

    public BeanUtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
