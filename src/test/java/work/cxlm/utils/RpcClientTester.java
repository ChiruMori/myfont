package work.cxlm.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import work.cxlm.config.QfzsProperties;
import work.cxlm.model.rpc.code2session.Code2SessionParam;
import work.cxlm.model.rpc.code2session.Code2SessionResponse;
import work.cxlm.model.rpc.RpcClient;

/**
 * created 2020/11/17 16:11
 *
 * @author Chiru
 */
@Slf4j
public class RpcClientTester {

    @Test
    public void getRequestTest() {
        QfzsProperties properties = new QfzsProperties();
        Code2SessionParam param = new Code2SessionParam(properties.getAppId(), properties.getAppSecret(), "jscode", "type");
        Code2SessionResponse res = RpcClient.getUrl(properties.getAppRequestUrl(), Code2SessionResponse.class, param);
        log.debug(res.toString());
    }
}
