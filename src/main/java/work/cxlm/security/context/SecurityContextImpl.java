package work.cxlm.security.context;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import work.cxlm.security.authentication.Authentication;

/**
 * 登录授权类（Authentication）的包装类
 * created 2020/11/9 9:38
 *
 * @author johnniang
 * @author Chiru
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SecurityContextImpl implements SecurityContext {

    private Authentication authentication;

    @Override
    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
