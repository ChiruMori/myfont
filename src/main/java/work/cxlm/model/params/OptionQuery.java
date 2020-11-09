package work.cxlm.model.params;

import lombok.Data;
import work.cxlm.model.enums.OptionType;

/**
 * 查询选项的参数
 * created 2020/11/9 20:19
 *
 * @author ryanwang
 * @author Chiru
 */
@Data
public class OptionQuery {

    private String key;
    private OptionType type;
}
