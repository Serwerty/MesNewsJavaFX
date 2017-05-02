package window.mainwindow;

import constants.Constants;
import dal.NewsDataProvider;
import dal.models.News;
import dal.models.PhotoNews;
import dal.models.PresseNews;
import javafx.scene.control.Skin;
import service.NewsService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import logger.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Oleg Dovzhenko on 28.04.2017.
 */
public class MainWindowController implements Initializable{

    @FXML
    private ListView logList;
    @FXML
    private ListView newsListView;
    @FXML
    private TextField searchField;

    private int selectedId = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logList.itemsProperty().bind(Logger.instance().getList());
        loadCollection();
        populateTableView();

        newsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedId = NewsDataProvider.instance().getList().indexOf(newValue);
            //logger.instance().addMessage("Selected item: " + selectedId);
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String _currentText = searchField.getText();
            newsListView.itemsProperty().bind(NewsDataProvider.instance().getList(x-> x.getTitre().contains(_currentText) ||
                    x.getAuteur().contains(_currentText) || x.getSource().toString().contains(_currentText)));
        });
    }

    public void populateTableView() {
        newsListView.itemsProperty().bind(NewsDataProvider.instance().getList());
    }

    @FXML
    private void cancel() {
        searchField.setText("");
        populateTableView();
    }

    @FXML
    private void newCollection() {
        NewsService.instance().newCollection();
        populateTableView();
    }

    @FXML
    private void loadCollection() {
        NewsService.instance().loadCollection();
        populateTableView();
    }

    @FXML
    private void saveCollection() {
        NewsService.instance().saveCollection();
    }

    @FXML
    private void addArticle() {
        NewsService.instance().switchMode(Constants.ADDING_ARTICLE_MODE);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("window/addarticlewindow/AddArticleWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Article Window");
            stage.setScene(new Scene(root1, 600, 400));
            stage.show();
        }
        catch (IOException e) {
            Logger.instance().addMessage("Error: " + e.getMessage());
        }
    }

    @FXML
    private void addPhoto() {
        NewsService.instance().switchMode(Constants.ADDING_PHOTO_MODE);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("window/addphotowindow/AddPhotoWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Photo Window");
            stage.setScene(new Scene(root1, 600, 400));
            stage.show();
        }
        catch (IOException e) {
            Logger.instance().addMessage("Error: " + e.getMessage());
        }
    }

    @FXML
    private void edit() {
        if (selectedId!=-1) {
           News _news = NewsDataProvider.instance().getArticle(selectedId);
            if (_news instanceof PhotoNews) {
                NewsService.instance().setNewsToBeEdit(_news);
                editPhoto();
            }
            else if (_news instanceof PresseNews){
                NewsService.instance().setNewsToBeEdit(_news);
                editArticle();
            }
        }
        else {
            Logger.instance().addMessage("Select item first!");
        }
    }

    private void editPhoto() {
        NewsService.instance().switchMode(Constants.EDITING_PHOTO_MODE);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("window/addphotowindow/AddPhotoWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Photo Window");
            stage.setScene(new Scene(root1, 600, 400));
            stage.show();
        }
        catch (IOException e) {
            Logger.instance().addMessage("Error: " + e.getMessage());
        }
    }

    private void editArticle() {
        NewsService.instance().switchMode(Constants.EDITING_ARTICLE_MODE);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("window/addarticlewindow/AddArticleWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Article Window");
            stage.setScene(new Scene(root1, 600, 400));
            stage.show();
        }
        catch (IOException e) {
            Logger.instance().addMessage("Error: " + e.getMessage());
        }
    }

    @FXML
    private void deleteArticle() {
        if (selectedId != -1) {
            if (NewsDataProvider.instance().delete(selectedId)) {
                selectedId = -1;
            }
        }
        else {
            Logger.instance().addMessage("Select item to be deleted first!");
        }
    }


}
