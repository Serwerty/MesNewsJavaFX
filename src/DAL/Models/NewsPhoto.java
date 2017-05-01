package DAL.Models;

import Utility.Resolution;

import java.io.PrintWriter;
import java.net.URL;

/**
 * Created by Oleg Dovzhenko on 26.04.2017.
 */
public class NewsPhoto extends News{


    private URL photoURL;
    private String imageType;
    private Resolution resolution;

    private boolean isBlanc;

    public NewsPhoto(String titre, String auteur, URL source,URL photoURL, String imageType, Resolution resolution,
                     boolean isBlanc) {
        super(titre, auteur, source);
        this.photoURL = photoURL;
        this.imageType = imageType;
        this.resolution = resolution;
        this.isBlanc = isBlanc;
    }

    public String toString() {

        return super.toString() + String.format(", Photo URL: %s, Image type: %s, Resolution: %spx : %spx, Image: %s",
                photoURL, imageType, resolution.getWidth(), resolution.getHeight(),
                                                isBlanc ? "blanc" : "noir");
    }

    public void afficher(PrintWriter pw) {
        pw.println(this.toString());
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public boolean isBlanc() {
        return isBlanc;
    }

    public void setBlanc(boolean isBlanc) {
        isBlanc = isBlanc;
    }

    public URL getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(URL photoURL) {
        this.photoURL = photoURL;
    }
}
