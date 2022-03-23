package net.dengzixu.maine.entity.bo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public record CreateTaskBO(@NotBlank(message = "任务名称不能为空") @Size(message = "任务名称不能超过60个字符", max = 50) String title,
                           @Size(message = "任务简介长度不能超过200个字符", max = 50) String description,
                           @JsonProperty(value = "end_time")
                           @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                           @Future(message = "截止时间不能小于当前时间") LocalDateTime endTime,
                           @JsonProperty(value = "setting") TaskSettingBO taskSettingBO) {
}
