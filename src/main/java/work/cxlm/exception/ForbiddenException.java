package work.cxlm.exception;

import org.springframework.http.HttpStatus;

/**
 * created 2020/10/22 15:51
 *
 * @author johnniang
 * @author cxlm
 */
public class ForbiddenException extends AbstractMyFontException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
