package work.cxlm.model.dto.base;

import org.springframework.lang.NonNull;
import work.cxlm.utils.BeanUtils;

/**
 * 将实体类转化为 DTO 的接口
 * created 2020/10/29 15:19
 *
 * @param <DTO>    本接口的实现类
 * @param <DOMAIN> 实体类
 * @author johnniang
 * @author cxlm
 */
public interface OutputConverter<DTO extends OutputConverter<DTO, DOMAIN>, DOMAIN> {

    @NonNull
    @SuppressWarnings("unchecked")
    default <T extends DTO> T convertFrom(DOMAIN domain) {
        BeanUtils.updateProperties(domain, this);
        return (T) this;
    }
}
