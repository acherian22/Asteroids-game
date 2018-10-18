package _08final.mvc.model;

import java.awt.*;

/**
 * The type Line.
 */
public class Line {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Color color;

    /**
     * Instantiates a new Line.
     *
     * @param a the a
     * @param b the b
     * @param c the c
     * @param d the d
     */
    public Line(int a, int b, int c, int d){
        x1=a;
        y1=b;
        x2=c;
        y2=d;
    }

    /**
     * Instantiates a new Line.
     *
     * @param a   the a
     * @param b   the b
     * @param c   the c
     * @param d   the d
     * @param col the col
     */
    public Line(int a, int b, int c, int d, Color col){
        x1=a;
        y1=b;
        x2=c;
        y2=d;
        color =col;
    }


    /**
     * X 1 int.
     *
     * @return the int
     */
    public int X1() {
        return x1;
    }

    /**
     * Y 1 int.
     *
     * @return the int
     */
    public int Y1() {
        return y1;
    }

    /**
     * X 2 int.
     *
     * @return the int
     */
    public int X2() {
        return x2;
    }

    /**
     * Y 2 int.
     *
     * @return the int
     */
    public int Y2() {
        return y2;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }
}
