package com.esgi.googlenews.Modeles;

import android.graphics.Bitmap;

/**
 * Model of the Article
 */
public class ArticleModel
{
    private Bitmap img;
    private String title;
    private String content;

    /**
     *
     * @param img
     * @param title
     * @param content
     */
    public ArticleModel (Bitmap img, String title, String content)
    {
        this.img = img;
        this.title = title;
        this.content = content;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }


}
