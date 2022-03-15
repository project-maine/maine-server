package net.dengzixu.maine.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ParticipantListVO(@JsonProperty(value = "participant_list") List<ParticipantVO> participantVOList) {
}
