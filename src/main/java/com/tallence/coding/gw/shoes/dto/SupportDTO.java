package com.tallence.coding.gw.shoes.dto;

public class SupportDTO {

    private String url;

    private String text;

    public SupportDTO() {
    }

    public SupportDTO(String url, String text) {
        this.url = url;
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
