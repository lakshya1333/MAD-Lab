package com.example.ui_gene;

import java.io.Serializable;

public class ComponentInfo implements Serializable {
    private String type;
    private String label;
    private int quantity;
    private String options; // Comma separated for Spinners, RadioGroups, etc.

    public ComponentInfo(String type, String label, int quantity, String options) {
        this.type = type;
        this.label = label;
        this.quantity = quantity;
        this.options = options;
    }

    public String getType() { return type; }
    public String getLabel() { return label; }
    public int getQuantity() { return quantity; }
    public String getOptions() { return options; }
}
