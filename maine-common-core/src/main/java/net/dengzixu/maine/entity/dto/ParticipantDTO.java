package net.dengzixu.maine.entity.dto;

import lombok.Data;

@Data
public class ParticipantDTO {
    private Long userID;
    private String userName;
    private String serialID;
    private Integer recordStatus;
    private String takeTime;
}
