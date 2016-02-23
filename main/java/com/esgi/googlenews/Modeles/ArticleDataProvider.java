package com.esgi.googlenews.Modeles;

import android.graphics.Bitmap;


/**
 * Created by zaafranigabriel on 13/02/2016.
 */
public class ArticleDataProvider {

    private Bitmap article_poster_ressource;
    private String article_title;
    private String article_rating;


    public ArticleDataProvider(Bitmap article_poster_ressource, String article_title, String article_rating) {
        this.article_poster_ressource = article_poster_ressource;
        this.article_title = article_title;
        this.article_rating = article_rating;
    }

    public Bitmap getArticle_poster_ressource() {
        return article_poster_ressource;
    }

    public void setArticle_poster_ressource(Bitmap article_poster_ressource) {
        this.article_poster_ressource = article_poster_ressource;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_rating() {
        return article_rating;
    }

    public void setArticle_rating(String article_rating) {
        this.article_rating = article_rating;
    }


}
