package work.cxlm.security.handler;

import work.cxlm.exception.AbstractQfzsException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录凭证验证失败后的处理接口
 * created 2020/11/19 18:44
 *
 * @author Chiru
 */
public interface AuthenticationFailureHandler {

    /**
     * 当用户登录授权信息验证失败时调用此方法
     */
    void onFailure(HttpServletRequest request, HttpServletResponse response, AbstractQfzsException exception)
            throws IOException, ServletException;
}
