package work.cxlm.cache;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * created 2020/11/1 15:33
 *
 * @author johnniang
 * @author cxlm
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CacheWrapper<V> implements Serializable {

    /**
     * 被包装的缓存值
     */
    private V data;

    /**
     * 到期时间
     */
    private Date expireAt;

    /**
     * 创建时间
     */
    private Date createAt;
}
