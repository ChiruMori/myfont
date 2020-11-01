package work.cxlm.service.impl;

import cn.hutool.core.lang.Assert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import work.cxlm.model.dto.LogDTO;
import work.cxlm.model.entity.Log;
import work.cxlm.repository.LogRepository;
import work.cxlm.service.LogService;
import work.cxlm.service.base.AbstractCrudService;

/**
 * created 2020/10/29 15:30
 *
 * @author ryanwang
 * @author cxlm
 */
@Service
public class LogServiceImpl extends AbstractCrudService<Log, Long> implements LogService {

    public LogServiceImpl(LogRepository logRepository) {
        super(logRepository);
    }

    @Override
    public Page<LogDTO> pageLatest(int top) {
        Assert.isTrue(top > 0, "每页条目必须大于 0");

        // 按创建时间降序排序，并取第一页
        PageRequest latestPageable = PageRequest.of(0, top, Sort.by(Sort.Direction.DESC, "createTime"));
        // 查询出指定范围的日志，并将每个实体转化为 DTO
        return listAll(latestPageable).map(log -> new LogDTO().convertFrom(log));
    }
}
