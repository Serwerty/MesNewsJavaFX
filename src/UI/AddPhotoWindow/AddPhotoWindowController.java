package UI.AddPhotoWindow;

import Constants.mainConstants;
import DAL.Models.NewsPhoto;
import Logic.LogicController;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private void addButton() {
        Boolean _isInputCorrect = true;
        String _titre = titreField.getText();
        String _auteur = auteurField.getText();
        String _source = sourceField.getText();
        try {
            URL _sourceURL = new URL(_source);
        }
        catch (MalformedURLException exp){
            _isInputCorrect = false;
            sourceField.getStyleClass().add("error");
        }

        String _secondSource = secondSourceField.getText();
        String _photoType = dropDownList.getValue();
        int _resX = Integer.parseInt(resolutionFieldWidth.getText());
        int _resY = Integer.parseInt(resolutionFieldHeight.getText());
        Boolean _isBlanc = firstChoice.isSelected();

        if (_isInputCorrect) {
           // NewsPhoto _photoToBeAdded = new

            switch (LogicController.instance().getCurrentMode()) {
                case mainConstants.ADDING_PHOTO_MODE:
                    break;
                case mainConstants.EDITING_PHOTO_MODE:
                    initEditingMode();
                    break;
                default:
                    Platform.exit();
                    break;
            }
        }
    }

    private void  addPhoto(NewsPhoto _photoToBeAdded)
    {
        LogicController.instance().addNews(_photoToBeAdded);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dropDownList.getItems().addAll(mainConstants.PHOTO_TYPES_LIST);
        dropDownList.getSelectionModel().selectFirst();

        resolutionFieldWidth.setTooltip(new Tooltip("Width"));
        resolutionFieldHeight.setTooltip(new Tooltip("Height"));

        UnaryOperator<TextFormatter.Change> filterNumbers = x -> x.getControlNewText().matches("[0-9]{0,5}")? x : null;

        resolutionFieldWidth.setTextFormatter(new TextFormatter<String>(filterNumbers));
        resolutionFieldHeight.setTextFormatter(new TextFormatter<String>(filterNumbers));


        switch (LogicController.instance().getCurrentMode()) {
            case mainConstants.ADDING_PHOTO_MODE  :     initAddingMode();    break;
            case mainConstants.EDITING_PHOTO_MODE :     initEditingMode();   break;
            default :                                   Platform.exit();     break;
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
