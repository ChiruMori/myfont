package work.cxlm.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * created 2020/10/28 17:21
 *
 * @author cxlm
 */
public class MyFontUtils {

    /**
     * 将字符串的指定部分替换为 * 号
     *
     * @param plainText 原始文本
     * @param left      左边开始替换的位置
     * @param right     右边开始替换的字位置
     * @return 替换后的结果
     */
    public static String desensitize(String plainText, int left, int right) {
        Assert.hasText(plainText, "原始文本不能为空");

        if (left < 0) {
            left = 0;
        }
        if (right < 0) {
            right = 0;
        }

        int len = plainText.length();

        if (left > len) {
            left = len;
        }
        if (right > len) {
            right = len;
        }

        if (left + right > len) {
            right = len - left;
        }
        int remainSize = len - left - right;
        String leftStr = StringUtils.left(plainText, left);
        String rightStr = StringUtils.right(plainText, right);
        return StringUtils.rightPad(leftStr, remainSize + left, '*') + rightStr;
    }
}
