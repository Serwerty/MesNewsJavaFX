package UI.AddArticleWindow;

import Constants.mainConstants;
import DAL.Models.NewsPresse;
import Logic.LogicController;
import javafx.application.Platform;
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

    @FXML
    public void addButtonAction() {
        Boolean _isInputCorrect = true;
        String _titre = titreField.getText();
        String _auteur = auteurField.getText();
        String _source = sourceField.getText();
        URL _sourceURL = null;
        try {
            _sourceURL = new URL(_source);
        }
        catch (MalformedURLException exp){
            _isInputCorrect = false;
            sourceField.setStyle(mainConstants.ERROR_STYLE);
        }

        String _secondSource = secondSourceField.getText();
        URL _secondSourceURL = null;
        try {
            _secondSourceURL = new URL(_secondSource);
        }
        catch (MalformedURLException exp){
            _isInputCorrect = false;
            secondSourceField.setStyle(mainConstants.ERROR_STYLE);
        }

        Boolean _isElectronic = firstChoice.isSelected();

        if (_isInputCorrect) {
            NewsPresse _presseToBeAdded = new NewsPresse(_titre, _auteur, _sourceURL, _secondSourceURL, _isElectronic);

            switch (LogicController.instance().getCurrentMode()) {
                case mainConstants.ADDING_ARTICLE_MODE:   LogicController.instance().addNews(_presseToBeAdded);    break;
                case mainConstants.EDITING_ARTICLE_MODE:  LogicController.instance().editNews(_presseToBeAdded);   break;
                default:                                  closeWindow();                                           break;
            }
            closeWindow();
        }
    }

    private void closeWindow() {
        ((Stage)(addButton.getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        switch (LogicController.instance().getCurrentMode()) {
            case mainConstants.ADDING_ARTICLE_MODE  :     initAddingMode();    break;
            case mainConstants.EDITING_ARTICLE_MODE :     initEditingMode();   break;
            default :                                     closeWindow();       break;
        }
    }

    private void initAddingMode() {
        titleLabel.setText(mainConstants.ADDING_ARTICLE_TITLE);
    }

    private void initEditingMode() {
        titleLabel.setText(mainConstants.EDITING_ARTICLE_TITLE);
        NewsPresse _newsArticle = (NewsPresse)LogicController.instance().getNewsToBeEdit();
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
