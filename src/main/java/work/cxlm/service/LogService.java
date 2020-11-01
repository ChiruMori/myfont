package work.cxlm.service;

import org.springframework.data.domain.Page;
import work.cxlm.model.dto.LogDTO;
import work.cxlm.model.entity.Log;
import work.cxlm.service.base.CrudService;

/**
 * created 2020/10/29 15:16
 *
 * @author cxlm
 */
public interface LogService extends CrudService<Log, Long> {

    /**
     * 列出最近的日志
     * @param top 条目数
     */
    Page<LogDTO> pageLatest(int top);
}
