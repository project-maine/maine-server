package net.dengzixu.maine.entity.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CreateTaskBO(@NotBlank(message = "任务名称不能为空") @Size(message = "任务名称不能超过60个字符", max = 50) String title,
                           @Size(message = "任务简介长度不能超过200个字符", max = 50) String description,
                           @JsonProperty(value = "setting") TaskSettingBO taskSettingBO) {
}
