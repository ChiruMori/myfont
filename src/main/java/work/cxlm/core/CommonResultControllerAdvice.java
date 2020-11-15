package work.cxlm.core;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import work.cxlm.model.support.BaseResponse;

/**
 * controller 的返回值公共处理
 * created 2020/11/15 10:24
 *
 * @author johnniang
 * @author Chiru
 */
@ControllerAdvice("work.cxlm.controller")
public class CommonResultControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType contentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        MappingJacksonValue container = getOrCreateContainer(body);
        beforeBodyWriteInternal(container, response);
        return container;
    }

    /**
     * 将响应正文使用 {@link MappingJacksonValue} 进行包装 （为了支持序列化）
     * 如果已经包装则仅进行类型转化
     */
    private MappingJacksonValue getOrCreateContainer(Object body) {
        return body instanceof MappingJacksonValue ? (MappingJacksonValue) body : new MappingJacksonValue(body);
    }

    private void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, ServerHttpResponse response) {
        // 获得响应正文（body）
        Object returnBody = bodyContainer.getValue();

        if (returnBody instanceof BaseResponse) {
            // 已使用 BaseResponse 包装
            BaseResponse<?> baseResponse = (BaseResponse<?>) returnBody;
            HttpStatus status = HttpStatus.resolve(baseResponse.getStatus());
            if (status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            response.setStatusCode(status);
            return;
        }

        // 使用 BaseResponse 包装
        BaseResponse<?> baseResponse = BaseResponse.ok(returnBody);
        bodyContainer.setValue(baseResponse);
        response.setStatusCode(HttpStatus.valueOf(baseResponse.getStatus()));
    }
}
