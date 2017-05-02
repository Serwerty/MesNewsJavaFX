package dal.models;

import java.io.PrintWriter;
import java.net.URL;

/**
 * Created by Oleg Dovzhenko on 26.04.2017.
 */
public class PresseNews extends News{

    private URL urlLongue;
    private boolean isELectronic;


    public PresseNews(String titre, String auteur, URL source, URL urlLongue, boolean isELectronic) {
        super(titre, auteur, source);
        this.urlLongue = urlLongue;
        this.isELectronic = isELectronic;
    }

    public String toString() {
        return super.toString() + String.format(", URL longue: %s, Article: %s", urlLongue.toString(),
                                                isELectronic? "electronic" : "papier");
    }

    public void afficher(PrintWriter pw) {
        pw.println(this.toString());
    }

    public URL getUrlLongue() {
        return urlLongue;
    }

    public void setUrlLongue(URL urlLongue) {
        this.urlLongue = urlLongue;
    }

    public boolean isELectronic() {
        return isELectronic;
    }

    public void setELectronic(boolean ELectronic) {
        isELectronic = ELectronic;
    }
}
