package work.cxlm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import work.cxlm.model.entity.Joining;
import work.cxlm.model.entity.id.JoiningId;
import work.cxlm.repository.CompositeRepository;
import work.cxlm.service.JoiningService;
import work.cxlm.service.base.AbstractCompositeCrudService;

import java.util.Optional;

/**
 * created 2020/11/18 13:13
 *
 * @author Chiru
 */
@Service
@Slf4j
public class JoiningServiceImpl extends AbstractCompositeCrudService<Joining, JoiningId> implements JoiningService {

    protected JoiningServiceImpl(CompositeRepository<Joining, JoiningId> repository) {
        super(repository);
    }

    @NonNull
    @Override
    public Page<Joining> pageAllJoiningByClubId(Integer clubId, Pageable pageable) {
        if (clubId == null) {
            return Page.empty();
        }
        JoiningId id = new JoiningId();
        id.setClubId(clubId);
        return pageAllById(id, pageable);
    }

    @NonNull
    @Override
    public Page<Joining> pageAllJoiningByUserId(Integer userId, Pageable pageable) {
        if (userId == null) {
            return Page.empty();
        }
        JoiningId id = new JoiningId();
        id.setUserId(userId);
        return pageAllById(id, pageable);
    }

    @Override
    @NonNull
    public Optional<Joining> getJoiningById(@Nullable Integer userId, @Nullable Integer clubId) {
        if (userId == null || clubId == null) {
            return Optional.empty();
        }
        JoiningId id = new JoiningId(userId, clubId);
        return Optional.of(getById(id));
    }
}
