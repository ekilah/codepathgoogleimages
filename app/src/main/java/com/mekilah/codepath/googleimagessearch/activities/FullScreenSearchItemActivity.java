package com.mekilah.codepath.googleimagessearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mekilah.codepath.googleimagessearch.R;
import com.mekilah.codepath.googleimagessearch.models.SearchResultItem;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class FullScreenSearchItemActivity extends ActionBarActivity{

    //intent request extras keys
    public static final String REQUEST_FULLSCREEN_RESULT_ITEM_DATA = "item_data";

    private SearchResultItem searchResultItem;

    private TextView tvTitle;
    private ImageView ivImage;
    private TextView tvContent;
    private TextView tvSite;
    private TextView tvDimensions;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_search_item);

        Intent i = getIntent();
        searchResultItem = (SearchResultItem) i.getParcelableExtra(FullScreenSearchItemActivity.REQUEST_FULLSCREEN_RESULT_ITEM_DATA);

        //get view references
        tvTitle = (TextView) findViewById(R.id.tvFullScreenTitle);
        ivImage = (ImageView) findViewById(R.id.ivFullscreenImage);
        tvContent = (TextView) findViewById(R.id.tvFullScreenContent);
        tvSite = (TextView) findViewById(R.id.tvFullscreenSite);
        tvDimensions = (TextView) findViewById(R.id.tvFullscreenDimensions);

        //populate view with search item
        tvTitle.setText(Html.fromHtml(searchResultItem.title));
        Picasso.with(this).load(searchResultItem.url).resize(searchResultItem.width, searchResultItem.height).centerInside().placeholder(R.drawable.ic_launcher).into(ivImage);
        tvContent.setText(Html.fromHtml(searchResultItem.content));
        tvSite.setText(Html.fromHtml("Site: " + searchResultItem.url));
        tvDimensions.setText(searchResultItem.width + " x " + searchResultItem.height);

        //image tap to turn on/off title & content views
        ivImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int newVisibility = FullScreenSearchItemActivity.this.tvTitle.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;

                tvTitle.setVisibility(newVisibility);
                tvContent.setVisibility(newVisibility);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_full_screen_search_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
