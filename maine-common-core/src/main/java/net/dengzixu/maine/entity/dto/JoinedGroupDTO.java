package net.dengzixu.maine.entity.dto;

import lombok.Data;

@Data
public class JoinedGroupDTO {
    private Long groupID;
    private String groupName;
    private String groupDescription;
    private Integer groupStatus;
    private Integer groupNumberStatus;
    private String joinTime;
}
