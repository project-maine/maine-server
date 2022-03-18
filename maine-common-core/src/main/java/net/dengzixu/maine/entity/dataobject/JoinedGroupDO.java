package net.dengzixu.maine.entity.dataobject;

import lombok.Data;

@Data
public class JoinedGroupDO {
    private Long groupID;
    private String groupName;
    private String groupDescription;
    private Integer groupStatus;
    private String joinTime;
}
