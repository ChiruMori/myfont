package work.cxlm.service.impl;

import org.springframework.stereotype.Service;
import work.cxlm.model.entity.TimePeriod;
import work.cxlm.repository.TimeRepository;
import work.cxlm.service.TimeService;
import work.cxlm.service.base.AbstractCrudService;

/**
 * created 2020/11/16 23:07
 *
 * @author Chiru
 */
@Service
public class TimeServiceImpl extends AbstractCrudService<TimePeriod, Integer> implements TimeService {

    private final TimeRepository timeRepository;

    public TimeServiceImpl(TimeRepository timeRepository) {
        super(timeRepository);
        this.timeRepository = timeRepository;
    }
}
