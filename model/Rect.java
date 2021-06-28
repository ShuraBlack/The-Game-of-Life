package model;

import java.awt.*;

public class Rect {

    public int state;
    public Color color;

    public Rect(int state) {
        this.state = state;
        this.color = new Color(255,255,255);
    }

    public Color getColor() {
        if (state == 1) {
            return color;
        } else {
            return new Color(0,0,0);
        }
    }

    public Color lifeTime () {
        if (color.getGreen() > 50 && color.getBlue() > 50) {
            return new Color(color.getRed(), color.getGreen()-50, color.getBlue()-50);
        }
        return null;
    }
}
