package com.cx.dataflow.dto;


public class Item {
    private String id;
    private String info;

    public Item(final String id, final String info) {
        this.id = id;
        this.info = info;
    }

    public Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(final String info) {
        this.info = info;
    }
}

