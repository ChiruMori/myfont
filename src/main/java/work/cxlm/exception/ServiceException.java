package work.cxlm.exception;


import org.springframework.http.HttpStatus;

/**
 * @author cxlm
 * created 2020/10/19 14:34
 *
 * Service 层异常：内部服务器错误（500）
 */
public class ServiceException extends AbstractMyFontException {
    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
