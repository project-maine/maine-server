package net.dengzixu.maine.entity.bo.task;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record AttendanceCreateBasicBO(@NotBlank(message = "主题不能为空") @Size(message = "标题长度不能超过60个字符", max = 50) String title,
                                      @Size(message = "简介长度不能超过200个字符", max = 50) String description) {

}
