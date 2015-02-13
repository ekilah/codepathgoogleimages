package com.mekilah.codepath.googleimagessearch.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mekilah.codepath.googleimagessearch.R;
import com.mekilah.codepath.googleimagessearch.helpers.OnSwipeTouchListener;
import com.mekilah.codepath.googleimagessearch.helpers.SwipableRelativeLayout;
import com.mekilah.codepath.googleimagessearch.models.SearchResultItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FullScreenSearchItemActivity extends ActionBarActivity{

    //intent request extras keys
    public static final String REQUEST_FULLSCREEN_RESULT_ITEM_LIST = "item_data";
    public static final String REQUEST_FULLSCREEN_RESULT_ITEM_POSITION = "item_position";

    private SearchResultItem searchResultItem;
    private List<SearchResultItem> searchResultItemList;
    private int position;

    private TextView tvTitle;
    private ImageView ivImage;
    private TextView tvContent;
    private TextView tvSite;
    private TextView tvDimensions;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_search_item);

        OnSwipeTouchListener listener = (new OnSwipeTouchListener(this){
            @Override
            public void onSwipeRight(){
                position--;
                if(position < 0){
                    position = searchResultItemList.size() - 1;
                }
                setupViews();
            }

            @Override
            public void onSwipeLeft(){
                position = (position + 1) % searchResultItemList.size();
                setupViews();
            }
        });

        ((SwipableRelativeLayout) findViewById(R.id.rlFullScreenActivity)).onSwipeTouchListenerToCall = listener;

        Intent i = getIntent();
        searchResultItemList = i.getParcelableArrayListExtra(FullScreenSearchItemActivity.REQUEST_FULLSCREEN_RESULT_ITEM_LIST);
        position = i.getIntExtra(FullScreenSearchItemActivity.REQUEST_FULLSCREEN_RESULT_ITEM_POSITION, -1);
        if(position < 0 || searchResultItemList == null || position >= searchResultItemList.size()){
            throw new ArrayIndexOutOfBoundsException();
        }

        this.setupViews();

    }

    private void setupViews(){
        searchResultItem = searchResultItemList.get(position);

        //get view references
        tvTitle = (TextView) findViewById(R.id.tvFullScreenTitle);
        ivImage = (ImageView) findViewById(R.id.ivFullscreenImage);
        tvContent = (TextView) findViewById(R.id.tvFullScreenContent);
        tvSite = (TextView) findViewById(R.id.tvFullscreenSite);
        tvDimensions = (TextView) findViewById(R.id.tvFullscreenDimensions);

        //populate view with search item
        tvTitle.setText(Html.fromHtml(searchResultItem.title));
        Picasso.with(this).load(searchResultItem.url)./*resize(searchResultItem.width, searchResultItem.height).*/placeholder(R.drawable.loading).into(ivImage);
        tvContent.setText(Html.fromHtml(searchResultItem.content));
        tvSite.setText(Html.fromHtml(getString(R.string.source_page_at) + "<a href='" + searchResultItem.originalContextUrl + "'>" + searchResultItem.visibleUrl + "</a>"), TextView.BufferType.SPANNABLE);
        tvSite.setMovementMethod(LinkMovementMethod.getInstance());
        tvDimensions.setText(getString(R.string.dimensions_colon) + searchResultItem.width + " x " + searchResultItem.height);

        //image tap to turn on/off title & content views
        ivImage.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                int newVisibility = FullScreenSearchItemActivity.this.tvTitle.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
                ((Vibrator)FullScreenSearchItemActivity.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);//haptic feedback
                tvTitle.setVisibility(newVisibility);
                tvContent.setVisibility(newVisibility);
                return true;
            }
        });

        Button btnOpenInBrowser = (Button) findViewById(R.id.btnFullScreenVisitSourcePage);
        btnOpenInBrowser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                sharingIntent.setData(Uri.parse(searchResultItem.url));
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.open_url_with)));
            }
        });

        Button btnOpenInApp = (Button) findViewById(R.id.btnFullscreenOpenInOtherApp);
        btnOpenInApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);

                ImageView siv = (ImageView) findViewById(R.id.ivFullscreenImage);
                Drawable mDrawable = siv.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Google images search result passed via intent", null);

                Uri bmpUri = Uri.parse(path);

                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, getString(R.string.open_image_with)));
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

        //action bar back button
        if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
