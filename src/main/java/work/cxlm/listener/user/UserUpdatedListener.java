package work.cxlm.listener.user;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import work.cxlm.event.UserUpdatedEvent;
import work.cxlm.event.logger.LogEvent;
import work.cxlm.model.entity.Log;
import work.cxlm.service.LogService;

/**
 * 监听用户更新事件
 * created 2020/11/17 23:34
 *
 * @author Chiru
 */
public class UserUpdatedListener {

    private final LogService logService;

    public UserUpdatedListener(LogService logService) {
        this.logService = logService;
    }

    @Async
    @EventListener
    public void onApplicationEvent(UserUpdatedEvent logEvent) {
        // TODO： 处理用户更新事件
    }
}
