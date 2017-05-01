package Logic;

import Constants.mainConstants;
import DAL.DataProvider;
import DAL.Models.News;

import java.lang.reflect.Type;

/**
 * Created by Oleg Dovzhenko on 28.04.2017.
 */
public class LogicController {
    private static LogicController _instance;
    private LogicController() {
        currentMode = mainConstants.NONE_MODE;
    }

    private News newsToBeEdit;

    public static LogicController instance() {
        if (_instance == null)
            _instance = new LogicController();
        return _instance;
    }

    private String currentMode;

    public String getCurrentMode() {
        return currentMode;
    }

    public void switchMode(String mode) {
        switch (mode) {
            case mainConstants.ADDING_PHOTO_MODE :      currentMode = mode;                     break;
            case mainConstants.EDITING_PHOTO_MODE :     currentMode = mode;                     break;
            case mainConstants.ADDING_ARTICLE_MODE :    currentMode = mode;                     break;
            case mainConstants.EDITING_ARTICLE_MODE :   currentMode = mode;                     break;
            default:                                    currentMode = mainConstants.NONE_MODE;  break;
        }
    }

    public void saveCollection() {
        DataProvider.instance().saveCollection();
    }

    public void loadCollection() {
        DataProvider.instance().loadCollection();
    }

    public void newCollection() {
        DataProvider.instance().newCollection();
    }

    public News getNewsToBeEdit() {
        return newsToBeEdit;
    }

    public void setNewsToBeEdit(News newsToBeEdit) {
        this.newsToBeEdit = newsToBeEdit;
    }

    public void addNews(News _newsToBeAdded) {
        DataProvider.instance().add(_newsToBeAdded);
    }

    public void editNews(int id, News _newsToBeEdited){
        DataProvider.instance().editArticle(id, _newsToBeEdited);
    }
}
