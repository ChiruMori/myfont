package work.cxlm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * created 2020/11/18 13:08
 *
 * @author Chiru
 */
@NoRepositoryBean
public interface CompositeRepository<DOMAIN, ID> extends BaseRepository<DOMAIN, ID> {

    /**
     * 通过联合主键的一项查找所有 DOMAIN
     */
    Page<DOMAIN> findALlById(ID id, Pageable pageable);
}
