package work.cxlm.model.rpc.code2session;

import lombok.AllArgsConstructor;
import lombok.Data;
import work.cxlm.model.rpc.GetParam;

/**
 * created 2020/11/17 16:22
 *
 * @author Chiru
 */
@Data
@AllArgsConstructor
public class Code2SessionParam implements GetParam {

    private String appid;

    private String secret;

    private String js_code;

    private String grant_type;
}
