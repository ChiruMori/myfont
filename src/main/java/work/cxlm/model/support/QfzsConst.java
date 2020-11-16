package work.cxlm.model.support;

import org.springframework.http.HttpHeaders;

import java.io.File;
import java.util.Optional;

/**
 * 公共常量
 * created 2020/11/1 23:17
 *
 * @author ryanwang
 * @author cxlm
 */
public class QfzsConst {

    /**
     * 用户路径
     */
    public static final String USER_HOME = System.getProperties().getProperty("user.home");

    /**
     * 文件分隔符
     */
    public static final String FILE_SEPARATOR = File.separator;

    /**
     * 路径分隔符
     */
    public static final String URL_SEPARATOR = "/";

    /**
     * freemarker 模板文件后缀名
     */
    public static final String SUFFIX_FTL = ".ftl";

    /**
     * Admin token header name.
     */
    public final static String ADMIN_TOKEN_HEADER_NAME = "ADMIN-" + HttpHeaders.AUTHORIZATION;
    /**
     * Admin token param name.
     */
    public final static String ADMIN_TOKEN_QUERY_NAME = "admin_token";
    /**
     * Content token header name.
     */
    public final static String API_ACCESS_KEY_HEADER_NAME = "API-" + HttpHeaders.AUTHORIZATION;
    /**
     * Content api token param name
     */
    public final static String API_ACCESS_KEY_QUERY_NAME = "api_access_key";
    /**
     * 默认个性签名
     */
    public final static String DEFAULT_USER_SIGNATURE = "这家伙很懒，什么都没写。。。";

}
