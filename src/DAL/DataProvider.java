package DAL;

import Constants.mainConstants;
import DAL.Models.News;
import Logger.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by Oleg Dovzhenko on 28.04.2017.
 */
public class DataProvider implements Serializable{
    private static DataProvider _instance;
    private ArrayList<News> newsCollection;
    private ListProperty<String> newsCollectionProperty;
    private ArrayList<String> newsStringCollection;

    private DataProvider()
    {
        newCollection();
    }

    public static DataProvider instance()
    {
        if (_instance == null)
            _instance = new DataProvider();
        return _instance;
    }

    public void newCollection()
    {
        newsCollection = new ArrayList<>();
        newsStringCollection = new ArrayList<>();
        newsCollectionProperty = new SimpleListProperty<>();
        Logger.instance().add("new Collection created");
    }

    public boolean saveCollection()
    {
        try
        {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(mainConstants.FILE_NAME));
            objectOutputStream.writeObject(newsCollection);
            objectOutputStream.close();
            Logger.instance().add("Collection saved successfully");
            return true;
        }
        catch (FileNotFoundException exp)
        {
            Logger.instance().add("Crashed with: " + exp.getMessage());
        }
        catch (IOException exp)
        {
            Logger.instance().add("Crashed with: " + exp.getMessage());
        }
        return false;
    }

    public boolean loadCollection()
    {
        try
        {
            newCollection();
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(mainConstants.FILE_NAME));
            newsCollection = (ArrayList<News>)objectInputStream.readObject();
            for (News _news : newsCollection) {
                newsStringCollection.add("#" + newsCollection.indexOf(_news)+ ": " +_news.toString());
            }
            newsCollectionProperty.set(FXCollections.observableArrayList(newsStringCollection));
            objectInputStream.close();
            Logger.instance().add("File loaded successfully : " + newsCollection.size() + " items loaded." );
            return true;
        }
        catch (FileNotFoundException exp)
        {
            Logger.instance().add("Crashed with: " + exp.getMessage());
        }
        catch (IOException exp)
        {
            Logger.instance().add("Crashed with: " + exp.getMessage());
        }
        catch (ClassNotFoundException exp)
        {
            Logger.instance().add("Crashed with: " + exp.getMessage());
        }
        return false;
    }

    public boolean delete(int id)
    {
        if (id > 0 && id <= newsCollection.size()) {
            newsCollection.remove(id );
            newsStringCollection = new ArrayList<>();
            for (News _news : newsCollection) {
                newsStringCollection.add("#" + newsCollection.indexOf(_news)+ ": " +_news.toString());
            }
            newsCollectionProperty.set(FXCollections.observableArrayList(newsStringCollection));
            Logger.instance().add("Article #" + id + " was successfully removed");
            return true;
        }
        return false;
    }

    public void add(News news)
    {
        newsCollection.add(news);
        newsStringCollection.add("#" + newsCollection.indexOf(news)+ ": " +news.toString());
        newsCollectionProperty.set(FXCollections.observableArrayList(newsStringCollection));
        //newsCollection.sort(News::compareTo);
    }

    public News getArticle(int id)
    {
        if (newsCollection.contains(id))
            return newsCollection.get(id);
        return null;
    }

    public boolean editArticle(int id, News news)
    {
        if (newsCollection.contains(id)) {
            newsCollection.set(id, news);
            return true;
        }
        return false;
    }

    public  ListProperty<String> getList()
    {
        return newsCollectionProperty;
    }

    public  ListProperty<String> getList(Predicate<News> tester)
    {
        ArrayList<String> _finalCollection = new ArrayList<>();
        ListProperty<String> _finalCollectionProperty = new SimpleListProperty<>();
        _finalCollectionProperty.set(FXCollections.observableArrayList(_finalCollection));
        for (News _news : newsCollection) {
            if (tester.test(_news)) {
                _finalCollection.add("#" + newsCollection.indexOf(_news)+ ": " + _news.toString());
            }
        }

        return _finalCollectionProperty;
    }

}
