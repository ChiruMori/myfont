package work.cxlm.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * created 2020/11/17 14:49
 *
 * @author Chiru
 */
@Slf4j
public class BeanUtilsTester {

    @Data
    @AllArgsConstructor
    private static class TestObject {
        private String strParam;
        private Integer intParam;
        private String otherString;
    }

    @Test
    public void beanStringConvert() {
        String result = "result";
        TestObject obj = new TestObject("4567", 4567, "我是你爸爸");
        BeanUtils.mapProperties(obj, (srcString) -> result);
        assertEquals(obj.strParam, result);
        assertEquals(obj.otherString, result);
        assertEquals(obj.intParam, 4567);
    }
}
