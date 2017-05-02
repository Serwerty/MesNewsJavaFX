package utility;

import java.io.Serializable;

/**
 * Created by Oleg Dovzhenko on 26.04.2017.
 */
public class Resolution implements Serializable{
    private int height;
    private int width;

    public Resolution(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
