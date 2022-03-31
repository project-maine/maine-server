package net.dengzixu.constant.enums;

public enum UserStatus {
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

    UserStatus(Integer value) {
        this.value = value;
    }


    public enum PasswordStatus {
        UNSET(127),

        SET(0);

        private final Integer value;

        public Integer value() {
            return this.value;
        }

        public Integer code() {
            return this.value();
        }

        PasswordStatus(Integer value) {
            this.value = value;
        }
    }

    public enum PhoneStatus {
        NOT_SET(127),
        INVALID(126),

        VALID(0);

        private final Integer value;

        public Integer value() {
            return this.value;
        }

        public Integer code() {
            return this.value();
        }

        PhoneStatus(Integer value) {
            this.value = value;
        }
    }

    public enum EmailStatus {
        NOT_SET(127),
        INVALID(126),

        VALID(0);

        private final Integer value;

        public Integer value() {
            return this.value;
        }

        public Integer code() {
            return this.value();
        }

        EmailStatus(Integer value) {
            this.value = value;
        }
    }
}
