package net.dengzixu.maine.entity.vo.group;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GroupInfoListVO(@JsonProperty(value = "group_list") List<GroupInfoVO> groupInfoVOList) {
}
