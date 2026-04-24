package com.example.ui_gene;

import java.io.Serializable;
import java.util.ArrayList;

public class ScreenConfig implements Serializable {
    private String screenName;
    private String purpose;
    private String layoutType;
    private ArrayList<ComponentInfo> components;

    public ScreenConfig(String screenName, String purpose, String layoutType, ArrayList<ComponentInfo> components) {
        this.screenName = screenName;
        this.purpose = purpose;
        this.layoutType = layoutType;
        this.components = components;
    }

    public String getScreenName() { return screenName; }
    public String getPurpose() { return purpose; }
    public String getLayoutType() { return layoutType; }
    public ArrayList<ComponentInfo> getComponents() { return components; }
}
