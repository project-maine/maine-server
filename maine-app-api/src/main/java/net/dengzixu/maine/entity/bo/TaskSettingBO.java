package net.dengzixu.maine.entity.bo;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public record TaskSettingBO(@JsonProperty(value = "end_time") String endTime,
                     @JsonProperty(value = "allow_group_list") ArrayList<Long> allowGroupList) {
}