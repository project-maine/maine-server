package net.dengzixu.constant.enums;

public enum GroupNumberStatus {
    EXITED(1),

    DEFAULT(0);


    private final Integer value;

    public Integer value() {
        return this.value;
    }

    public Integer code() {
        return this.value();
    }



    GroupNumberStatus(Integer value) {
        this.value = value;
    }
}
