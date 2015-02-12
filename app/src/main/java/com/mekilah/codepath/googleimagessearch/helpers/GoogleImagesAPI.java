package com.mekilah.codepath.googleimagessearch.helpers;

/**
 * Created by mekilah on 2/10/15.
 */
public class GoogleImagesAPI{

    public static final String URL_BASE = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";

    //REQUEST PARAMS & VALUES
    public static final String URL_PARAMETER_QUERY = "&q=";
    public static final String URL_PARAMETER_SITESEARCH = "&as_sitesearch=";
    public static final String URL_PARAMETER_COLOR = "&imgcolor=";
    public static final String URL_PARAMETER_SIZE = "&imgsz=";
    public static final String URL_PARAMETER_TYPE = "&imgtype=";
    public static final String URL_PARAMETER_SAFE = "&safe=";
    public static final String URL_PARAMETER_START = "&start=";

    public static final String COLOR_ANY = "";
    public static final String COLOR_BLACK = "black";
    public static final String COLOR_BLUE = "blue";
    public static final String COLOR_BROWN = "brown";
    public static final String COLOR_GRAY = "gray";
    public static final String COLOR_GREEN = "green";
    public static final String COLOR_ORANGE = "orange";
    public static final String COLOR_PINK = "pink";
    public static final String COLOR_PURPLE = "purple";
    public static final String COLOR_RED = "red";
    public static final String COLOR_TEAL = "teal";
    public static final String COLOR_WHITE = "white";
    public static final String COLOR_YELLOW = "yellow";

    public static final String SIZE_ANY = "";
    public static final String SIZE_ICON = "icon";
    public static final String SIZE_SMALL = "small";
    public static final String SIZE_MEDIUM = "medium";
    public static final String SIZE_LARGE = "large";
    public static final String SIZE_XL = "xlarge";
    public static final String SIZE_XXL = "xxlarge";
    public static final String SIZE_HUGE = "huge";

    public static final String TYPE_ANY = "";
    public static final String TYPE_FACE = "face";
    public static final String TYPE_PHOTO = "photo";
    public static final String TYPE_CLIPART = "clipart";
    public static final String TYPE_LINEART = "lineart";

    public static final String SAFE_ANY = "";
    public static final String SAFE_ACTIVE = "active";
    public static final String SAFE_MODERATE = "moderate";
    public static final String SAFE_OFF = "off";


    //RESPONSE PARAMS
    public static String RESPONSE_DATA = "responseData";
    public static String RESPONSE_CURSOR = "cursor";
    public static String RESPONSE_RESULTS = "results";
    public static String RESPONSE_DETAILS = "responseDetails";

    public static String RESPONSE_RESULT_WIDTH = "width";
    public static String RESPONSE_RESULT_HEIGHT = "height";
    public static String RESPONSE_RESULT_TB_WIDTH = "tbWidth";
    public static String RESPONSE_RESULT_TB_HEIGHT = "tbHeight";
    public static String RESPONSE_RESULT_URL = "unescapedUrl";
    public static String RESPONSE_RESULT_TB_URL = "tbUrl";
    public static String RESPONSE_RESULT_VISIBLE_URL = "visibleUrl";
    public static String RESPONSE_RESULT_TITLE = "title";
    public static String RESPONSE_RESULT_ORIGINAL_CONTEXT_URL = "originalContextUrl";
    public static String RESPONSE_RESULT_CONTENT = "content";

    public static String RESPONSE_CURSOR_RESULTCOUNT = "resultCount";
    public static String RESPONSE_CURSOR_PAGES = "pages";
    public static String RESPONSE_CURSOR_PAGES_START = "start";
    public static String RESPONSE_CURSOR_PAGES_LABEL = "label";
    public static String RESPONSE_CURSOR_PAGES_CURRENTPAGE = "currentPageIndex";

}
