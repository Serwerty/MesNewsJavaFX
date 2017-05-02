package service;

import constants.Constants;
import dal.NewsDataProvider;
import dal.models.News;
import logger.Logger;

/**
 * Created by Oleg Dovzhenko on 28.04.2017.
 */
public class NewsService {
    private static NewsService _instance;
    private NewsService() {
        currentMode = Constants.NONE_MODE;
    }

    private News newsToBeEdit;

    public static NewsService instance() {
        if (_instance == null)
            _instance = new NewsService();
        return _instance;
    }

    private String currentMode;

    public String getCurrentMode() {
        return currentMode;
    }

    public void switchMode(String mode) {
        switch (mode) {
            case Constants.ADDING_PHOTO_MODE :      currentMode = mode;                     break;
            case Constants.EDITING_PHOTO_MODE :     currentMode = mode;                     break;
            case Constants.ADDING_ARTICLE_MODE :    currentMode = mode;                     break;
            case Constants.EDITING_ARTICLE_MODE :   currentMode = mode;                     break;
            default:                                    currentMode = Constants.NONE_MODE;  break;
        }
    }

    public void saveCollection() {
        NewsDataProvider.instance().saveCollection();
    }

    public void loadCollection() {
        NewsDataProvider.instance().loadCollection();
    }

    public void newCollection() {
        NewsDataProvider.instance().newCollection();
    }

    public News getNewsToBeEdit() {
        return newsToBeEdit;
    }

    public void setNewsToBeEdit(News newsToBeEdit) {
        this.newsToBeEdit = newsToBeEdit;
    }

    public void addNews(News newsToBeAdded) {
        NewsDataProvider.instance().add(newsToBeAdded);
        Logger.instance().addMessage("Added successfully");
    }

    public void editNews(News editedNews){
        int id = NewsDataProvider.instance().getIndexOfArticle(newsToBeEdit);
        NewsDataProvider.instance().editArticle(id, editedNews);
        Logger.instance().addMessage("Edited successfully");
    }
}
