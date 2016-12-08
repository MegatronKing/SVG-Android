package com.github.megatronking.svg.sample;

public class SampleData {

    public String title;
    public String page;

    public SampleData(String title, String page) {
        this.title = title;
        this.page = page;
    }

    @Override
    public String toString() {
        return title;
    }
}
