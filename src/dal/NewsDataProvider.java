package dal;

import constants.Constants;
import dal.models.News;
import logger.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by Oleg Dovzhenko on 28.04.2017.
 */
public class NewsDataProvider implements Serializable{
    private static NewsDataProvider _instance;
    private ArrayList<News> newsCollection;
    private ListProperty<String> newsCollectionProperty;
    private ArrayList<String> newsStringCollection;

    private NewsDataProvider() {
        newCollection();
    }

    public static NewsDataProvider instance() {
        if (_instance == null)
            _instance = new NewsDataProvider();
        return _instance;
    }

    public void newCollection() {
        newsCollection = new ArrayList<>();
        newsStringCollection = new ArrayList<>();
        newsCollectionProperty = new SimpleListProperty<>();
        Logger.instance().addMessage("new Collection created");
    }

    public boolean saveCollection() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(Constants.FILE_NAME));
            objectOutputStream.writeObject(newsCollection);
            objectOutputStream.close();
            Logger.instance().addMessage("Collection saved successfully");
            return true;
        }
        catch (FileNotFoundException exp) {
            Logger.instance().addMessage("Crashed with: " + exp.getMessage());
        }
        catch (IOException exp) {
            Logger.instance().addMessage("Crashed with: " + exp.getMessage());
        }
        return false;
    }

    public boolean loadCollection() {
        try {
            newCollection();
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(Constants.FILE_NAME));
            newsCollection = (ArrayList<News>)objectInputStream.readObject();
            for (News _news : newsCollection) {
                newsStringCollection.add("#" + newsCollection.indexOf(_news)+ ": " +_news.toString());
            }
            newsCollectionProperty.set(FXCollections.observableArrayList(newsStringCollection));
            objectInputStream.close();
            Logger.instance().addMessage("File loaded successfully : " + newsCollection.size() + " items loaded." );
            return true;
        }
        catch (FileNotFoundException exp) {
            Logger.instance().addMessage("Crashed with: " + exp.getMessage());
        }
        catch (IOException | ClassNotFoundException exp) {
            Logger.instance().addMessage("Crashed with: " + exp.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        if (id >= 0 && id < newsCollection.size()) {
            newsCollection.remove(id );
            newsStringCollection = new ArrayList<>();
            newsCollection.forEach(news -> newsStringCollection.add("#" + newsCollection.indexOf(news)+ ": " +news.toString()));
            newsCollectionProperty.set(FXCollections.observableArrayList(newsStringCollection));
            Logger.instance().addMessage("Article #" + id + " was successfully removed");
            return true;
        }
        return false;
    }

    public void add(News news) {
        newsCollection.add(news);
        newsStringCollection.add("#" + newsCollection.indexOf(news)+ ": " +news.toString());
        newsCollectionProperty.set(FXCollections.observableArrayList(newsStringCollection));
        //newsCollection.sort(News::compareTo);
    }

    public News getArticle(int id) {
        if (id >= 0 && id < newsCollection.size())
            return newsCollection.get(id);
        return null;
    }

    public boolean editArticle(int id, News news) {
        if (id >= 0 && id <= newsCollection.size()) {
            newsCollection.set(id, news);
            newsStringCollection.set(id, "#" + id +": " +news.toString());
            newsCollectionProperty.set(FXCollections.observableArrayList(newsStringCollection));
            return true;
        }
        return false;
    }

    public  ListProperty<String> getList() {
        return newsCollectionProperty;
    }

    public  ListProperty<String> getList(Predicate<News> tester) {
        ArrayList<String> _finalCollection = new ArrayList<>();
        ListProperty<String> _finalCollectionProperty = new SimpleListProperty<>();

        newsCollection.stream().filter(tester::test).forEach(news -> _finalCollection.add("#" + newsCollection.indexOf(news)+ ": " + news.toString()));

        _finalCollectionProperty.set(FXCollections.observableArrayList(_finalCollection));
        return _finalCollectionProperty;
    }

    public int getIndexOfArticle(News article){
        return newsCollection.indexOf(article);
    }
}
