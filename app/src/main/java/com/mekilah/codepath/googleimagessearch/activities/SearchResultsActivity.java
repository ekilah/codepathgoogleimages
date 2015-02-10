package com.mekilah.codepath.googleimagessearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.mekilah.codepath.googleimagessearch.R;
import com.mekilah.codepath.googleimagessearch.activities.models.SearchResultItem;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class SearchResultsActivity extends ActionBarActivity{

    //intent request codes
    public static final int REQUEST_CODE_ADVANCED_SETTINGS = 100;

    //intent result codes
    public static final int RESULT_CODE_CANCEL = 400;
    public static final int RESULT_CODE_SEARCH = 401;
    public static final int RESULT_CODE_DONE = 402;


    //intent request data keys
    public static final String REQUEST_DATA_SEARCH_STRING = "searchString";

    private EditText etSearchBox;
    private GridView gvSearchResults;

    private ArrayList<SearchResultItem> searchResults;
    private ArrayAdapter<SearchResultItem> searchResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        etSearchBox = (EditText) findViewById(R.id.etSearchBox_SearchResults);
        gvSearchResults = (GridView) findViewById(R.id.gvResults_SearchResults);
        searchResults = new ArrayList<SearchResultItem>();
        searchResultsAdapter = new ArrayAdapter<SearchResultItem>(this, R.layout.search_result_item, this.searchResults);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.miAdvancedSearchSettings_SearchResultsMenu:
                this.onAdvancedMenuButtonPresesed(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onAdvancedMenuButtonPresesed(MenuItem menuItem){
        Intent intent = new Intent(this, AdvancedSettingsActivity.class);
        intent.putExtra(REQUEST_DATA_SEARCH_STRING, this.etSearchBox.getText().toString());
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
            this.newSearch();
        }
    }

    /**
     * Clear adapter and call for more search results.
     */
    private void newSearch(){
        searchResultsAdapter.clear();
        this.getSearchResults();
    }

    /**
     * Add to adapter. Caller shoulcd clear adapter if it needs to be cleared, this appends.
     */
    private void getSearchResults(){

    }
}
