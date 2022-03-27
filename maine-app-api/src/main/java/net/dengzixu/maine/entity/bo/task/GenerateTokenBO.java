package net.dengzixu.maine.entity.bo.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.dengzixu.maine.entity.dto.TokenDTO;

import javax.validation.constraints.NotBlank;
import java.util.TreeMap;

public record GenerateTokenBO(@NotBlank
                              @JsonProperty(value = "task_id") String taskID,
                              @NotBlank
                              @JsonProperty(value = "user_id") String userID,
                              @NotBlank
                              @JsonProperty(value = "channel") String channel,
                              @JsonProperty(value = "longitude") String longitude,
                              @JsonProperty(value = "latitude") String latitude,
                              @NotBlank
                              @JsonProperty(value = "timestamp") String timestamp) {

    public TreeMap<String, String> toTreeMap() {
        TreeMap<String, String> treeMap = new TreeMap<>();

        treeMap.put("task_id", this.taskID());
        treeMap.put("user_id", this.userID());
        treeMap.put("channel", this.channel());
        if (null != this.longitude()) {
            treeMap.put("longitude", this.longitude());
        }
        if (null != this.latitude) {
            treeMap.put("latitude", this.latitude());
        }
        treeMap.put("timestamp", this.timestamp());

        return treeMap;
    }

    public TokenDTO toTokenDTO() {
        return new TokenDTO(Long.valueOf(this.userID()),
                Long.valueOf(this.taskID()),
                this.channel(),
                this.longitude(),
                this.latitude(),
                Long.valueOf(timestamp()));
    }
}
