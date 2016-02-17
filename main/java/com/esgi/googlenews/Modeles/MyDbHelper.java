package com.esgi.googlenews.Modeles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by zaafranigabriel on 21/01/2016.
 */
public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "googleNews.db";
    public static final String DATABASE_TABLE_FLAG = "flag";
    public static final String ID_FLAG = "id";
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

    public MyDbHelper(Context context) {
        super(context, DATABASE, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        this.onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists flag (id integer primary key autoincrement,name text)");
        db.execSQL("create table if not exists article (idArticle integer primary key autoincrement,titleArticle text,contentArticle text,urlArticle text,urlPicture text,downloadPicture integer,dateArticle text,idFlagArticle integer,FOREIGN KEY(idFlagArticle) REFERENCES flag(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    

    /*
    *
    * FLAG PART
    * */
    // can be for get the id and get the number of the element with the cursor
    public  int getIDFlag(String name)
    {
        name = name.toLowerCase();
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select id from "+DATABASE_TABLE_FLAG+" where name = '"+name+"'",null);
        if(cur.getCount()>0){
            cur.moveToFirst();
            do{
                id = cur.getInt(cur.getColumnIndex("id"));
            }while(cur.moveToNext());
            return id;
        }else{
            return -1;
        }

    }

    public Flag getFlag(String name){
        int id;
        name = name.toLowerCase();
        String names = null;
        Flag flag = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select id,name from "+DATABASE_TABLE_FLAG+" where name = '"+name+"'",null);
        if(cur.getCount()>0){
            cur.moveToFirst();
            do{
                id  = cur.getInt(cur.getColumnIndex("id"));
                names  = cur.getString(cur.getColumnIndex("name"));
                flag = new Flag(id,names);
            }while(cur.moveToNext());
        }else{
            return flag;
        }
        return flag;
    }

    public boolean addFlag(String name)
    {
        if(name==null)
        {
            return false;
        }
        name = name.toLowerCase();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(NAME_FLAG, name);
        if(db.insert(DATABASE_TABLE_FLAG, null, content)==-1)
            return false;
        return true;
    }

    public Integer deleteFlag(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return (db.delete(DATABASE_TABLE_FLAG, "id=?",new String[]{String.valueOf(id)}));
    }


    public ArrayList<Flag> getAllFlags()
    {
        ArrayList<Flag> listFlag = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select id,name from " + DATABASE_TABLE_FLAG, null);
        if(cur.getCount()>0){
            cur.moveToFirst();
            do{
                Flag flag = new Flag(cur.getColumnIndex("id"),cur.getString(cur.getColumnIndex("name")));
                listFlag.add(flag);
            }while(cur.moveToNext());
            return listFlag;
        }else{
            return null;
        }
    }

    public Boolean cleanFlag()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete(DATABASE_TABLE_FLAG, null, null)==0)
        {
            return false;
        }else
        {
            return true;
        }
    }

    /*
    *
    *   Article Flag
    *
    * */
    public boolean addArticle(Article art)
    {
        if((art.getTitleArticle()==null) || (art.getDateArticle()==null) || (art.getIdFlagArticle()==0))
        {
            return false;
        }
        if(this.articleExist(art.getTitleArticle())==false) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put(TITLE_ARTICLE, art.getTitleArticle());
            content.put(CONTENT_ARTICLE, art.getContentArticle());
            content.put(URL_ARTICLE, art.getUrlArticle());
            content.put(URL_PICTURE, art.getUrlPicture());
            content.put(DOWNLOAD_PICTURE,art.getDownloadPicture());
            content.put(ID_FLAG_ARTICLE, art.getIdFlagArticle());
            content.put(DATE_ARTICLE, art.getDateArticle().toString());

        if(db.insert(DATABASE_TABLE_Article, null, content)==-1)
            return false;
          return true;
        }
        return false;
    }

    public Boolean deleteAllArticle(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete(DATABASE_TABLE_FLAG, " "+ID_FLAG_ARTICLE+"=?",new String[]{String.valueOf(id)})==0)
            return false;
        return true;
    }


    public Boolean articleExist(String name){
        int nb = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select count(*) AS nombre from " + DATABASE_TABLE_Article + " where titleArticle = '" + name + "'", null);
        if(cur.getCount()>0){
            cur.moveToFirst();
            do{
                nb  = cur.getInt(cur.getColumnIndex("nombre"));
            }while(cur.moveToNext());
        }
        if(nb>0){
            return true;
        }
        return false;

    }

    public int getIdArticle(String name,int fid)
    {
        int idArticle;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select idArtcle from "+DATABASE_TABLE_Article+" where name = '"+name+"' and "+ID_FLAG_ARTICLE+"="+fid,null);
        if(cur.getCount()>0){
            cur.moveToFirst();
            do{
                idArticle  = cur.getInt(cur.getColumnIndex("idArtcle"));
            }while(cur.moveToNext());
        }else{
            return -1;
        }
        return idArticle;
    }

    public ArrayList<Article> getListArticle(int fid){
        ArrayList<Article> listeArr = new ArrayList<>();
        int idArticle,fidFlag,downloadPicture;
        String title,content,urlArticle,urlPicture;
        String date;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select * from "+DATABASE_TABLE_Article+" where "+ID_FLAG_ARTICLE+"="+fid,null);
        if(cur.getCount()>0){
            cur.moveToFirst();
            do{
                idArticle  = cur.getInt(cur.getColumnIndex(ID_ARTICLE));
                title = cur.getString(cur.getColumnIndex(TITLE_ARTICLE));
                content = cur.getString(cur.getColumnIndex(CONTENT_ARTICLE));
                urlArticle = cur.getString(cur.getColumnIndex(URL_ARTICLE));
                urlPicture = cur.getString(cur.getColumnIndex(URL_PICTURE));
                downloadPicture = cur.getInt(cur.getColumnIndex(DOWNLOAD_PICTURE));
                date = cur.getString(cur.getColumnIndex(DATE_ARTICLE));
                fidFlag  = cur.getInt(cur.getColumnIndex(ID_FLAG_ARTICLE));
                listeArr.add(new Article(idArticle,title,content,urlArticle,urlPicture,downloadPicture,date,fidFlag));
            }while(cur.moveToNext());
            return listeArr;
        }

        return null;
    }

}
