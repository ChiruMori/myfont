package work.cxlm.rpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 用于发送 HTTP 请求的工具类
 * created 2020/11/17 15:19
 *
 * @author Chiru
 */
public class RpcClient {

    public static <T> T getUrl(@NonNull String url, @NonNull Class<T> type, @Nullable Map<String, String> params) {
        return getUrl(url + mapToParams(params), type);
    }

    /**
     * 将参数 Map 转化为 URL 直接参数，要求 Map 为纯字符串
     */
    public static String mapToParams(@Nullable Map<?, ?> stringMap) {
        StringBuilder sb;
        if (!CollectionUtils.isEmpty(stringMap)) {
            sb = new StringBuilder("?");
        } else {
            return StringUtils.EMPTY;
        }
        stringMap.forEach((k, v) -> sb.append(k).append("=").append(v).append("&"));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static <T> T getUrl(@NonNull String url, @NonNull Class<T> type, @Nullable GetParam param) {
        if (param == null) {
            return getUrl(url, type);
        }
        try {
            return getUrl(url + param.toParamString(), type);
        } catch (JsonProcessingException e) {
            throw new RestClientException("响应对象转化异常", e);
        }
    }

    public static <T> T getUrl(@NonNull String url, @NonNull Class<T> type) {
        Assert.notNull(url, "请求链接不能为 null");
        Assert.notNull(type, "返回值类型不能为 null");

        RestTemplate restTemplate = new RestTemplate();
        // 为微信的接口设置单独的转换器
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        ResponseEntity<T> forEntity = restTemplate.getForEntity(url, type);
        return forEntity.getBody();
    }
}
