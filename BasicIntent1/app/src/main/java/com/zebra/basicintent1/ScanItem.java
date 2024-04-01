package com.zebra.basicintent1;

public class ScanItem {
    private String source;
    private String data;
    private String labelType;

    public ScanItem(String source, String data, String labelType) {
        this.source = source;
        this.data = data;
        this.labelType = labelType;
    }

    // Getters
    public String getSource() {
        return source;
    }

    public String getData() {
        return data;
    }

    public String getLabelType() {
        return labelType;
    }
}
