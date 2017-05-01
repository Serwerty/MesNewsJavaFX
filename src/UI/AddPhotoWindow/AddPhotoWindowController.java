package UI.AddPhotoWindow;

import Constants.mainConstants;
import DAL.DataProvider;
import DAL.Models.NewsPhoto;
import Logic.LogicController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Oleg Dovzhenko on 30.04.2017.
 */
public class AddPhotoWindowController implements Initializable {

    @FXML
    public TextField titreField;
    @FXML
    public TextField auteurField;
    @FXML
    public TextField sourceField;
    @FXML
    public TextField secondSourceField;
    @FXML
    public TextField resolutionFieldWidth;
    @FXML
    public TextField resolutionFieldHeight;
    @FXML
    public ChoiceBox<String> dropDownList;
    @FXML
    public RadioButton firstChoice;
    @FXML
    public RadioButton secondChoice;
    @FXML
    public Label titleLabel;

    @FXML
    public void addButton()
    {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        switch (LogicController.instance().getCurrentMode())
        {
            case mainConstants.ADDING_PHOTO_MODE  :     initAddingMode();    break;
            case mainConstants.EDITING_PHOTO_MODE :     initEditingMode();   break;
            default :                                   Platform.exit();     break;
        }
    }

    private void initAddingMode()
    {
        titleLabel.setText(mainConstants.ADDING_PHOTO_TITLE);
    }

    private void initEditingMode()
    {
        titleLabel.setText(mainConstants.EDITING_ARTICLE_MODE);
        NewsPhoto _newsPhoto = (NewsPhoto)LogicController.instance().getNewsToBeEdit();
        titreField.setText(_newsPhoto.getTitre());
        auteurField.setText(_newsPhoto.getAuteur());
        sourceField.setText(_newsPhoto.getSource().toString());
        secondSourceField.setText(_newsPhoto.getPhotoURL().toString());
        resolutionFieldWidth.setText((new Integer(_newsPhoto.getResolution().getWidth()).toString()));
        resolutionFieldHeight.setText(new Integer(_newsPhoto.getResolution().getHeight()).toString());
        if (_newsPhoto.isBlanc()) {
            firstChoice.fire();
        }
        else {
            secondChoice.fire();
        }

    }
}
