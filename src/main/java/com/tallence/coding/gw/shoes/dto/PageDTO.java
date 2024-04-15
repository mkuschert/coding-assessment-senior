package com.tallence.coding.gw.shoes.dto;

import java.util.List;

public class PageDTO {

    private int page;

    private int per_page;

    private int total;

    private int total_pages;

    private List<ShoeDTO> data;

    private SupportDTO support;

    public PageDTO() {
    }

    public PageDTO(int page, int per_page, int total, int total_pages, List<ShoeDTO> data, SupportDTO support) {
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
        this.data = data;
        this.support = support;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<ShoeDTO> getData() {
        return data;
    }

    public void setData(List<ShoeDTO> data) {
        this.data = data;
    }

    public SupportDTO getSupport() {
        return support;
    }

    public void setSupport(SupportDTO support) {
        this.support = support;
    }
}
