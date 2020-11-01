package work.cxlm.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        desensitization = MyFontUtils.desensitize(plainText, -1, 1);
        assertEquals("*******8", desensitization);
    }

    @Test
    void desensitizeFailureTest() {
        String plainText = " ";
        assertThrows(IllegalArgumentException.class, () -> MyFontUtils.desensitize(plainText, 1, 1));
    }

    @Test
    void timeFormatTest() {
        long seconds = 0;
        String timeFormat = MyFontUtils.timeFormat(seconds);
        assertEquals("0 秒", timeFormat);

        seconds = -1;
        timeFormat = MyFontUtils.timeFormat(seconds);
        assertEquals("0 秒", timeFormat);

        seconds = 30;
        timeFormat = MyFontUtils.timeFormat(seconds);
        assertEquals("30 秒", timeFormat);

        seconds = 60;
        timeFormat = MyFontUtils.timeFormat(seconds);
        assertEquals("1 分", timeFormat);

        seconds = 120;
        timeFormat = MyFontUtils.timeFormat(seconds);
        assertEquals("2 分", timeFormat);

        seconds = 3600;
        timeFormat = MyFontUtils.timeFormat(seconds);
        assertEquals("1 时", timeFormat);

        seconds = 7200;
        timeFormat = MyFontUtils.timeFormat(seconds);
        assertEquals("2 时", timeFormat);

        seconds = 7200 + 30;
        timeFormat = MyFontUtils.timeFormat(seconds);
        assertEquals("2 时, 30 秒", timeFormat);

        seconds = 7200 + 60 + 30;
        timeFormat = MyFontUtils.timeFormat(seconds);
        assertEquals("2 时, 1 分, 30 秒", timeFormat);
    }

    @Test
    void pluralizeTest() {

        String label = "chance";
        String pluralLabel = "chances";

        String pluralizedFormat = MyFontUtils.pluralize(1, label, pluralLabel);
        assertEquals("1 chance", pluralizedFormat);


        pluralizedFormat = MyFontUtils.pluralize(2, label, pluralLabel);
        assertEquals("2 chances", pluralizedFormat);

        pluralizedFormat = MyFontUtils.pluralize(0, label, pluralLabel);
        assertEquals("no chances", pluralizedFormat);

        // Test random positive time
        IntStream.range(0, 10000).forEach(i -> {
            long time = RandomUtils.nextLong(2, Long.MAX_VALUE);
            String result = MyFontUtils.pluralize(time, label, pluralLabel);
            assertEquals(time + " " + pluralLabel, result);
        });

        // Test random negative time
        IntStream.range(0, 10000).forEach(i -> {
            long time = (-1) * RandomUtils.nextLong();
            String result = MyFontUtils.pluralize(time, label, pluralLabel);
            assertEquals("no " + pluralLabel, result);
        });
    }

    @Test
    @SuppressWarnings("all")
    void pluralizeLabelExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> MyFontUtils.pluralize(1, null, null));
    }

    @Test
    void textEnsurePrefixAndSuffix() {
        assertEquals("abcd", MyFontUtils.ensurePrefix("abcd", "ab"));
        assertEquals("abcd", MyFontUtils.ensurePrefix("abcd", "abcd"));
        assertEquals("abcd", MyFontUtils.ensurePrefix("cd", "ab"));
        assertEquals("accd", MyFontUtils.ensurePrefix("cd", "ac"));

        assertEquals("accd", MyFontUtils.ensureSuffix("ac", "cd"));
        assertEquals("accd", MyFontUtils.ensureSuffix("accd", "cd"));
        assertEquals("accd", MyFontUtils.ensureSuffix("accd", "accd"));
    }

}
