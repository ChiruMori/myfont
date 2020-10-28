package work.cxlm.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import work.cxlm.exception.NotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * created 2020/10/21 10:59
 * 包含增删改查共同行为的 service 接口
 *
 * @param <DOMAIN> domain type
 * @param <ID>     id type
 * @author johnniang
 * @author cxlm
 */
public interface CrudService<DOMAIN, ID> {

    /**
     * 列出全部 DOMAIN
     */
    @NonNull
    List<DOMAIN> listAll();

    /**
     * 按指定顺序列出全部 DOMAIN
     */
    @NonNull
    List<DOMAIN> listAll(@NonNull Sort sort);

    /**
     * 按指定分页列出全部 DOMAIN
     */
    @NonNull
    Page<DOMAIN> listAll(@NonNull Pageable pageable);

    /**
     * 按照 ID 集合列出全部 DOMAIN
     */
    @NonNull
    List<DOMAIN> listAllByIds(@Nullable Collection<ID> ids);

    /**
     * 按照 ID 集合列出全部 DOMAIN，并排序
     */
    @NonNull
    List<DOMAIN> listAllByIds(@Nullable Collection<ID> ids, @NonNull Sort sort);

    /**
     * 通过 ID 查找单个 DOMAIN 的 Optional
     */
    @NonNull
    Optional<DOMAIN> fetchById(@NonNull ID id);

    /**
     * 通过 ID 查找单个 DOMAIN 实例
     *
     * @throws NotFoundException ID 不存在时抛出
     */
    @NonNull
    DOMAIN getById(@NonNull ID id);

    /**
     * 通过 ID 查找单个 DOMAIN 实例，不存在时返回 null
     */
    @Nullable
    DOMAIN getByIdOfNullable(@NonNull ID id);

    /**
     * 查找某个 ID 是否存在
     */
    boolean existsById(@NonNull ID id);

    /**
     * 断言某个 ID 存在，否则抛出异常
     *
     * @throws NotFoundException ID 不存在时抛出
     */
    void mustExistById(@NonNull ID id);

    /**
     * 计数
     */
    long count();

    /**
     * 创建（持久化存储）一个 DOMAIN
     */
    @NonNull
    @Transactional
    DOMAIN create(@NonNull DOMAIN domain);

    /**
     * 创建（持久化存储）多个 DOMAIN
     */
    @NonNull
    @Transactional
    List<DOMAIN> createInBatch(@NonNull Collection<DOMAIN> domains);

    /**
     * 更新一个 DOMAIN
     */
    @NonNull
    @Transactional
    DOMAIN update(@NonNull DOMAIN domain);

    /**
     * 更新多个 DOMAIN
     */
    @NonNull
    @Transactional
    List<DOMAIN> updateInBatch(@NonNull Collection<DOMAIN> domains);

    /**
     * 将所有等待的修改写入数据库
     */
    void flush();

    /**
     * 删除指定 ID 的 DOMAIN
     *
     * @throws NotFoundException 如果指定的 DOMAIN 不存在
     */
    @NonNull
    @Transactional
    DOMAIN removeById(@NonNull ID id);

    /**
     * 删除指定 ID 的 DOMAIN，如果不存在则返回 null
     */
    @Nullable
    @Transactional
    DOMAIN removeByIdOfNullable(@NonNull ID id);

    /**
     * 删除指定的 DOMAIN
     */
    @Transactional
    void remove(@NonNull DOMAIN domain);

    /**
     * 删除集合中指定 ID 的 DOMAIN
     */
    @Transactional
    void removeInBatch(@NonNull Collection<ID> ids);

    /**
     * 删除集合中指定的 DOMAIN
     */
    @Transactional
    void removeAll(@NonNull Collection<DOMAIN> domains);

    /**
     * 删除全部 DOMAIN
     */
    @Transactional
    void removeAll();
}
