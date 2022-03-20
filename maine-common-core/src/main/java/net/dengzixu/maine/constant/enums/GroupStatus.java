package net.dengzixu.maine.constant.enums;

public enum GroupStatus {
    DELETED(127),
    BANNED(126),

    CLOSED(1),

    DEFAULT(0);


    private final Integer value;


    public Integer value() {
        return this.value;
    }

    public Integer code() {
        return this.value();
    }

    GroupStatus(Integer value) {
        this.value = value;
    }
}
