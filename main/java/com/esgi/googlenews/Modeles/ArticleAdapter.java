package com.esgi.googlenews.Modeles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.googlenews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaafranigabriel on 07/02/2016.
 */
public class ArticleAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ArticleAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class DataHandler
    {
        ImageView poster;
        TextView title;
        TextView rating;

    }


    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        DataHandler handler;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_layout,parent,false);
            handler = new DataHandler();
            handler.poster = (ImageView)row.findViewById(R.id.article_poster);
            handler.title = (TextView)row.findViewById(R.id.article_title);
            handler.rating = (TextView)row.findViewById(R.id.article_rating);
            row.setTag(handler);
        }else{
            handler = (DataHandler)row.getTag();
        }
        ArticleDataProvider dataProvider;
        dataProvider = (ArticleDataProvider)this.getItem(position);
        handler.poster.setImageBitmap(dataProvider.getArticle_poster_ressource());
        handler.title.setText(dataProvider.getArticle_title());
        handler.rating.setText(dataProvider.getArticle_rating());

        return row;

    }
}
