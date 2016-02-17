package com.esgi.googlenews.Modeles;

/**
 * Created by zaafranigabriel on 21/01/2016.
 */
public class Article {

    private int idArticle;
    private String titleArticle;
    private String contentArticle;
    private String urlArticle;
    private String urlPicture;
    private int downloadPicture;
    private String dateArticle;
    private int idFlagArticle;

    private MyDbHelper db;

    public Article(int idArticle,String titleArticle, String contentArticle, String urlArticle, String urlPicture, int downloadPicture,String dateArticle,int idFlagArticle) {
        this.idArticle = idArticle;
        this.titleArticle = titleArticle;
        this.contentArticle = contentArticle;
        this.urlArticle = urlArticle;
        this.urlPicture = urlPicture;
        this.downloadPicture = downloadPicture;
        this.dateArticle = dateArticle;
        this.idFlagArticle = idFlagArticle;
    }

    public Article(String titleArticle, String contentArticle, String urlArticle, String urlPicture,int downloadPicture, String dateArticle,int idFlagArticle) {
        this.titleArticle = titleArticle;
        this.contentArticle = contentArticle;
        this.urlArticle = urlArticle;
        this.urlPicture = urlPicture;
        this.downloadPicture = downloadPicture;
        this.dateArticle = dateArticle;
        this.idFlagArticle = idFlagArticle;
    }

    public Article(String titleArticle, String contentArticle, String urlArticle, String urlPicture,int downloadPicture, String dateArticle)
    {
        this.titleArticle = titleArticle;
        this.contentArticle = contentArticle;
        this.urlArticle = urlArticle;
        this.urlPicture = urlPicture;
        this.downloadPicture=downloadPicture;
        this.dateArticle = dateArticle;
    }

    public Article()
    {

    }

    public int getDownloadPicture() {
        return downloadPicture;
    }

    public void setDownloadPicture(int downloadPicture) {
        this.downloadPicture = downloadPicture;
    }

    public Article(int idFlagArticle)
    {
        this.idFlagArticle = idFlagArticle;
    }

    public Article(MyDbHelper db)
    {
        this.db = db;
    }



    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getTitleArticle() {
        return titleArticle;
    }

    public void setTitleArticle(String titleArticle) {
        this.titleArticle = titleArticle;
    }

    public String getContentArticle() {
        return contentArticle;
    }

    public void setContentArticle(String contentArticle) {
        this.contentArticle = contentArticle;
    }

    public String getUrlArticle() {
        return urlArticle;
    }

    public void setUrlArticle(String urlArticle) {
        this.urlArticle = urlArticle;
    }

    public int getIdFlagArticle() {
        return idFlagArticle;
    }

    public void setIdFlagArticle(int idFlagArticle) {
        this.idFlagArticle = idFlagArticle;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getDateArticle()
    {
        return dateArticle;
    }

    public void setDateArticle(String dateArticle){
        this.dateArticle = dateArticle;
    }

    public MyDbHelper getDb() {
        return db;
    }

    public void setDb(MyDbHelper db) {
        this.db = db;
    }

    @Override
    public String toString() {
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
