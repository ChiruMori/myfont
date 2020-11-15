package work.cxlm.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

/**
 * 运行模式
 * created 2020/11/15 9:34
 *
 * @author johnniang
 * @author Chiru
 */
public enum Mode {

    /**
     * 生产
     */
    PRODUCTION,

    /**
     * 开发
     */
    DEVELOPMENT,

    /**
     * Demo
     */
    DEMO,

    /**
     * 测试
     */
    TEST;

    /**
     * 从字符串转化为枚举
     */
    @JsonCreator
    public static Mode valueFrom(@Nullable String value) {
        Mode modeResult = null;
        for (Mode mode : values()) {
            if (mode.name().equalsIgnoreCase(value)) {
                modeResult = mode;
                break;
            }
        }
        if (modeResult == null) {
            modeResult = PRODUCTION;
        }
        return modeResult;
    }

    @JsonValue
    String getValue() {
        return this.name().toLowerCase();
    }
}
