package com.esgi.googlenews.Modeles;

/**
 * Created by zaafranigabriel on 21/01/2016.
 */
public class Flag {

    private int id;
    private String name;
    private MyDbHelper db;




    public Flag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Flag(){

    }

    public Flag(String name)
    {
        this.name = name;
    }

    public Flag(MyDbHelper db){
        this.db = db;
    }



    public Flag getFlag(){
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
