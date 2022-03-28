package net.dengzixu.constant.enums.enums;

public enum TaskStatus {
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

    TaskStatus(Integer value) {
        this.value = value;
    }
}
