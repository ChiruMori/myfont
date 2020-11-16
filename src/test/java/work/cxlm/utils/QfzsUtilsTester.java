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
public class QfzsUtilsTester {

    @Test
    void desensitizeSuccessTest() {
        String plainText = "12345678";

        String desensitization = QfzsUtils.desensitize(plainText, 1, 1);
        assertEquals("1******8", desensitization);

        desensitization = QfzsUtils.desensitize(plainText, 2, 3);
        assertEquals("12***678", desensitization);

        desensitization = QfzsUtils.desensitize(plainText, 2, 6);
        assertEquals("12345678", desensitization);

        desensitization = QfzsUtils.desensitize(plainText, 2, 7);
        assertEquals("12345678", desensitization);

        desensitization = QfzsUtils.desensitize(plainText, 0, 0);
        assertEquals("********", desensitization);

        desensitization = QfzsUtils.desensitize(plainText, -1, -1);
        assertEquals("********", desensitization);

        desensitization = QfzsUtils.desensitize(plainText, -1, 1);
        assertEquals("*******8", desensitization);
    }

    @Test
    void desensitizeFailureTest() {
        String plainText = " ";
        assertThrows(IllegalArgumentException.class, () -> QfzsUtils.desensitize(plainText, 1, 1));
    }

    @Test
    void timeFormatTest() {
        long seconds = 0;
        String timeFormat = QfzsUtils.timeFormat(seconds);
        assertEquals("0 秒", timeFormat);

        seconds = -1;
        timeFormat = QfzsUtils.timeFormat(seconds);
        assertEquals("0 秒", timeFormat);

        seconds = 30;
        timeFormat = QfzsUtils.timeFormat(seconds);
        assertEquals("30 秒", timeFormat);

        seconds = 60;
        timeFormat = QfzsUtils.timeFormat(seconds);
        assertEquals("1 分", timeFormat);

        seconds = 120;
        timeFormat = QfzsUtils.timeFormat(seconds);
        assertEquals("2 分", timeFormat);

        seconds = 3600;
        timeFormat = QfzsUtils.timeFormat(seconds);
        assertEquals("1 时", timeFormat);

        seconds = 7200;
        timeFormat = QfzsUtils.timeFormat(seconds);
        assertEquals("2 时", timeFormat);

        seconds = 7200 + 30;
        timeFormat = QfzsUtils.timeFormat(seconds);
        assertEquals("2 时, 30 秒", timeFormat);

        seconds = 7200 + 60 + 30;
        timeFormat = QfzsUtils.timeFormat(seconds);
        assertEquals("2 时, 1 分, 30 秒", timeFormat);
    }

    @Test
    void pluralizeTest() {

        String label = "chance";
        String pluralLabel = "chances";

        String pluralizedFormat = QfzsUtils.pluralize(1, label, pluralLabel);
        assertEquals("1 chance", pluralizedFormat);


        pluralizedFormat = QfzsUtils.pluralize(2, label, pluralLabel);
        assertEquals("2 chances", pluralizedFormat);

        pluralizedFormat = QfzsUtils.pluralize(0, label, pluralLabel);
        assertEquals("no chances", pluralizedFormat);

        // Test random positive time
        IntStream.range(0, 10000).forEach(i -> {
            long time = RandomUtils.nextLong(2, Long.MAX_VALUE);
            String result = QfzsUtils.pluralize(time, label, pluralLabel);
            assertEquals(time + " " + pluralLabel, result);
        });

        // Test random negative time
        IntStream.range(0, 10000).forEach(i -> {
            long time = (-1) * RandomUtils.nextLong();
            String result = QfzsUtils.pluralize(time, label, pluralLabel);
            assertEquals("no " + pluralLabel, result);
        });
    }

    @Test
    @SuppressWarnings("all")
    void pluralizeLabelExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> QfzsUtils.pluralize(1, null, null));
    }

    @Test
    void textEnsurePrefixAndSuffix() {
        assertEquals("abcd", QfzsUtils.ensurePrefix("abcd", "ab"));
        assertEquals("abcd", QfzsUtils.ensurePrefix("abcd", "abcd"));
        assertEquals("abcd", QfzsUtils.ensurePrefix("cd", "ab"));
        assertEquals("accd", QfzsUtils.ensurePrefix("cd", "ac"));

        assertEquals("accd", QfzsUtils.ensureSuffix("ac", "cd"));
        assertEquals("accd", QfzsUtils.ensureSuffix("accd", "cd"));
        assertEquals("accd", QfzsUtils.ensureSuffix("accd", "accd"));
    }

}
