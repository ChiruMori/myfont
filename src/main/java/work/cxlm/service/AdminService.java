package work.cxlm.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import work.cxlm.model.dto.StatisticDTO;
import work.cxlm.model.entity.User;
import work.cxlm.model.params.LoginParam;
import work.cxlm.model.params.ResetPasswordParam;
import work.cxlm.security.token.AuthToken;

/**
 * created 2020/10/21 14:49
 *
 * @author johnniang
 * @author ryanwang
 * @author cxlm
 */
@Service
public interface AdminService {
    /**
     * ACCESS TOKEN 时效
     */
    int ACCESS_TOKEN_EXPIRED_SECONDS = 24 * 3600;

    int REFRESH_TOKEN_EXPIRED_DAYS = 30;

    String LOG_PATH = "logs/font_run.log";

    /**
     * 通过用户表单验证用户名、密码
     *
     * @param loginParam 登录表单，不可为 null
     * @return User 用户实例
     */
    @NonNull
    User authenticate(@NonNull LoginParam loginParam);

    /**
     * 清除用户授权
     */
    void clearToken();

    /**
     * 向用户发送密码重设提示邮件
     *
     * @param param 重设密码表单，不可为 null
     */
    void sendResetPasswordCode(@NonNull ResetPasswordParam param);

    /**
     * 通过验证码重设密码
     *
     * @param param 重设密码表单，不可为 null
     */
    void resetPasswordByCode(@NonNull ResetPasswordParam param);

    /**
     * 获取系统统计数据
     *
     * @return 统计数据传输对象
     */
    @NonNull
    @Deprecated
    StatisticDTO getCount();

    /**
     * 刷新 token.
     *
     * @param refreshToken 新的 token
     * @return AuthToken 刷新后的实例
     */
    @NonNull
    AuthToken refreshToken(@NonNull String refreshToken);
}
