package com.example.youssefwagih.mychurchapp.news.models;

/**
 * Created by y.wagih on 10/22/2017.
 */

public class NewsModel {
    private String title;
    private String description;

    public NewsModel(){

    }

    public NewsModel(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
