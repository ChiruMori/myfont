package work.cxlm.controller.admin.content;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import work.cxlm.model.properties.PrimaryProperties;
import work.cxlm.service.OptionService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * created 2020/11/21 11:01
 *
 * @author ryanwang
 * @author Chiru
 */
@Controller
public class RootController {

    private final OptionService optionService;

    public RootController(OptionService optionService) {
        this.optionService = optionService;
    }

    /**
     * 获取图标
     */
    @GetMapping("favicon.ico")
    public void favicon(HttpServletResponse response) throws IOException {
        String favicon = optionService.getByProperty(PrimaryProperties.FAVICON_URL).orElse("").toString();
        if (StringUtils.isNotEmpty(favicon)) {
            response.sendRedirect(favicon);
        }
    }
}
