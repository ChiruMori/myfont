package work.cxlm.model.entity.support;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import work.cxlm.utils.ReflectionUtils;

import java.io.Serializable;

/**
 * @author cxlm
 * created 2020/10/17 15:27
 * <p>
 * 获取唯一键
 */
public class CustomIdGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        Object id = ReflectionUtils.getFieldValue("id", obj);
        if (id != null) {
            return (Serializable) id;
        }
        return super.generate(s, obj);
    }
}
