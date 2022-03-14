package net.dengzixu.maine.entity.bo;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public record TaskSettingBO(@JsonProperty(value = "allow_group_list") ArrayList<Long> allowGroupList) {
}