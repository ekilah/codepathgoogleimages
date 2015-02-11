package com.mekilah.codepath.googleimagessearch.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mekilah.codepath.googleimagessearch.R;
import com.mekilah.codepath.googleimagessearch.models.SearchSettings;

import java.util.Collections;

public class AdvancedSettingsActivity extends ActionBarActivity{

    LinearLayout llSettingsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_settings);



        llSettingsItems = (LinearLayout)this.findViewById(R.id.llSettingsItems);

        //for each option (key), populate settings page
        for(final String key : Collections.list(SearchSettings.getInstance(this).options.keys()) ){
            Log.w("IMAGES", "populating settings for key: " + key);
            View settingView = LayoutInflater.from(this).inflate(R.layout.setting_item, llSettingsItems, false);

            ((TextView) settingView.findViewById(R.id.tvSettingName)).setText(this.getResources().getString(this.getResources().getIdentifier(key, "string", this.getPackageName())));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SearchSettings.getInstance(this).getListOfThisOptionLocalizedResources(key));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = (Spinner) settingView.findViewById(R.id.spinSettingChoices);
            spinner.setAdapter(adapter);
            int preselection = SearchSettings.getInstance(this).getIndexOfChoiceForKeyInOptions(key);
            spinner.setSelection(preselection);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                    boolean success = SearchSettings.getInstance(AdvancedSettingsActivity.this).setChoiceFromApiParamValue(key, SearchSettings.getInstance(AdvancedSettingsActivity.this).getListOfThisOptionApiParamValues(key).get(position));
                    Log.i("IMAGES", "onItemSeelcted position " + position + " for key: " + key + ". success? " + success);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent){
                    Log.w("IMAGES", "onNothingSelected setting selection to 0");
                    ((Spinner) parent).setSelection(0);

                }
            });

            llSettingsItems.addView(settingView);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advanced_settings, menu);
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
