package DAL.Models;

import java.io.PrintWriter;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.time.LocalDateTime;

/**
 * Created by Oleg Dovzhenko on 25.04.2017.
 */
public abstract class News implements Comparable<News>, Serializable {

    private String titre;
    private LocalDateTime date;
    private String auteur;
    private URL source;
    private static DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public News() {

    }

    public News(String titre, LocalDateTime date, String auteur, URL source) {
        this.titre = titre;
        this.date = date;
        this.auteur = auteur;
        this.source = source;
    }

    public News(String titre, String auteur, URL source) {
        this.titre = titre;
        this.date =  LocalDateTime.now();
        this.auteur = auteur;
        this.source = source;
    }

    public void afficher(PrintWriter pw) {
        pw.println(this.toString());
    }

    public String toString() {
        return String.format("Titre: %s, Date: %s, Auteur: %s, Source: %s", titre, date.format(formatter).toString(), auteur,
                             source.toString());
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date= date;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur= auteur;
    }

    public URL getSource() {
        return source;
    }

    public void setSource(URL source) {
        this.source= source;
    }

    @Override
    public int compareTo(News o) {
        return this.titre.compareTo(o.titre);
    }
}
