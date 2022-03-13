package net.dengzixu.maine.entity.bo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record GroupAddBO(
        @NotBlank(message = "组织名称不能为空") @Size(message = "组织名称长度应在2-50个字符之间", min = 2, max = 50) String name,
        @Size(message = "组织描述应该在 1000 个字符以内", max = 1000) String description) {
}
