package work.cxlm.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import work.cxlm.config.QfzsProperties;
import work.cxlm.utils.QfzsDateUtils;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 提供部分方法的默认实现
 * created 2020/11/1 15:26
 *
 * @author johnniang
 * @author cxlm
 */
@Slf4j
public abstract class AbstractCacheStore<K, V> implements CacheStore<K, V> {

    protected QfzsProperties qfzsProperties;

    /**
     * 通过缓存键获取被包装（CacheWrapper）的缓存值
     *
     * @param key 缓存键
     * @return 包装在 Optional 中的 CacheWrapper 缓存值
     */
    abstract Optional<CacheWrapper<V>> gerInternal(@NonNull K key);

    /**
     * 设置缓存，由子类实现
     *
     * @param key   缓存键
     * @param value 被 CacheWrapper 包装的缓存值
     */
    abstract void putInternal(@NonNull K key, @NonNull CacheWrapper<V> value);

    /**
     * 如果不存在则设置
     *
     * @param key   缓存键
     * @param value 缓存值
     * @return 如果键已存在且已经设置了值，则返回 true，键不存在或已过期则返回 false，其他原因则返回 null
     */
    abstract Boolean putInternalIfAbsent(@NonNull K key, @NonNull CacheWrapper<V> value);

    @Override
    public Optional<V> get(@NonNull K key) {
        Assert.notNull(key, "缓存键不能为 null");

        return gerInternal(key).map(cacheWrapper -> {
            if (cacheWrapper.getExpireAt() != null && cacheWrapper.getExpireAt().before(QfzsDateUtils.now())) {
                log.warn("缓存已过期：[{}]", key);
                delete(key);  // 惰性删除
                return null;
            }
            return cacheWrapper.getData();
        });
    }

    @Override
    public void put(K key, V value, long timeout, TimeUnit timeUnit) {
        putInternal(key, wrapCacheValue(value, timeout, timeUnit));
    }

    @Override
    public Boolean putIfAbsent(K key, V value, long timeout, TimeUnit timeUnit) {
        return putInternalIfAbsent(key, wrapCacheValue(value, timeout, timeUnit));
    }

    @Override
    public void put(K key, V value) {
        putInternal(key, wrapCacheValue(value, 0, null));
    }

    /**
     * 包装缓存值对象为 CacheWrapper 对象
     *
     * @param value    缓存值，不能为 null
     * @param timeout  超时时间，不能小于零
     * @param timeUnit 时间单位，可以为 null
     * @return CacheWrapper 包装后的 缓存对象
     */
    @NonNull
    private CacheWrapper<V> wrapCacheValue(@NonNull V value, long timeout, @Nullable TimeUnit timeUnit) {
        Assert.notNull(value, "缓存值不能为 null");
        Assert.isTrue(timeout >= 0, "缓存过期时间不能小于零");

        Date now = QfzsDateUtils.now();
        Date expireAt = null;

        if (timeout > 0 && timeUnit != null) {
            expireAt = QfzsDateUtils.add(now, timeout, timeUnit);
        }

        CacheWrapper<V> cacheWrapper = new CacheWrapper<>();
        cacheWrapper.setCreateAt(now);
        cacheWrapper.setExpireAt(expireAt);
        cacheWrapper.setData(value);
        return cacheWrapper;
    }
}
