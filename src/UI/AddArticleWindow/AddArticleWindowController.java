package UI.AddArticleWindow;

import Constants.mainConstants;
import Logic.LogicController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Oleg Dovzhenko on 30.04.2017.
 */
public class AddArticleWindowController implements Initializable {

    @FXML
    public TextField titreField;
    @FXML
    public TextField auteurField;
    @FXML
    public TextField sourceField;
    @FXML
    public TextField secondSourceField;
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
            case mainConstants.ADDING_ARTICLE_MODE  :     initAddingMode();    break;
            case mainConstants.EDITING_ARTICLE_MODE :     initEditingMode();   break;
            default :                                     Platform.exit();     break;
        }
    }

    private void initAddingMode()
    {
        titleLabel.setText(mainConstants.ADDING_ARTICLE_TITLE);
    }

    private void initEditingMode()
    {
        titleLabel.setText(mainConstants.EDITING_ARTICLE_TITLE);
    }

}
