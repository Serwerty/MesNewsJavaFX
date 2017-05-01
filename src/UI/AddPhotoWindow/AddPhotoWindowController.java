package UI.AddPhotoWindow;

import Constants.mainConstants;
import DAL.Models.NewsPhoto;
import Logic.LogicController;
import Utility.Resolution;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * Created by Oleg Dovzhenko on 30.04.2017.
 */
public class AddPhotoWindowController implements Initializable {

    @FXML
    private TextField titreField;
    @FXML
    private TextField auteurField;
    @FXML
    private TextField sourceField;
    @FXML
    private TextField secondSourceField;
    @FXML
    private TextField resolutionFieldWidth;
    @FXML
    private TextField resolutionFieldHeight;
    @FXML
    public ChoiceBox<String> dropDownList;
    @FXML
    private RadioButton firstChoice;
    @FXML
    private RadioButton secondChoice;
    @FXML
    private Label titleLabel;
    @FXML
    private Button addButton;

    @FXML
    private void addButtonAction() {
        Boolean _isInputCorrect = true;
        String _titre = titreField.getText();
        if ("".equals(_titre))
            titreField.setStyle(mainConstants.ERROR_STYLE);

        String _auteur = auteurField.getText();
        if ("".equals(_auteur))
            auteurField.setStyle(mainConstants.ERROR_STYLE);

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

        String _photoType = dropDownList.getValue();
        String _resXString = resolutionFieldWidth.getText();
        int _resX=0;
        if ("".equals(_resXString))
            resolutionFieldWidth.setStyle(mainConstants.ERROR_STYLE);
        else
            _resX = Integer.parseInt(_resXString);

        String _resYString = resolutionFieldHeight.getText();
        int _resY=0;
        if ("".equals(_resYString))
            resolutionFieldHeight.setStyle(mainConstants.ERROR_STYLE);
        else
            _resY = Integer.parseInt(_resYString);
        Boolean _isBlanc = firstChoice.isSelected();

        if (_isInputCorrect) {
            NewsPhoto _photoToBeAdded = new NewsPhoto(_titre, _auteur, _sourceURL, _secondSourceURL, _photoType,
                                                      new Resolution(_resX, _resY), _isBlanc);

            switch (LogicController.instance().getCurrentMode()) {
                case mainConstants.ADDING_PHOTO_MODE:   LogicController.instance().addNews(_photoToBeAdded);    break;
                case mainConstants.EDITING_PHOTO_MODE:  LogicController.instance().editNews(_photoToBeAdded);   break;
                default:                                closeWindow();                                          break;
            }
            closeWindow();
        }
    }

    private void closeWindow() {
        ((Stage)(addButton.getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dropDownList.getItems().addAll(mainConstants.PHOTO_TYPES_LIST);
        dropDownList.getSelectionModel().selectFirst();

        sourceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if ("".equals(sourceField.getText()))
                sourceField.setText("http://");
        });

        sourceField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if ("".equals(sourceField.getText()))
                sourceField.setText("http://");
            sourceField.setStyle(null);
        });

        secondSourceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if ("".equals(secondSourceField.getText()))
                secondSourceField.setText("http://");
        });

        secondSourceField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if ("".equals(secondSourceField.getText()))
                secondSourceField.setText("http://");
            secondSourceField.setStyle(null);
        });

        titreField.focusedProperty().addListener((observable, oldValue, newValue) -> titreField.setStyle(null));
        auteurField.focusedProperty().addListener((observable, oldValue, newValue) -> auteurField.setStyle(null));
        resolutionFieldHeight.focusedProperty().addListener((observable, oldValue, newValue) -> resolutionFieldHeight.setStyle(null));
        resolutionFieldWidth.focusedProperty().addListener((observable, oldValue, newValue) -> resolutionFieldWidth.setStyle(null));

        resolutionFieldWidth.setTooltip(new Tooltip("Width"));
        resolutionFieldHeight.setTooltip(new Tooltip("Height"));

        UnaryOperator<TextFormatter.Change> filterNumbers = x -> x.getControlNewText().matches("[0-9]{0,5}")? x : null;

        resolutionFieldWidth.setTextFormatter(new TextFormatter<String>(filterNumbers));
        resolutionFieldHeight.setTextFormatter(new TextFormatter<String>(filterNumbers));


        switch (LogicController.instance().getCurrentMode()) {
            case mainConstants.ADDING_PHOTO_MODE  :     initAddingMode();    break;
            case mainConstants.EDITING_PHOTO_MODE :     initEditingMode();   break;
            default :                                   closeWindow();       break;
        }
    }

    private void initAddingMode() {
        titleLabel.setText(mainConstants.ADDING_PHOTO_TITLE);
    }

    private void initEditingMode() {
        titleLabel.setText(mainConstants.EDITING_PHOTO_TITLE);
        NewsPhoto _newsPhoto = (NewsPhoto)LogicController.instance().getNewsToBeEdit();
        titreField.setText(_newsPhoto.getTitre());
        auteurField.setText(_newsPhoto.getAuteur());
        sourceField.setText(_newsPhoto.getSource().toString());
        secondSourceField.setText(_newsPhoto.getPhotoURL().toString());
        resolutionFieldWidth.setText((new Integer(_newsPhoto.getResolution().getWidth()).toString()));
        resolutionFieldHeight.setText(new Integer(_newsPhoto.getResolution().getHeight()).toString());
        dropDownList.getSelectionModel().select(_newsPhoto.getImageType());
        if (_newsPhoto.isBlanc()) {
            firstChoice.setSelected(true);
        }
        else {
            secondChoice.setSelected(true);
        }
    }
}
