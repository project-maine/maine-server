package net.dengzixu.maine;

import lombok.Data;

@Data
public class Group {
    private Long id;
    private String name;
    private String description;
    private Long userID;
    private Integer status;
    private String createTime;
    private String modifyTime;
}
