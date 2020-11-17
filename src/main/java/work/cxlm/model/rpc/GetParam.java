package work.cxlm.model.rpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.lang.NonNull;
import work.cxlm.utils.JsonUtils;

import java.util.Map;

/**
 * Get 请求的参数实体
 */
public interface GetParam {

    @NonNull
    default String toParamString() throws JsonProcessingException {
        Map<?, ?> paramMap = JsonUtils.objectToMap(this);
        return RpcClient.mapToParams(paramMap);
    }
}
