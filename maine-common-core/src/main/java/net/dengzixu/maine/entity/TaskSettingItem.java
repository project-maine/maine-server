package net.dengzixu.maine.entity;


import lombok.Data;

import java.util.ArrayList;

@Data
public class TaskSettingItem implements java.io.Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 114514_1919810L;

    private ArrayList<Long> allowGroups;
}
