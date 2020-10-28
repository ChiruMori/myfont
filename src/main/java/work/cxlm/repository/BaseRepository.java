package work.cxlm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

/**
 * created 2020/10/21 12:18
 *
 * @param <DOMAIN> 实体类
 * @param <ID>     ID 类
 * @author johnniang
 * @author ryanwang
 * @author cxlm
 */
public interface BaseRepository<DOMAIN, ID> extends JpaRepository<DOMAIN, ID> {

    /**
     * 通过 ID 集合与排序，列出所有 DOMAIN
     *
     * @param ids  ID 集合，不能为 null
     * @param sort 指定的排序规则，不能为 null
     * @return DOMAIN 列表
     */
    @NonNull
    List<DOMAIN> findAllByIdIn(@NonNull Collection<ID> ids, @NonNull Sort sort);

    /**
     * 通过 ID 集合与分页，列出所有 DOMAIN
     *
     * @param ids      ID 集合，不能为 null
     * @param pageable 指定的分页规则，不能为 null
     * @return DOMAIN 列表
     */
    @NonNull
    Page<DOMAIN> findAllByIdIn(@NonNull Collection<ID> ids, @NonNull Pageable pageable);

    /**
     * 删除 ID 集合中指定的所有 DOMAIN 实例
     *
     * @param ids ID 集合，不能为 null
     * @return 删除的行数
     */
    long deleteByIdIn(@NonNull Collection<ID> ids);

}
