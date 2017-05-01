package UI.MainWindow;

import Constants.mainConstants;
import DAL.DataProvider;
import DAL.Models.News;
import DAL.Models.NewsPhoto;
import DAL.Models.NewsPresse;
import Logic.LogicController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import Logger.Logger;
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
            selectedId = DataProvider.instance().getList().indexOf(newValue);
            //Logger.instance().add("Selected item: " + selectedId);
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String _currentText = searchField.getText();
            newsListView.itemsProperty().bind(DataProvider.instance().getList(x-> x.getTitre().contains(_currentText) ||
                    x.getAuteur().contains(_currentText) || x.getSource().toString().contains(_currentText)));
        });
    }

    public void populateTableView() {
        newsListView.itemsProperty().bind(DataProvider.instance().getList());
    }

    @FXML
    private void cancel() {
        searchField.setText("");
        populateTableView();
    }

    @FXML
    private void newCollection() {
        LogicController.instance().newCollection();
        populateTableView();
    }

    @FXML
    private void loadCollection() {
        LogicController.instance().loadCollection();
        populateTableView();
    }

    @FXML
    private void saveCollection() {
        LogicController.instance().saveCollection();
    }

    @FXML
    private void addArticle() {
        LogicController.instance().switchMode(mainConstants.ADDING_ARTICLE_MODE);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UI/AddArticleWindow/addArticleWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Article Window");
            stage.setScene(new Scene(root1, 600, 400));
            stage.show();
        }
        catch (IOException e) {
            Logger.instance().add("Error: " + e.getMessage());
        }
    }

    @FXML
    private void addPhoto() {
        LogicController.instance().switchMode(mainConstants.ADDING_PHOTO_MODE);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UI/AddPhotoWindow/addPhotoWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Photo Window");
            stage.setScene(new Scene(root1, 600, 400));
            stage.show();
        }
        catch (IOException e) {
            Logger.instance().add("Error: " + e.getMessage());
        }
    }

    @FXML
    private void edit() {
        if (selectedId!=-1) {
           News _news = DataProvider.instance().getArticle(selectedId);
            if (_news instanceof NewsPhoto) {
                LogicController.instance().setNewsToBeEdit(_news);
                editPhoto();
            }
            else if (_news instanceof NewsPresse){
                LogicController.instance().setNewsToBeEdit(_news);
                editArticle();
            }
        }
        else {
            Logger.instance().add("Select item first!");
        }
    }

    private void editPhoto() {
        LogicController.instance().switchMode(mainConstants.EDITING_PHOTO_MODE);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UI/AddPhotoWindow/addPhotoWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Photo Window");
            stage.setScene(new Scene(root1, 600, 400));
            stage.show();
        }
        catch (IOException e) {
            Logger.instance().add("Error: " + e.getMessage());
        }
    }

    private void editArticle() {
        LogicController.instance().switchMode(mainConstants.EDITING_ARTICLE_MODE);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UI/AddArticleWindow/addArticleWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Article Window");
            stage.setScene(new Scene(root1, 600, 400));
            stage.show();
        }
        catch (IOException e) {
            Logger.instance().add("Error: " + e.getMessage());
        }
    }

    @FXML
    private void deleteArticle() {
        if (selectedId != -1) {
            if (DataProvider.instance().delete(selectedId)) {
                selectedId = -1;
            }
        }
        else {
            Logger.instance().add("Select item to be deleted first!");
        }
    }


}
