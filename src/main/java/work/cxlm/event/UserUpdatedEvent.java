package work.cxlm.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * 用户更新事件（含创建）
 * created 2020/10/29 15:45
 *
 * @author johnniang
 * @author cxlm
 */
public class UserUpdatedEvent extends ApplicationEvent {

    private final Integer userId;

    public UserUpdatedEvent(Object source, @NonNull Integer userId) {
        super(source);
        Assert.notNull(userId, "用户 ID 不能为 null");
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}
