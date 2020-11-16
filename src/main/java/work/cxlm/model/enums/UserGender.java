package work.cxlm.model.enums;

/**
 * created 2020/11/16 21:56
 *
 * @author Chiru
 */
public enum UserGender implements ValueEnum<Integer> {
    /**
     * 未知性别，和奇怪的性别
     */
    UNKNOWN(0),

    MALE(1),

    FEMALE(2);

    private final Integer value;

    UserGender(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
