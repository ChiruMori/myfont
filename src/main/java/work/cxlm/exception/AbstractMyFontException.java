package work.cxlm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author cxlm
 * created 2020/10/19 14:29
 */
public abstract class AbstractMyFontException extends RuntimeException {

    private Object errData;

    public AbstractMyFontException(String msg) {
        super(msg);
    }

    public AbstractMyFontException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 获取 HTTP 状态，需要在子类实现
     */
    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errData;
    }

    /**
     * 设置详细错误数据
     */
    @NonNull
    public AbstractMyFontException setErrorData(@Nullable Object errData) {
        this.errData = errData;
        return this;
    }

}
