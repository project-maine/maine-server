package net.dengzixu.maine.entity.vo.task;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public record TaskSettingVO(@JsonProperty(value = "allow_group_list") ArrayList<Long> allowGroupList) {
}
