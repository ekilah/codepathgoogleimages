package com.mekilah.codepath.googleimagessearch.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mekilah.codepath.googleimagessearch.R;
import com.mekilah.codepath.googleimagessearch.models.SearchResultItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mekilah on 2/11/15.
 */
public class SearchResultItemGridAdapter extends ArrayAdapter<SearchResultItem>{

    public SearchResultItemGridAdapter(Context context, List<SearchResultItem> objects){
        super(context, 0, objects);
    }

    class SearchResultItemGridViewHolder{
        ImageView ivResultItem;
        TextView tvResultTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SearchResultItemGridViewHolder viewHolder;
        //Log.i("IMAGES", "getView called in grid adapter for position " + position);
        if(convertView == null || convertView.getTag() == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_item, parent, false);
            viewHolder = new SearchResultItemGridViewHolder();
            viewHolder.ivResultItem = (ImageView) convertView.findViewById(R.id.ivResultItem);
            viewHolder.tvResultTitle = (TextView) convertView.findViewById(R.id.tvResultTitle);

        }else{
            viewHolder = (SearchResultItemGridViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).load(getItem(position).tbUrl).resize(getItem(position).tbWidth, getItem(position).tbHeight).centerInside().placeholder(R.drawable.loading).into(viewHolder.ivResultItem);

        viewHolder.tvResultTitle.setText(Html.fromHtml(getItem(position).title));
        convertView.setTag(viewHolder);
        return convertView;
    }
}
