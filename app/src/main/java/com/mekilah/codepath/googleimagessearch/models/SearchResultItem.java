package com.mekilah.codepath.googleimagessearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.mekilah.codepath.googleimagessearch.helpers.GoogleImagesAPI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mekilah on 2/10/15.
 */
public class SearchResultItem implements Parcelable{
    public int width;
    public int height;
    public int tbWidth;
    public int tbHeight;
    public String url;
    public String tbUrl;
    public String visibleUrl;
    public String title;
    public String originalContextUrl;
    public String content;


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

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(tbWidth);
        dest.writeInt(tbHeight);
        dest.writeString(url);
        dest.writeString(tbUrl);
        dest.writeString(visibleUrl);
        dest.writeString(title);
        dest.writeString(originalContextUrl);
        dest.writeString(content);
    }

    public static final Creator<SearchResultItem> CREATOR = new Creator<SearchResultItem>(){
        @Override
        public SearchResultItem createFromParcel(Parcel source){
            SearchResultItem dest = new SearchResultItem();

            dest.width = source.readInt();
            dest.height = source.readInt();
            dest.tbWidth = source.readInt();
            dest.tbHeight = source.readInt();
            dest.url = source.readString();
            dest.tbUrl = source.readString();
            dest.visibleUrl = source.readString();
            dest.title = source.readString();
            dest.originalContextUrl = source.readString();
            dest.content = source.readString();

            return dest;
        }

        @Override
        public SearchResultItem[] newArray(int size){
            return new SearchResultItem[size];
        }
    };
}
