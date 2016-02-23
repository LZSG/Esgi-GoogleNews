package com.esgi.googlenews.Modeles;

import android.graphics.Bitmap;


/**
 * Data Provider of the Article
 */
public class ArticleDataProvider
{

    private Bitmap article_poster_resource;
    private String article_title;
    private String article_rating;

    /**
     * @param article_poster_resource Bitmap
     * @param article_title           String
     * @param article_rating          String
     */
    public ArticleDataProvider (Bitmap article_poster_resource, String article_title, String article_rating)
    {
        this.article_poster_resource = article_poster_resource;
        this.article_title = article_title;
        this.article_rating = article_rating;
    }

    /**
     * @return Bitmap
     */
    public Bitmap getArticle_poster_resource ()
    {
        return article_poster_resource;
    }

    /**
     * @return String
     */
    public String getArticle_title ()
    {
        return article_title;
    }

    /**
     * @return String
     */
    public String getArticle_rating ()
    {
        return article_rating;
    }
}
