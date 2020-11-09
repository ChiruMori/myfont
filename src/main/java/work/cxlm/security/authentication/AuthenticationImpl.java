package work.cxlm.security.authentication;

import work.cxlm.security.support.UserDetail;

/**
 * created 2020/11/9 9:29
 *
 * @author Chiru
 * @author johnniang
 */
public class AuthenticationImpl implements Authentication {

    private final UserDetail userDetail;

    public AuthenticationImpl(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    @Override
    public UserDetail getUserDetail() {
        return userDetail;
    }
}
