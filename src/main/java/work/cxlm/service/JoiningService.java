package work.cxlm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import work.cxlm.model.entity.Joining;
import work.cxlm.model.entity.id.JoiningId;
import work.cxlm.service.base.CrudService;

import java.util.Optional;

/**
 * created 2020/11/18 12:50
 *
 * @author Chiru
 */
public interface JoiningService extends CrudService<Joining, JoiningId> {
    /**
     * 根据社团 ID 列出全部 Joining 实体
     */
    @NonNull
    Page<Joining> pageAllJoiningByClubId(@Nullable Integer clubId, Pageable pageable);

    /**
     * 根据用户 ID 列出全部 Joining 实体
     */
    @NonNull
    Page<Joining> pageAllJoiningByUserId(@Nullable Integer userId, Pageable pageable);

    Optional<Joining> getJoiningById(@Nullable Integer userId, @Nullable Integer clubId);
}
