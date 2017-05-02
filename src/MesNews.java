import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Oleg Dovzhenko on 24.04.2017.
 */
public class MesNews extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("window/mainwindow/MainWindow.fxml"));
        primaryStage.setTitle("MesNews");
        primaryStage.setScene(new Scene(root, 720, 400));
        primaryStage.show();
    }
}
