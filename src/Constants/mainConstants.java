package Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Dovzhenko on 28.04.2017.
 */
public interface mainConstants {
   String FILE_NAME = "Data.dat";

   String EDITING_PHOTO_MODE = "editingPhoto";
   String EDITING_ARTICLE_MODE = "editingArticle";
   String ADDING_PHOTO_MODE = "addingPhoto";
   String ADDING_ARTICLE_MODE = "addingArticle";
   String NONE_MODE = "none";

   String EDITING_ARTICLE_TITLE = "Edit Article";
   String EDITING_PHOTO_TITLE = "Edit Photo";
   String ADDING_ARTICLE_TITLE = "Add new Article";
   String ADDING_PHOTO_TITLE = "Add new Photo";

   List<String> PHOTO_TYPES_LIST = new ArrayList<String>(){{
         add("JPG");
         add("GIF");
         add("JPEG");
         add("BMP");
         add("PNG");
         add("BAT");
   }};

   String ERROR_STYLE = "-fx-border-color: red ; -fx-border-width: 2px ;";
}
