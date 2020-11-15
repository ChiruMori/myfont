package work.cxlm.exception;

import org.springframework.http.HttpStatus;

/**
 * created 2020/11/15 14:19
 *
 * @author johnniang
 * @author Chiru
 */
public class AuthenticationException extends AbstractMyFontException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
