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

    private static boolean isRunning;
    private static String menuChoise ;
    private static Scanner scanner;
    private static PrintWriter printWriter;
    private static BaseDeNews newsCollection;

    public static void main(String[] args)
    {
        Init();

        while(isRunning)
        {
            afficherMenu();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("UI/MainWindow/mainWindow.fxml"));
        primaryStage.setTitle("Article explorer");
        primaryStage.setScene(new Scene(root, 720, 400));
        primaryStage.show();

    }



    private static void Init()
    {
     launch();
     isRunning = true;
     scanner = new Scanner(in);
     printWriter = new PrintWriter(System.out, true);
     newsCollection = new BaseDeNews();
    }

    private static void afficherMenu()
    {

        printWriter.println("You can choose one of this 8 options:");
        printWriter.println("1. Creer ");
        printWriter.println("2. Ouvrir ");
        printWriter.println("3. Sauvegarder ");
        printWriter.println("4. Ins´erer ");
        printWriter.println("5. Supprimer ");
        printWriter.println("6. Afficher ");
        printWriter.println("7. Rechercher ");
        printWriter.println("8. Quiter ");
        menuChoise = scanner.nextLine();

        switch (menuChoise)
        {
            case "1":   creer();        break;
            case "2":   ouvrir();       break;
            case "3":   sauvegarder();  break;
            case "4":   inserer();      break;
            case "5":   supprimer();    break;
            case "6":   afficher();     break;
            case "7":   rechercher();   break;
            case "8":   quitter();      break;
            default:    wrongInput();

        }
    }

    private static void wrongInput()
    {
        printWriter.println("Wrong Input try again!");
    }

    private static void creer()
    {
        printWriter.println("creer new BaseDeNews!");
        newsCollection = new BaseDeNews();
    }

    private static void ouvrir()
    {
        printWriter.println("ouvrir!");
        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("base.dat"));
            newsCollection = (BaseDeNews)objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (FileNotFoundException exp)
        {
            printWriter.println("There is no saved file");
        }
        catch (IOException exp)
        {
            printWriter.println("Crashed with: " + exp.getMessage());
        }
        catch (ClassNotFoundException exp)
        {
            printWriter.println("Crashed with: " + exp.getMessage());
        }
        finally
        {
            printWriter.println("success!");
        }
    }

    private static void sauvegarder()
    {
        printWriter.println("sauvegarder");
        try
        {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("base.dat"));
            objectOutputStream.writeObject(newsCollection);
            objectOutputStream.close();
        }
        catch (FileNotFoundException exp)
        {
            printWriter.println("Crashed with: " + exp.getMessage());
        }
        catch (IOException exp)
        {
            printWriter.println("Crashed with: " + exp.getMessage());
        }
        finally
        {
            printWriter.println("success!");
        }
    }

    private static void inserer()
    {
        printWriter.println("Select type of news you want to add:");
        printWriter.println("1 : Presse");
        printWriter.println("2 : Photo");
        boolean _isSelected= false;
        String _newsTypeChoice;

        while (!_isSelected)
        {
            _newsTypeChoice = scanner.nextLine();
            if (_newsTypeChoice.equals("1"))
            {
                _isSelected = true;
                insererPresse();
            }
            else if (_newsTypeChoice.equals("2"))
            {
                _isSelected = true;
                insererPhoto();
            }
            else printWriter.println("Wrong input, please try again!");
        }
    }

    private static void insererPresse()
    {
        printWriter.println("Insert news.");
        printWriter.println("Insert titre:");
        String _titre = scanner.nextLine();

        //printWriter.println("Insert date:");
        // String _date = scanner.nextLine();

        printWriter.println("Insert auteur:");
        String _autuer = scanner.nextLine();


        URL _sourceURL = null;

        while(_sourceURL == null) {
            printWriter.println("Insert source:");
            String _source = scanner.nextLine();
            try
            {
                _sourceURL = new URL(_source);
            }
            catch (Exception exp)
            {
                printWriter.println("Incorrect URL try again!");
            }
        }

        URL _sourceURLLongue = null;

        while(_sourceURLLongue == null) {
            printWriter.println("Insert long URL: ");
            String _source = scanner.nextLine();
            try
            {
                _sourceURLLongue = new URL(_source);
            }
            catch (Exception exp)
            {
                printWriter.println("Incorrect URL try again!");
            }
        }

        Boolean _isElectronic;
        printWriter.println("Insert true/false or 1/0 if this article is electronic(default: paper): ");
        String _inputBool = scanner.nextLine();
        if (_inputBool.equals("1") || _inputBool.equals("true"))
            _isElectronic = true;
        else
            _isElectronic = false;
        NewsPresse _newsPresse = new NewsPresse(_titre, _autuer, _sourceURL, _sourceURLLongue, _isElectronic);
        newsCollection.add(_newsPresse);
    }

    private static void insererPhoto()
    {
        printWriter.println("Insert news.");
        printWriter.println("Insert titre:");
        String _titre = scanner.nextLine();

        //printWriter.println("Insert date:");
        // String _date = scanner.nextLine();

        printWriter.println("Insert auteur:");
        String _autuer = scanner.nextLine();


        URL _sourceURL = null;

        while(_sourceURL == null) {
            printWriter.println("Insert source:");
            String _source = scanner.nextLine();
            try
            {
                _sourceURL = new URL(_source);
            }
            catch (Exception exp)
            {
                printWriter.println("Incorrect URL try again!");
            }
        }


        URL _sourceURLImage = null;

        while(_sourceURLImage == null) {
            printWriter.println("Insert photo URL: ");
            String _source = scanner.nextLine();
            try
            {
                _sourceURLImage = new URL(_source);
            }
            catch (Exception exp)
            {
                printWriter.println("Incorrect URL try again!");
            }
        }

        printWriter.println("Insert type of the photo(JPG, GIF, JPEG),(default: JPG):");
        String _imageTypeInsert = scanner.nextLine();
        if (!_imageTypeInsert.equals("GIF") && !_imageTypeInsert.equals("JPEG") && !_imageTypeInsert.equals("JPG"))
            _imageTypeInsert = "JPG";

        printWriter.println("Insert resolution of the photo(two numbers, width then height):");
        int _imageWidth = scanner.nextInt();
        int _imageHeight = scanner.nextInt();
        scanner.nextLine();

        if (_imageHeight < 0 || _imageHeight > 10000)
            _imageHeight = 0;
        if (_imageWidth < 0 || _imageWidth > 10000)
            _imageWidth = 0;
        Resolution _resolution = new Resolution(_imageHeight, _imageWidth );

        Boolean _isBlanc;

        printWriter.println("Insert true/false or 1/0 if this photo is blanc(default: blanc): ");
        String _inputBool = scanner.nextLine();
        if (_inputBool.equals("1") || _inputBool.equals("true"))
            _isBlanc = true;
        else
            _isBlanc = false;

        NewsPhoto _newsPhoto = new NewsPhoto(_titre, _autuer, _sourceURL, _sourceURLImage,
                                             _imageTypeInsert, _resolution, _isBlanc);
        newsCollection.add(_newsPhoto);
    }

    private static void supprimer()
    {
        printWriter.println("supprimer!");
        afficher();

        printWriter.println("Choose witch one you wanna delete:");
        int _choise = scanner.nextInt();

        if (newsCollection.delete(_choise))
            printWriter.println(_choise + " news was deleted");
        else
            printWriter.println(_choise + "something went wrong!");
    }

    private static void afficher()
    {
        printWriter.println("afficher!");
        newsCollection.afficher(printWriter);
    }

    private static void rechercher()
    {
        printWriter.println("rechercher!");
        printWriter.println("Insert keyword to search:");

        String _keyWord = scanner.nextLine();
        newsCollection.printNewsWithPredicate(x-> x.getTitre().contains(_keyWord), printWriter);
    }

    private static void quitter()
    {
        printWriter.println("exiting!");
        isRunning = false;
        scanner.close();
        printWriter.close();

    }


}
