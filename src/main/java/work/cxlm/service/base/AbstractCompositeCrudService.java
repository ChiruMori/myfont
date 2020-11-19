package work.cxlm.service.base;

import org.springframework.data.domain.Page;
import work.cxlm.repository.CompositeRepository;

import org.springframework.data.domain.Pageable;

/**
 * created 2020/11/18 13:04
 *
 * @author Chiru
 */
public abstract class AbstractCompositeCrudService<DOMAIN, ID> extends AbstractCrudService<DOMAIN, ID> implements CompositeCrudService<DOMAIN, ID> {

    CompositeRepository<DOMAIN, ID> compositeRepository;

    protected AbstractCompositeCrudService(CompositeRepository<DOMAIN, ID> repository) {
        super(repository);
        this.compositeRepository = repository;
    }

    @Override
    public Page<DOMAIN> pageAllById(ID id, Pageable pageable) {
        return compositeRepository.findALlById(id, pageable);
    }
}
