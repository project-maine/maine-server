package net.dengzixu.maine.entity.bo.task;

import javax.validation.constraints.Pattern;

public record TakeByCodeBO(@Pattern(message = "考勤码错误", regexp = "^\\d{6}$") String code) {
}
