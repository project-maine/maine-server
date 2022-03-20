package net.dengzixu.maine.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GroupNumberListVO(@JsonProperty(value = "number_list") List<GroupNumberVO> groupNumberVOList) {
}
