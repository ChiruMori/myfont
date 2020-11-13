package work.cxlm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import work.cxlm.model.dto.base.OutputConverter;
import work.cxlm.model.entity.Option;

/**
 * created 2020/11/9 20:06
 *
 * @author johnniang
 * @author Chiru
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO implements OutputConverter<OptionDTO, Option> {

    private String key;

    private Object value;
}
