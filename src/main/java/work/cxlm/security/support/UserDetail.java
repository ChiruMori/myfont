package work.cxlm.security.support;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import work.cxlm.model.entity.User;

/**
 * 用户详情，包装了用户对象
 * created 2020/11/9 9:27
 *
 * @author johnniang
 * @author Chiru
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {

    private User user;

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
