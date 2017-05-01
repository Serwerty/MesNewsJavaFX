package Logger;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Dovzhenko on 28.04.2017.
 */
public class Logger {
    private static Logger _instance;
    private List<String> logList;
    private Logger() {
        logList = new ArrayList<>();
        logListProperty = new SimpleListProperty<>();
    }
    private ListProperty<String> logListProperty;

    public static Logger instance() {
        if (_instance == null )
            _instance = new Logger();
        return _instance;
    }

    public ListProperty<String> getList() {
        return logListProperty;
    }

    public void add(String logMes) {
       logList.add(logMes);
       logListProperty.set(FXCollections.observableArrayList(logList));
    }

}
