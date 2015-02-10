package com.mekilah.codepath.googleimagessearch.activities.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mekilah on 2/10/15.
 */
public class SearchSettings implements Parcelable{


    private static SearchSettings ourInstance = new SearchSettings();

    public static SearchSettings getInstance(){
        return ourInstance;
    }

    private SearchSettings(){
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){

    }
}
