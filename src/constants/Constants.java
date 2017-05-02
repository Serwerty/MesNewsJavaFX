package constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Dovzhenko on 28.04.2017.
 */
public final class Constants {
    private Constants() {

    }

    public static String FILE_NAME = "Data.dat";
    public static final String EDITING_PHOTO_MODE = "editingPhoto";
    public static final String EDITING_ARTICLE_MODE = "editingArticle";
    public static final String ADDING_PHOTO_MODE = "addingPhoto";
    public static final String ADDING_ARTICLE_MODE = "addingArticle";
    public static final String NONE_MODE = "none";

    public static final String EDITING_ARTICLE_TITLE = "Edit Article";
    public static final String EDITING_PHOTO_TITLE = "Edit Photo";
    public static final String ADDING_ARTICLE_TITLE = "Add new Article";
    public static final String ADDING_PHOTO_TITLE = "Add new Photo";

    public static final List<String> PHOTO_TYPES_LIST = new ArrayList<String>(){{
         add("JPG");
         add("GIF");
         add("JPEG");
         add("BMP");
         add("PNG");
         add("BAT");
   }};

    public static final String ERROR_STYLE = "-fx-border-color: red ; -fx-border-width: 2px ;";
    public static final String HTTP_TAG = "http://";

}
