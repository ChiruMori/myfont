package work.cxlm.exception;

import org.springframework.http.HttpStatus;

/**
 * created 2020/10/21 11:35
 *
 * @author cxlm
 * @author johnniang
 */
public class NotFoundException extends AbstractMyFontException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
