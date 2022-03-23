package net.dengzixu.maine.entity.vo.group;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record JoinedGroupListVO(@JsonProperty("joined_group_list") List<JoinedGroupVO> joinedGroupList) {
}
