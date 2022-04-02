package net.dengzixu.maine.entity.vo.task;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record HistoryListVO(@JsonProperty(value = "history_list") List<HistoryVO> historyVOList) {
}
