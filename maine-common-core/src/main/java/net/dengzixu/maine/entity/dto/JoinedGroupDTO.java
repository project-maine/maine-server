package net.dengzixu.maine.entity.dto;

public record JoinedGroupDTO(Long groupID,
                             String groupName,
                             String groupDescription,
                             Integer groupStatus,
                             String joinTime) {
}
