package net.dengzixu.maine.constant.enums;

public enum RecordStatus {
    DELETED(127),

    LATE(1),

    DEFAULT(0);


    private final Integer value;


    public Integer value() {
        return this.value;
    }

    public Integer code() {
        return this.value();
    }

    RecordStatus(Integer value) {
        this.value = value;
    }
}
