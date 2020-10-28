package work.cxlm.exception;

import org.springframework.http.HttpStatus;

/**
 * created 2020/10/22 16:51
 *
 * @author cxlm
 */
public class BadRequestException extends AbstractMyFontException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
