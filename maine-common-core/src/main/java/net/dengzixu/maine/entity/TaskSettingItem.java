package net.dengzixu.maine.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class TaskSettingItem implements Serializable {
    private static final long serialVersionUID = 1413254111655445456L;

    private ArrayList<Long> allowGroups;
}
