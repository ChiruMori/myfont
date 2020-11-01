package work.cxlm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import work.cxlm.utils.MyFontUtils;

import static work.cxlm.model.support.MyFontConst.FILE_SEPARATOR;
import static work.cxlm.model.support.MyFontConst.USER_HOME;

/**
 * 系统配置
 * created 2020/11/1 17:08
 *
 * @author johnniang
 * @author cxlm
 */
@Data
@ConfigurationProperties("my-font")
public class MyFontProperties {

    /**
     * 上传路径前缀
     */
    private String uploadUrlPrefix = "upload";

    /**
     * 缓存保存的位置
     * 可选：memory, level, redis
     */
    private String cache = "memory";

    private String workDir = MyFontUtils.ensureSuffix(USER_HOME, FILE_SEPARATOR) + ".my_font" + FILE_SEPARATOR;


}
