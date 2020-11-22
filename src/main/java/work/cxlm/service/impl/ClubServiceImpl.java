package work.cxlm.service.impl;

import org.springframework.stereotype.Service;
import work.cxlm.model.entity.Club;
import work.cxlm.repository.ClubRepository;
import work.cxlm.service.ClubService;
import work.cxlm.service.base.AbstractCrudService;
import work.cxlm.service.base.CrudService;

/**
 * created 2020/11/21 15:24
 *
 * @author Chiru
 */
@Service
public class ClubServiceImpl extends AbstractCrudService<Club, Integer> implements ClubService {

    public ClubServiceImpl(ClubRepository repository) {
        super(repository);
    }
}
