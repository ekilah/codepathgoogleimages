package com.mekilah.codepath.googleimagessearch.models;

import android.content.Context;
import android.util.Log;

import com.mekilah.codepath.googleimagessearch.R;
import com.mekilah.codepath.googleimagessearch.helpers.GoogleImagesAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by mekilah on 2/10/15.
 */
public class SearchSettings {
    public static final String KEY_SIZE = "image_size";
    public static final String KEY_COLOR = "image_color";
    public static final String KEY_TYPE = "image_type";
    public static final String KEY_SAFE = "image_safety";

    /*
    "image_size" => { [ SettingsDetails("sm" , R.string.small_string_resource) ] }
    */
    public Hashtable< String, List<SettingDetails> > options;
    public HashMap<String, SettingDetails> choices; // maps KEY_SIZE to settingdetail object
    //public static List<String> validChoiceKeys = Arrays.asList(KEY_COLOR, KEY_SAFE, KEY_TYPE, KEY_SIZE);

    private static SearchSettings ourInstance;

    private Context context;

    public static SearchSettings getInstance(Context context){
        if(ourInstance == null){
            ourInstance = new SearchSettings(context);
            ourInstance.init();
        }
        ourInstance.context = context;
        return ourInstance;
    }

    private SearchSettings(Context context){
        this.context = context;
        this.options = new Hashtable<>(4);
        this.choices = new HashMap<>(4);
    }

    private void init(){
        List<SettingDetails> imageSizes = new ArrayList<>(8);
        imageSizes.add(new ImageSizeSettingDetails(GoogleImagesAPI.SIZE_ANY, R.string.image_size_label_any));
        imageSizes.add(new ImageSizeSettingDetails(GoogleImagesAPI.SIZE_ICON, R.string.image_size_label_icon));
        imageSizes.add(new ImageSizeSettingDetails(GoogleImagesAPI.SIZE_SMALL, R.string.image_size_label_small));
        imageSizes.add(new ImageSizeSettingDetails(GoogleImagesAPI.SIZE_MEDIUM, R.string.image_size_label_medium));
        imageSizes.add(new ImageSizeSettingDetails(GoogleImagesAPI.SIZE_LARGE, R.string.image_size_label_large));
        imageSizes.add(new ImageSizeSettingDetails(GoogleImagesAPI.SIZE_XL, R.string.image_size_label_xlarge));
        imageSizes.add(new ImageSizeSettingDetails(GoogleImagesAPI.SIZE_XXL, R.string.image_size_label_xxlarge));
        imageSizes.add(new ImageSizeSettingDetails(GoogleImagesAPI.SIZE_HUGE, R.string.image_size_label_huge));
        this.options.put(SearchSettings.KEY_SIZE, imageSizes);
        this.choices.put(SearchSettings.KEY_SIZE, imageSizes.get(0));

        List<SettingDetails> imageColors = new ArrayList<>(13);
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_ANY, R.string.image_color_label_any));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_BLACK, R.string.image_color_label_black));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_BLUE, R.string.image_color_label_blue));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_BROWN, R.string.image_color_label_brown));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_GRAY, R.string.image_color_label_gray));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_GREEN, R.string.image_color_label_green));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_ORANGE, R.string.image_color_label_orange));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_PINK, R.string.image_color_label_pink));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_PURPLE, R.string.image_color_label_purple));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_RED, R.string.image_color_label_red));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_TEAL, R.string.image_color_label_teal));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_WHITE, R.string.image_color_label_white));
        imageColors.add(new ImageColorSettingDetails(GoogleImagesAPI.COLOR_YELLOW, R.string.image_color_label_yellow));
        this.options.put(SearchSettings.KEY_COLOR, imageColors);
        this.choices.put(SearchSettings.KEY_COLOR, imageColors.get(0));

        List<SettingDetails> imageTypes = new ArrayList<>(5);
        imageTypes.add(new ImageTypeSettingDetails(GoogleImagesAPI.TYPE_ANY, R.string.image_type_label_any));
        imageTypes.add(new ImageTypeSettingDetails(GoogleImagesAPI.TYPE_FACE, R.string.image_type_label_face));
        imageTypes.add(new ImageTypeSettingDetails(GoogleImagesAPI.TYPE_PHOTO, R.string.image_type_label_photo));
        imageTypes.add(new ImageTypeSettingDetails(GoogleImagesAPI.TYPE_CLIPART, R.string.image_type_label_clipart));
        imageTypes.add(new ImageTypeSettingDetails(GoogleImagesAPI.TYPE_LINEART, R.string.image_type_label_lineart));
        this.options.put(SearchSettings.KEY_TYPE, imageTypes);
        this.choices.put(SearchSettings.KEY_TYPE, imageTypes.get(0));

        List<SettingDetails> imageSafeties = new ArrayList<>(4);
        imageSafeties.add(new ImageSafeSettingDetails(GoogleImagesAPI.SAFE_ANY, R.string.image_safe_label_any));
        imageSafeties.add(new ImageSafeSettingDetails(GoogleImagesAPI.SAFE_ACTIVE, R.string.image_safe_label_active));
        imageSafeties.add(new ImageSafeSettingDetails(GoogleImagesAPI.SAFE_MODERATE, R.string.image_safe_label_moderate));
        imageSafeties.add(new ImageSafeSettingDetails(GoogleImagesAPI.SAFE_OFF, R.string.image_safe_label_off));
        this.options.put(SearchSettings.KEY_SAFE, imageSafeties);
        this.choices.put(SearchSettings.KEY_SAFE, imageSafeties.get(0));
    }

    public boolean isValidOptionKey(String key){
        return options.containsKey(key);
    }

    public int getIndexOfChoiceForKeyInOptions(String key){
        if(!isValidOptionKey(key)){
            return -1;
        }
        for(int i=0; i < options.get(key).size(); ++i){
            String optionsVal = options.get(key).get(i).apiParamValue;
            String choicesVal = choices.get(key).apiParamValue;
            if(optionsVal.equals(choicesVal)){
                return i;
            }
        }

        return -1;
    }

    public SettingDetails getSettingDetailsIfValidLocalizedResourceForOption(String key, String localizedResource){
        if(!isValidOptionKey(key)){
            return null;
        }
        SettingDetails sd = null;
        for(SettingDetails setting : options.get(key)){
            if(setting.hasLocalizedResource(localizedResource)){
                sd = setting;
                break;
            }
        }
        return sd;
    }

    public SettingDetails getSettingDetailsIfValidApiParamValueForOption(String key, String apiParamValue){
        if(!isValidOptionKey(key)){
            return null;
        }
        SettingDetails sd = null;
        for(SettingDetails setting : options.get(key)){
            if(setting.hasApiParamValue(apiParamValue)){
                sd = setting;
                break;
            }
        }
        return sd;
    }

    public boolean setChoiceFromLocalizedResourceString(String key, String localizedResource){
        SettingDetails sd = getSettingDetailsIfValidLocalizedResourceForOption(key, localizedResource);

        if(sd == null){
            return false;
        }

        choices.put(key, sd);
        return true;
    }

    public boolean setChoiceFromApiParamValue(String key, String apiParamValue){
        SettingDetails sd = getSettingDetailsIfValidApiParamValueForOption(key, apiParamValue);

        if(sd == null){
            return false;
        }

        choices.put(key, sd);
        Log.i("IMAGES", "Succesfully set {k,v} = {" + key + "," + sd.apiParamValue + "} as saved setting.");
        return true;
    }

    public List<String> getListOfThisOptionLocalizedResources(String key){
        if(!isValidOptionKey(key)){
            return null;
        }

        List<String> list = new ArrayList<String>(options.get(key).size());
        for(SettingDetails details : options.get(key)){
            list.add(details.localizedResource);
        }

        return list;
    }

    public List<String> getListOfThisOptionApiParamValues(String key){
        if(!isValidOptionKey(key)){
            return null;
        }

        List<String> list = new ArrayList<String>(options.get(key).size());
        for(SettingDetails details : options.get(key)){
            list.add(details.apiParamValue);
        }

        return list;
    }

    public static class SettingDetails{
        public String apiParamValue;
        public String apiParam;
        public int resourceID;
        public String localizedResource;

        public SettingDetails(String apv, int rid){
            this.apiParamValue = apv;
            this.resourceID = rid;
            this.localizedResource = SearchSettings.ourInstance.context.getResources().getString(resourceID);

        }

        public boolean hasApiParamValue(String param){
            return this.apiParamValue.equals(param);
        }

        public boolean hasResourceID(int rid){
            return rid == this.resourceID;
        }

        public boolean hasLocalizedResource(String r){
            return this.localizedResource.equals(r);
        }
    }

    public static class ImageSizeSettingDetails extends SettingDetails{
        public ImageSizeSettingDetails(String apv, int rid){
            super(apv, rid);
            this.apiParam = GoogleImagesAPI.URL_PARAMETER_SIZE;
        }
    }

    public static class ImageColorSettingDetails extends SettingDetails{
        public ImageColorSettingDetails(String apv, int rid){
            super(apv, rid);
            this.apiParam = GoogleImagesAPI.URL_PARAMETER_COLOR;
        }
    }

    public static class ImageTypeSettingDetails extends SettingDetails{
        public ImageTypeSettingDetails(String apv, int rid){
            super(apv, rid);
            this.apiParam = GoogleImagesAPI.URL_PARAMETER_TYPE;
        }
    }

    public static class ImageSafeSettingDetails extends SettingDetails{
        public ImageSafeSettingDetails(String apv, int rid){
            super(apv, rid);
            this.apiParam = GoogleImagesAPI.URL_PARAMETER_SAFE;
        }
    }

    public String getGoogleImagesAPIRequestParamsFromSavedSettings(){
        StringBuilder stringBuilder = new StringBuilder();
        for(String key: this.choices.keySet()){
            SettingDetails details = this.choices.get(key);
            stringBuilder.append(details.apiParam + details.apiParamValue);
        }
        return stringBuilder.toString();
    }

}
