package com.esgi.googlenews.Modeles;

import android.graphics.Bitmap;

/**
 * Created by zaafranigabriel on 07/02/2016.
 */
public class ArticleModel {

    private Bitmap img;
    private String title;
    private String content;


    public ArticleModel(){

    }

    public ArticleModel(Bitmap img,String title,String content)
    {
        this.img = img;
        this.title = title;
        this.content = content;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }




}
