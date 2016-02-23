package com.esgi.googlenews.Modeles;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 *
 */
public class ParsingData
{

    private JSONObject jsonObject;
    private ArrayList<Article> listArticle = new ArrayList<>();

    /**
     * @param data string
     *
     * @throws JSONException
     */
    public ParsingData (String data) throws JSONException
    {
        data = this.escapeSpace(data);
        AsyncTask<String, Void, JSONObject> jsonObjectVar = new DataApi().execute(data);
        try {
            this.jsonObject = jsonObjectVar.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JSONObject json = null;
        try {
            json = (JSONObject) this.jsonObject.get("responseData");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArr = null;
        try {
            jsonArr = json.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Article article;
        for (int i = 0; i < jsonArr.length(); i++) {
            String imageUrl = null;
            if (jsonArr.getJSONObject(i).has("image")) {
                JSONObject jsonObject;
                jsonObject = (JSONObject) jsonArr.getJSONObject(i).get("image");

                imageUrl = jsonObject.get("tbUrl").toString();
            }

            article = new Article(
                    jsonArr.getJSONObject(i).get("titleNoFormatting").toString(),
                    jsonArr.getJSONObject(i).get("content").toString(),
                    jsonArr.getJSONObject(i).get("url").toString(),
                    imageUrl,
                    0,
                    jsonArr.getJSONObject(i).get("publishedDate").toString());

            this.listArticle.add(article);
        }
    }

    /**
     * @param data string
     *
     * @return string
     */
    private String escapeSpace (String data)
    {
        String regex = " ";
        data = data.replaceAll(regex, "%20");
        return data;
    }

    /**
     * @return
     */
    public ArrayList<Article> getListArticle ()
    {
        return listArticle;
    }
}
