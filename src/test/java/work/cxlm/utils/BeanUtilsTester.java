package work.cxlm.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import work.cxlm.model.entity.Joining;
import work.cxlm.model.entity.User;
import work.cxlm.model.entity.id.JoiningId;
import work.cxlm.model.vo.PageUserVO;

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

    @Test
    public void updatePropertiesTest() {
        User user = new User();
        user.setRealName("我是你爸爸");
        PageUserVO vo = new PageUserVO().convertFrom(user);
        assertEquals(vo.getRealName(), "我是你爸爸");

        user.setHead("head");
        user.setRealName("名字");
        BeanUtils.updateProperties(user, vo);
        assertEquals(vo.getRealName(), "名字");
        assertEquals(vo.getHead(), "head");

        Joining joining = new Joining();
        joining.setId(new JoiningId(1, 1));
        joining.setPosition("弟弟");
        BeanUtils.updateProperties(joining, vo);
        assertEquals(vo.getPosition(), "弟弟");

        log.debug(vo.toString());
    }
}
