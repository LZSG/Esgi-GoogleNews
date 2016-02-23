package com.esgi.googlenews.Modeles;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * DbHelper
 */
public class DbHelper extends SQLiteOpenHelper
{

    private static final String DATABASE = "googleNews.db";

    public static final String DATABASE_TABLE_FLAG = "flag";
    public static final String NAME_FLAG = "name";

    public static final String DATABASE_TABLE_Article = "article";
    public static final String ID_ARTICLE = "idArticle";
    public static final String TITLE_ARTICLE = "titleArticle";
    public static final String CONTENT_ARTICLE = "contentArticle";
    public static final String URL_ARTICLE = "urlArticle";
    public static final String URL_PICTURE = "urlPicture";
    public static final String DOWNLOAD_PICTURE = "downloadPicture";
    public static final String ID_FLAG_ARTICLE = "idFlagArticle";
    public static final String DATE_ARTICLE = "dateArticle";

    public DbHelper (Context context)
    {
        super(context, DATABASE, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        this.onCreate(db);
    }

    @Override
    public void onCreate (SQLiteDatabase db)
    {
        db.execSQL("create table if not exists flag (id integer primary key autoincrement,name text)");
        db.execSQL("create table if not exists article (idArticle integer primary key autoincrement,titleArticle text,contentArticle text,urlArticle text,urlPicture text,downloadPicture integer,dateArticle text,idFlagArticle integer,FOREIGN KEY(idFlagArticle) REFERENCES flag(id))");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }


    /**
     * Delete the Flag and the Articles bind
     *
     * @param name string
     *
     * @return boolean
     */
    public Boolean deleteArticlesAndFlag (String name)
    {
        if (name == null) {
            return false;
        }
        int idFlag = this.getIDFlag(name);
        if (idFlag == -1) {
            return false;
        } else {
            if (this.deleteAllArticle(idFlag)) {
                if (this.deleteFlag(idFlag) > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                this.deleteFlag(idFlag);
                return false;
            }
        }
    }

    /*
    *
    * FLAG PART
    * */
    // can be for get the id and get the number of the element with the cursor
    public int getIDFlag (String name)
    {
        name = name.toLowerCase();
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select id from " + DATABASE_TABLE_FLAG + " where name = '" + name + "'", null);
        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                id = cur.getInt(cur.getColumnIndex("id"));
            } while (cur.moveToNext());
            return id;
        } else {
            return -1;
        }

    }

    /**
     * @param name string
     *
     * @return boolean
     */
    public boolean addFlag (String name)
    {
        if (name == null) {
            return false;
        }

        name = name.toLowerCase();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(NAME_FLAG, name);

        return (db.insert(DATABASE_TABLE_FLAG, null, content) >= 0);
    }

    /**
     * @param id int
     *
     * @return Integer
     */
    public int deleteFlag (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return (db.delete(DATABASE_TABLE_FLAG, "id=?", new String[] {String.valueOf(id)}));
    }

    /**
     * @return
     */
    public ArrayList<Flag> getAllFlags ()
    {
        ArrayList<Flag> listFlag = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select id,name from " + DATABASE_TABLE_FLAG, null);

        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                Flag flag = new Flag(cur.getColumnIndex("id"), cur.getString(cur.getColumnIndex("name")));
                listFlag.add(flag);
            } while (cur.moveToNext());
        }

        return listFlag;
    }

    /**
     * Delete all flag
     *
     * @return boolean
     */
    public Boolean cleanFlag ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return (db.delete(DATABASE_TABLE_FLAG, null, null) != 0);
    }

    /*
    *
    *   Article Flag
    *
    * */
    public boolean addArticle (Article art)
    {
        if ((art.getTitleArticle() == null) || (art.getDateArticle() == null) || (art.getIdFlagArticle() == 0)) {
            return false;
        }

        if (!this.articleExist(art.getTitleArticle())) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put(TITLE_ARTICLE, art.getTitleArticle());
            content.put(CONTENT_ARTICLE, art.getContentArticle());
            content.put(URL_ARTICLE, art.getUrlArticle());
            content.put(URL_PICTURE, art.getUrlPicture());
            content.put(DOWNLOAD_PICTURE, art.getDownloadPicture());
            content.put(ID_FLAG_ARTICLE, art.getIdFlagArticle());
            content.put(DATE_ARTICLE, art.getDateArticle().toString());

            return (db.insert(DATABASE_TABLE_Article, null, content) >= 0);
        }

        return false;
    }

    public Boolean deleteAllArticle (int id)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            return (db.delete(DATABASE_TABLE_Article, " " + ID_FLAG_ARTICLE + "=?", new String[] {String.valueOf(id)}) != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean articleExist (String name)
    {
        int nb = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select count(*) AS nombre from " + DATABASE_TABLE_Article + " where titleArticle = '" + name + "'", null);

        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                nb = cur.getInt(cur.getColumnIndex("nombre"));
            } while (cur.moveToNext());
        }

        return (nb > 0);

    }

    public String getUrlArticle (String titleArticle)
    {
        String url = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select " + URL_ARTICLE + " from " + DATABASE_TABLE_Article + " where titleArticle = '" + titleArticle + "'", null);

        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                url = cur.getString(cur.getColumnIndex(URL_ARTICLE));
            } while (cur.moveToNext());

        }
        return url;
    }

    public ArrayList<Article> getListArticle (int fid)
    {
        ArrayList<Article> listArr = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select * from " + DATABASE_TABLE_Article + " where " + ID_FLAG_ARTICLE + "=" + fid, null);

        if (cur.getCount() > 0) {

            int idArticle, fidFlag, downloadPicture;
            String title, content, urlArticle, urlPicture, date;
            cur.moveToFirst();

            do {
                idArticle = cur.getInt(cur.getColumnIndex(ID_ARTICLE));
                title = cur.getString(cur.getColumnIndex(TITLE_ARTICLE));
                content = cur.getString(cur.getColumnIndex(CONTENT_ARTICLE));
                urlArticle = cur.getString(cur.getColumnIndex(URL_ARTICLE));
                urlPicture = cur.getString(cur.getColumnIndex(URL_PICTURE));
                downloadPicture = cur.getInt(cur.getColumnIndex(DOWNLOAD_PICTURE));
                date = cur.getString(cur.getColumnIndex(DATE_ARTICLE));
                fidFlag = cur.getInt(cur.getColumnIndex(ID_FLAG_ARTICLE));
                listArr.add(new Article(idArticle, title, content, urlArticle, urlPicture, downloadPicture, date, fidFlag));
            } while (cur.moveToNext());
        }

        return listArr;
    }
}
