import DAL.Models.News;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Oleg Dovzhenko on 25.04.2017.
 */
public class BaseDeNews implements Serializable{
    private List<News> newsCollection;

    public BaseDeNews()
    {
        newsCollection = new ArrayList<News>();
    }

    public void add(News news)
    {
        newsCollection.add(news);
        newsCollection.sort(News::compareTo);
    }

    public void update(int id, News news)
    {
        //TODO:implement update
    }

    public boolean delete(int id)
    {
        if (id > 0 && id <= newsCollection.size()) {
            newsCollection.remove(id - 1);
            return true;
        }
        return false;
    }

    public void afficher(PrintWriter printWriter)
    {
        int _index=0;
        for(News _news : newsCollection)
        {
            _index++;
            printWriter.print("DAL.Models.News #" + _index + ": ");
            _news.afficher(printWriter);
        }
    }

    public void printNewsWithPredicate (
             Predicate<News> tester, PrintWriter printWriter) {
        int _index=0;
        for (News _news : newsCollection) {
            if (tester.test(_news)) {
                _index++;
                printWriter.print("DAL.Models.News #" + _index + ": ");
                _news.afficher(printWriter);
            }
        }
    }

    /*public ArrayList<DAL.Models.News> search(String keyWord)
    {

    }*/
}
