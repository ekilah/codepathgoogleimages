package com.mekilah.codepath.googleimagessearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mekilah.codepath.googleimagessearch.R;
import com.mekilah.codepath.googleimagessearch.models.SearchSettings;

import java.util.Collections;
import java.util.HashMap;

public class AdvancedSettingsActivity extends ActionBarActivity{

    LinearLayout llSettingsItems;
    LinearLayout llSettingsItemsBottom;

    HashMap<String, SearchSettings.SettingDetails> oldSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_settings);



        llSettingsItems = (LinearLayout)this.findViewById(R.id.llSettingsItems);
        llSettingsItemsBottom = (LinearLayout)this.findViewById(R.id.llSettingsItemsBottom);

        //for each option (key), populate settings page
        for(final String key : Collections.list(SearchSettings.getInstance(this).options.keys()) ){
            Log.w("IMAGES", "populating settings for key: " + key);

            switch(key){

                case SearchSettings.KEY_SITE:
                    //site is a text box instead of a spinner

                    View settingViewEditText = LayoutInflater.from(this).inflate(R.layout.setting_item_edittext, llSettingsItemsBottom, false);

                    ((TextView) settingViewEditText.findViewById(R.id.tvSettingName2)).setText(this.getResources().getString(this.getResources().getIdentifier(key, "string", this.getPackageName())));

                    ((EditText) settingViewEditText.findViewById(R.id.etSettingChoice)).setText(SearchSettings.getInstance(this).choices.get(key).apiParamValue); //saved choice for site parameter

                    llSettingsItemsBottom.addView(settingViewEditText);

                    break;

                default:

                    View settingView = LayoutInflater.from(this).inflate(R.layout.setting_item_spinner, llSettingsItems, false);

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
                break;
            }
        }

        ((Button) findViewById(R.id.btnSettingsSave)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onSave();
            }
        });

        ((Button) findViewById(R.id.btnSettingsCancel)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onCancel();

            }
        });
        //undo point
        oldSettings = (HashMap<String, SearchSettings.SettingDetails>) SearchSettings.getInstance(this).choices.clone();

    }

    private void onCancel(){
        //reset settings
        SearchSettings.getInstance(AdvancedSettingsActivity.this).choices = oldSettings;
        setResult(SearchResultsActivity.RESULT_CODE_CANCEL);
        finish();
    }

    private void onSave(){
        //save edit text
        String site = ((EditText)llSettingsItemsBottom.findViewById(R.id.etSettingChoice)).getText().toString();
        SearchSettings.getInstance(AdvancedSettingsActivity.this).saveSiteSetting(site);

        setResult(SearchResultsActivity.RESULT_CODE_SEARCH);
        finish();
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

        //action bar back button
        if(id == android.R.id.home){
           onCancel();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
