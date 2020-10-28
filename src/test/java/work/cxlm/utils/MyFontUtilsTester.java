package work.cxlm.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import work.cxlm.utils.MyFontUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * created 2020/10/28 17:19
 *
 * @author johnniang
 * @author ryanwang
 * @author cxlm
 */
@Slf4j
public class MyFontUtilsTester {

    @Test
    void desensitizeSuccessTest() {
        String plainText = "12345678";

        String desensitization = MyFontUtils.desensitize(plainText, 1, 1);
        assertEquals("1******8", desensitization);

        desensitization = MyFontUtils.desensitize(plainText, 2, 3);
        assertEquals("12***678", desensitization);

        desensitization = MyFontUtils.desensitize(plainText, 2, 6);
        assertEquals("12345678", desensitization);

        desensitization = MyFontUtils.desensitize(plainText, 2, 7);
        assertEquals("12345678", desensitization);

        desensitization = MyFontUtils.desensitize(plainText, 0, 0);
        assertEquals("********", desensitization);

        desensitization = MyFontUtils.desensitize(plainText, -1, -1);
        assertEquals("********", desensitization);
    }
}
