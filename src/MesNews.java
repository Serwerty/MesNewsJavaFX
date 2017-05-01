import DAL.Models.NewsPhoto;
import DAL.Models.NewsPresse;
import Utility.Resolution;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.*;
import java.util.Scanner;
import java.net.URL;

import static java.lang.System.in;

/**
 * Created by Oleg Dovzhenko on 24.04.2017.
 */
public class MesNews extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("UI/MainWindow/mainWindow.fxml"));
        primaryStage.setTitle("MesNews");
        primaryStage.setScene(new Scene(root, 720, 400));
        primaryStage.show();
    }
}
