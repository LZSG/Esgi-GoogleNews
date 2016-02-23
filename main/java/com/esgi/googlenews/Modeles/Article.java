package com.esgi.googlenews.Modeles;

/**
 * Article object
 */
public class Article
{

    private String contentArticle;
    private String titleArticle;
    private String urlArticle;
    private String urlPicture;
    private String dateArticle;

    private int downloadPicture;
    private int idFlagArticle;
    private int idArticle;

    /**
     *
     * @param idArticle
     * @param titleArticle
     * @param contentArticle
     * @param urlArticle
     * @param urlPicture
     * @param downloadPicture
     * @param dateArticle
     * @param idFlagArticle
     */
    public Article (int idArticle, String titleArticle, String contentArticle, String urlArticle, String urlPicture, int downloadPicture, String dateArticle, int idFlagArticle)
    {
        this(titleArticle, contentArticle, urlArticle, urlPicture, downloadPicture, dateArticle, idFlagArticle);
        this.idArticle = idArticle;
    }

    /**
     *
     * @param titleArticle
     * @param contentArticle
     * @param urlArticle
     * @param urlPicture
     * @param downloadPicture
     * @param dateArticle
     * @param idFlagArticle
     */
    public Article (String titleArticle, String contentArticle, String urlArticle, String urlPicture, int downloadPicture, String dateArticle, int idFlagArticle)
    {
        this(titleArticle, contentArticle, urlArticle, urlPicture, downloadPicture, dateArticle);
        this.idFlagArticle = idFlagArticle;
    }

    /**
     *
     * @param titleArticle
     * @param contentArticle
     * @param urlArticle
     * @param urlPicture
     * @param downloadPicture
     * @param dateArticle
     */
    public Article (String titleArticle, String contentArticle, String urlArticle, String urlPicture, int downloadPicture, String dateArticle)
    {
        this.titleArticle = titleArticle;
        this.contentArticle = contentArticle;
        this.urlArticle = urlArticle;
        this.urlPicture = urlPicture;
        this.downloadPicture = downloadPicture;
        this.dateArticle = dateArticle;
    }

    /**
     * Get the Picture  TODO:
     * @return int downloadPicture
     */
    public int getDownloadPicture ()
    {
        return downloadPicture;
    }

    /**
     * Get id of the Article use in DatabaseHelper
     *
     * @return idArticle int
     */
    public int getIdArticle ()
    {
        return idArticle;
    }

    /**
     * Get Title of the Article
     *
     * @return titleArticle string
     */
    public String getTitleArticle ()
    {
        return titleArticle;
    }

    /**
     * Get Content of the Article
     *
     * @return contentArticle string
     */
    public String getContentArticle ()
    {
        return contentArticle;
    }

    /**
     * Get Url of the Article
     *
     * @return urlArticle string
     */
    public String getUrlArticle ()
    {
        return urlArticle;
    }

    /**
     * Get the id Flag link to article
     *
     * @return idFlagArticle int
     */
    public int getIdFlagArticle ()
    {
        return idFlagArticle;
    }

    /**
     * Set the id Flag link to article
     *
     * @param idFlagArticle int
     */
    public void setIdFlagArticle (int idFlagArticle)
    {
        this.idFlagArticle = idFlagArticle;
    }

    /**
     * Get Url of the Picture
     *
     * @return urlPicture string
     */
    public String getUrlPicture ()
    {
        return urlPicture;
    }

    /**
     * Get release date
     *
     * @return dateArticle string
     */
    public String getDateArticle ()
    {
        return dateArticle;
    }

    /**
     * to String
     * @return a string on JsonFormat
     */
    @Override
    public String toString ()
    {
        return "Article{" +
                "idArticle=" + idArticle +
                ", titleArticle='" + titleArticle + '\'' +
                ", contentArticle='" + contentArticle + '\'' +
                ", urlArticle='" + urlArticle + '\'' +
                ", urlPicture='" + urlPicture + '\'' +
                ", dateArticle=" + dateArticle +
                ", idFlagArticle=" + idFlagArticle +
                '}';
    }
}
