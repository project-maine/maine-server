package net.dengzixu.maine.entity.dto;

import java.io.Serializable;

public record TokenDTO(Long userID,
                       Long taskID,
                       String channel,
                       String longitude,
                       String latitude,
                       Long timestamp) implements Serializable {
}
