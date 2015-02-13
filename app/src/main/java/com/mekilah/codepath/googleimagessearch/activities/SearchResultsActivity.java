package com.mekilah.codepath.googleimagessearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mekilah.codepath.googleimagessearch.R;
import com.mekilah.codepath.googleimagessearch.adapters.SearchResultItemGridAdapter;
import com.mekilah.codepath.googleimagessearch.helpers.EndlessScrollListener;
import com.mekilah.codepath.googleimagessearch.helpers.GoogleImagesAPI;
import com.mekilah.codepath.googleimagessearch.models.SearchResultItem;
import com.mekilah.codepath.googleimagessearch.models.SearchSettings;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchResultsActivity extends ActionBarActivity{

    //intent request codes
    public static final int REQUEST_CODE_ADVANCED_SETTINGS = 100;

    //intent result codes
    public static final int RESULT_CODE_CANCEL = 400;
    public static final int RESULT_CODE_SEARCH = 401;
    public static final int RESULT_CODE_DONE = 402;

    private StaggeredGridView gvSearchResults;
    private SearchView searchView;
    private String lastQueryString;
    private String currentSearchText;

    private ArrayList<SearchResultItem> searchResults;
    private SearchResultItemGridAdapter searchResultsAdapter;

    private AsyncHttpClient asyncHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.google_image_search));
        setSupportActionBar(toolbar);

        gvSearchResults = (StaggeredGridView) findViewById(R.id.gvResults_SearchResults);
        searchResults = new ArrayList<SearchResultItem>();
        searchResultsAdapter = new SearchResultItemGridAdapter(this, this.searchResults);
        gvSearchResults.setAdapter(searchResultsAdapter);

        asyncHttpClient = new AsyncHttpClient();

        gvSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //bring to fullscreen view

                Intent intent = new Intent(SearchResultsActivity.this, FullScreenSearchItemActivity.class);
                intent.putExtra(FullScreenSearchItemActivity.REQUEST_FULLSCREEN_RESULT_ITEM_LIST, searchResults);
                intent.putExtra(FullScreenSearchItemActivity.REQUEST_FULLSCREEN_RESULT_ITEM_POSITION, position);
                //don't need to receive data from this activity right now.
                startActivity(intent);
            }
        });

        gvSearchResults.setOnScrollListener(new EndlessScrollListener(){
            @Override
            public void onLoadMore(int page, int totalItemsCount){
                getSearchResults(totalItemsCount);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s){
                newSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s){
                currentSearchText = s;
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.miAdvancedSearchSettings_SearchResultsMenu:
                this.onAdvancedMenuButtonPresesed(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onAdvancedMenuButtonPresesed(MenuItem menuItem){
        Intent intent = new Intent(this, AdvancedSettingsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADVANCED_SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case REQUEST_CODE_ADVANCED_SETTINGS:
                this.onSearchResultsActivityResult(resultCode, data);
            break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
            break;
        }
    }

    private void onSearchResultsActivityResult(int resultCode, Intent data){
        boolean executeSearch = false;
        switch(resultCode){
            case RESULT_CODE_CANCEL:
            break;
            case RESULT_CODE_SEARCH:
                //remember we want to search, fall through to done handler
                executeSearch = true;
            case RESULT_CODE_DONE:
                Toast.makeText(this, "Settings saved.", Toast.LENGTH_SHORT).show();
            break;

        }

        if(executeSearch){
            this.newSearch(currentSearchText);
        }
    }

    /**
     * Clear adapter and call for more search results.
     */
    private void newSearch(String searchQuery){
        if(searchQuery == null){
            return;
        }
        searchResultsAdapter.clear();
        this.lastQueryString = searchQuery;
        this.getSearchResults(0);
    }

    /**
     * Add to adapter. Caller should clear adapter if it needs to be cleared, this appends.
     */
    private void getSearchResults(final int paginationIndex){

        if(isNetworkAvailable() == false){
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            return;
        }
        String query = GoogleImagesAPI.URL_BASE + GoogleImagesAPI.URL_PARAMETER_QUERY + this.lastQueryString + GoogleImagesAPI.URL_PARAMETER_START + paginationIndex +  SearchSettings.getInstance(this).getGoogleImagesAPIRequestParamsFromSavedSettings();
        //Log.i("IMAGES", "Query: " + AsyncHttpClient.getUrlWithQueryString(true,query,null));
        asyncHttpClient.get(query, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                //Log.i("IMAGES", "Query success. Code: " + statusCode);
                try{
                    JSONObject responseDataObj = response.optJSONObject(GoogleImagesAPI.RESPONSE_DATA);
                    if(responseDataObj == null){
                        String details = response.getString(GoogleImagesAPI.RESPONSE_DETAILS);
                        if(paginationIndex == 64){
                            details = getString(R.string.api_limit_reached);
                        }
                        Toast.makeText(SearchResultsActivity.this, getString(R.string.error_colon) + details, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray resultsArr = responseDataObj.getJSONArray(GoogleImagesAPI.RESPONSE_RESULTS);
                    for(int i=0; i < resultsArr.length(); ++i){
                        SearchResultItem item = SearchResultItem.fromJSON(resultsArr.getJSONObject(i));
                        if(item != null){
                            SearchResultsActivity.this.searchResultsAdapter.add(item);
                        }
                    }

                }catch(JSONException e){
                    Toast.makeText(SearchResultsActivity.this, getString(R.string.json_parse_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                Toast.makeText(SearchResultsActivity.this, getString(R.string.network_error_colon) + statusCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
