package com.mekilah.codepath.googleimagessearch.models;

import com.mekilah.codepath.googleimagessearch.helpers.GoogleImagesAPI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mekilah on 2/10/15.
 */
public class SearchResultItem{
    int width;
    int height;
    int tbWidth;
    int tbHeight;
    String url;
    String tbUrl;
    String visibleUrl;
    String title;
    String originalContextUrl;
    String content;


    public static SearchResultItem fromJSON(JSONObject json) throws JSONException{
        SearchResultItem item = new SearchResultItem();

        item.width = Integer.parseInt(json.getString(GoogleImagesAPI.RESPONSE_RESULT_WIDTH));
        item.height = Integer.parseInt(json.getString(GoogleImagesAPI.RESPONSE_RESULT_HEIGHT));
        item.tbWidth = Integer.parseInt(json.getString(GoogleImagesAPI.RESPONSE_RESULT_TB_WIDTH));
        item.tbHeight = Integer.parseInt(json.getString(GoogleImagesAPI.RESPONSE_RESULT_TB_HEIGHT));

        item.url = json.getString(GoogleImagesAPI.RESPONSE_RESULT_URL);
        item.tbUrl = json.getString(GoogleImagesAPI.RESPONSE_RESULT_TB_URL);
        item.visibleUrl = json.getString(GoogleImagesAPI.RESPONSE_RESULT_VISIBLE_URL);
        item.title = json.getString(GoogleImagesAPI.RESPONSE_RESULT_TITLE);
        item.originalContextUrl = json.getString(GoogleImagesAPI.RESPONSE_RESULT_ORIGINAL_CONTEXT_URL);
        item.content = json.getString(GoogleImagesAPI.RESPONSE_RESULT_CONTENT);

        return item;
    }
}
