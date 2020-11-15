package work.cxlm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import work.cxlm.model.support.MyFontConst;
import work.cxlm.utils.MyFontUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * created 2020/11/15 13:46
 *
 * @author ryanwang
 * @author Chiru
 */
@Slf4j
public class MyFontRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final Set<String> blackPatterns = new HashSet<>();

    private final PathMatcher pathMatcher;

    private final MyFontProperties myFontProperties;

    public MyFontRequestMappingHandlerMapping(MyFontProperties myFontProperties) {
        this.myFontProperties = myFontProperties;
        initBlackPatterns();
        pathMatcher = new AntPathMatcher();
    }

    private void initBlackPatterns() {
        String uploadUrlPattern = MyFontUtils.ensureBoth(myFontProperties.getUploadUrlPrefix(), MyFontConst.URL_SEPARATOR) + "**";

        blackPatterns.add("/font/js/**");
        blackPatterns.add("/font/images/**");
        blackPatterns.add("/font/fonts/**");
        blackPatterns.add("/font/css/**");
        blackPatterns.add("/font/assets/**");
        blackPatterns.add("/font/csrf");
        blackPatterns.add("/swagger-ui.html");
        blackPatterns.add("/webjars/**");
        blackPatterns.add(uploadUrlPattern);
        // blackPatterns.add(adminPathPattern);
    }
}
