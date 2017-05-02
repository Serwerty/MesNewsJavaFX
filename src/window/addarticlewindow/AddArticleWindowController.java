package window.addarticlewindow;

import constants.Constants;
import dal.models.PresseNews;
import service.NewsService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Oleg Dovzhenko on 30.04.2017.
 */
public class AddArticleWindowController implements Initializable {

    @FXML
    private TextField titreField;
    @FXML
    private TextField auteurField;
    @FXML
    private TextField sourceField;
    @FXML
    private TextField secondSourceField;
    @FXML
    private RadioButton firstChoice;
    @FXML
    private RadioButton secondChoice;
    @FXML
    private Label titleLabel;
    @FXML
    private Button addButton;
    private Boolean isInputCorrect;

    @FXML
    public void addButtonAction() {
         isInputCorrect = true;

        String _titre = titreField.getText();
        if ("".equals(_titre))
            titreField.setStyle(Constants.ERROR_STYLE);

        String _auteur = auteurField.getText();
        if ("".equals(_auteur))
            auteurField.setStyle(Constants.ERROR_STYLE);

        URL _sourceURL = getUrlFromField(sourceField);
        URL _secondSourceURL = getUrlFromField(secondSourceField);

        Boolean _isElectronic = firstChoice.isSelected();

        if (isInputCorrect) {
            PresseNews _presseToBeAdded = new PresseNews(_titre, _auteur, _sourceURL, _secondSourceURL, _isElectronic);

            switch (NewsService.instance().getCurrentMode()) {
                case Constants.ADDING_ARTICLE_MODE:   NewsService.instance().addNews(_presseToBeAdded);    break;
                case Constants.EDITING_ARTICLE_MODE:  NewsService.instance().editNews(_presseToBeAdded);   break;
                default:                              closeWindow();                                       break;
            }
            closeWindow();
        }
    }

    private URL getUrlFromField(TextField source) {
        try {
            return new URL(source.getText());
        } catch (MalformedURLException exp) {
            isInputCorrect = false;
            source.setStyle(Constants.ERROR_STYLE);
            return null;
        }
    }

    private void closeWindow() {
        ((Stage)(addButton.getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sourceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if ("".equals(sourceField.getText()))
                sourceField.setText(Constants.HTTP_TAG);
        });

        sourceField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if ("".equals(sourceField.getText()))
                sourceField.setText(Constants.HTTP_TAG);
            sourceField.setStyle(null);
        });

        secondSourceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if ("".equals(secondSourceField.getText()))
                secondSourceField.setText(Constants.HTTP_TAG);
        });

        secondSourceField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if ("".equals(secondSourceField.getText()))
                secondSourceField.setText(Constants.HTTP_TAG);
            secondSourceField.setStyle(null);
        });

        titreField.focusedProperty().addListener((observable, oldValue, newValue) -> titreField.setStyle(null));
        auteurField.focusedProperty().addListener((observable, oldValue, newValue) -> auteurField.setStyle(null));

        switch (NewsService.instance().getCurrentMode()) {
            case Constants.ADDING_ARTICLE_MODE  :     initAddingMode();    break;
            case Constants.EDITING_ARTICLE_MODE :     initEditingMode();   break;
            default :                                 closeWindow();       break;
        }
    }

    private void initAddingMode() {
        titleLabel.setText(Constants.ADDING_ARTICLE_TITLE);
    }

    private void initEditingMode() {
        titleLabel.setText(Constants.EDITING_ARTICLE_TITLE);
        PresseNews _newsArticle = (PresseNews) NewsService.instance().getNewsToBeEdit();
        titreField.setText(_newsArticle.getTitre());
        auteurField.setText(_newsArticle.getAuteur());
        sourceField.setText(_newsArticle.getSource().toString());
        secondSourceField.setText(_newsArticle.getUrlLongue().toString());
        if (_newsArticle.isELectronic()) {
            firstChoice.setSelected(true);
        }
        else {
            secondChoice.setSelected(true);
        }
    }

}
