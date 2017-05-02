package window.addphotowindow;

import com.sun.org.apache.bcel.internal.classfile.Constant;
import com.sun.org.apache.xpath.internal.operations.Bool;
import constants.Constants;
import dal.models.PhotoNews;
import service.NewsService;
import utility.Resolution;
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

    private Boolean isInputCorrect;

    @FXML
    private void addButtonAction() {
        isInputCorrect = true;
        String _titre = titreField.getText();
        if ("".equals(_titre))
            titreField.setStyle(Constants.ERROR_STYLE);

        String _auteur = auteurField.getText();
        if ("".equals(_auteur))
            auteurField.setStyle(Constants.ERROR_STYLE);

        URL _sourceURL = getUrlFromField(sourceField);
        URL _secondSourceURL = getUrlFromField(secondSourceField);

        String _photoType = dropDownList.getValue();

        int _resX = getIntFromField(resolutionFieldWidth);
        int _resY = getIntFromField(resolutionFieldHeight);
        Boolean _isBlanc = firstChoice.isSelected();

        if (isInputCorrect) {
            PhotoNews _photoToBeAdded = new PhotoNews(_titre, _auteur, _sourceURL, _secondSourceURL, _photoType,
                                                      new Resolution(_resX, _resY), _isBlanc);

            switch (NewsService.instance().getCurrentMode()) {
                case Constants.ADDING_PHOTO_MODE:   NewsService.instance().addNews(_photoToBeAdded);    break;
                case Constants.EDITING_PHOTO_MODE:  NewsService.instance().editNews(_photoToBeAdded);   break;
                default:                            closeWindow();                                      break;
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

    private int getIntFromField(TextField source) {
        String _resString = source.getText();
        if ("".equals(_resString)) {
            source.setStyle(Constants.ERROR_STYLE);
            isInputCorrect = false;
        }
        else
            return Integer.parseInt(_resString);
        return 0;
    }

    private void closeWindow() {
        ((Stage)(addButton.getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dropDownList.getItems().addAll(Constants.PHOTO_TYPES_LIST);
        dropDownList.getSelectionModel().selectFirst();

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
        resolutionFieldHeight.focusedProperty().addListener((observable, oldValue, newValue) -> resolutionFieldHeight.setStyle(null));
        resolutionFieldWidth.focusedProperty().addListener((observable, oldValue, newValue) -> resolutionFieldWidth.setStyle(null));

        resolutionFieldWidth.setTooltip(new Tooltip("Width"));
        resolutionFieldHeight.setTooltip(new Tooltip("Height"));

        UnaryOperator<TextFormatter.Change> filterNumbers = x -> x.getControlNewText().matches("[0-9]{0,4}")? x : null;

        resolutionFieldWidth.setTextFormatter(new TextFormatter<String>(filterNumbers));
        resolutionFieldHeight.setTextFormatter(new TextFormatter<String>(filterNumbers));


        switch (NewsService.instance().getCurrentMode()) {
            case Constants.ADDING_PHOTO_MODE  :     initAddingMode();    break;
            case Constants.EDITING_PHOTO_MODE :     initEditingMode();   break;
            default :                               closeWindow();       break;
        }
    }

    private void initAddingMode() {
        titleLabel.setText(Constants.ADDING_PHOTO_TITLE);
    }

    private void initEditingMode() {
        titleLabel.setText(Constants.EDITING_PHOTO_TITLE);
        PhotoNews _newsPhoto = (PhotoNews) NewsService.instance().getNewsToBeEdit();
        titreField.setText(_newsPhoto.getTitre());
        auteurField.setText(_newsPhoto.getAuteur());
        sourceField.setText(_newsPhoto.getSource().toString());
        secondSourceField.setText(_newsPhoto.getPhotoURL().toString());
        resolutionFieldWidth.setText((Integer.toString(_newsPhoto.getResolution().getWidth())));
        resolutionFieldHeight.setText(Integer.toString(_newsPhoto.getResolution().getHeight()));
        dropDownList.getSelectionModel().select(_newsPhoto.getImageType());
        if (_newsPhoto.isBlanc()) {
            firstChoice.setSelected(true);
        }
        else {
            secondChoice.setSelected(true);
        }
    }
}
