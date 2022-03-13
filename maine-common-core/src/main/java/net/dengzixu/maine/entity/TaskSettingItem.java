package net.dengzixu.maine.entity;


import java.io.Serializable;
import java.util.ArrayList;


public class TaskSettingItem implements Serializable {
    private static final long serialVersionUID = 1413254111655445456L;

    private String endTime;
    private ArrayList<Long> allowGroups;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Long> getAllowGroups() {
        return allowGroups;
    }

    public void setAllowGroups(ArrayList<Long> allowGroups) {
        this.allowGroups = allowGroups;
    }

    @Override
    public String toString() {
        return "TaskSettingItem{" +
                "endTime='" + endTime + '\'' +
                ", allowGroups=" + allowGroups +
                '}';
    }
}
