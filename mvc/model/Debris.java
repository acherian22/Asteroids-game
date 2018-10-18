package _08final.mvc.model;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * The type Debris.
 */
public class Debris implements Movable {
    private int mExpiry;
    private Point mPoint;
    private Random mRandom;
    private Point[] foeCoords;
    private Line[] foeLines;
    private Point[] deltas;
    private boolean foeActive=new Boolean(false);


    /**
     * Instantiates a new Debris.
     *
     * @param mExpiry the m expiry
     * @param mPoint  the m point
     */
    public Debris(int mExpiry, Point mPoint) {
        this.mExpiry = mExpiry;
        this.mPoint = mPoint;
        mRandom = new Random();
    }

    /**
     * Instantiates a new Debris.
     *
     * @param mExpiry the m expiry
     * @param mPoint  the m point
     * @param foe     the foe
     */
    public Debris(int mExpiry, Point mPoint, Sprite foe) {
        this.mExpiry = mExpiry;
        this.mPoint = mPoint;
        mRandom = new Random();
        foeCoords=foe.getPntCoords();
        foeLines = new Line[foeCoords.length-1];
        deltas=new Point[foeCoords.length];
        for (int n=0; n<foeCoords.length-1;n++) {
            int x =mRandom.nextInt(10);
            int y =mRandom.nextInt(10);
            deltas[n]=new Point(x,y);
            foeLines[n]= new Line(foeCoords[n].x,foeCoords[n].y,foeCoords[n+1].x,foeCoords[n+1].y, new Color(mRandom.nextInt(256),
                    mRandom.nextInt(256),
                    mRandom.nextInt(256)));
            foeActive=true;
        }
    }

    @Override
    public void move() {
        if(!foeActive){
            if (mExpiry ==0){
                CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
            }
            else {
                mExpiry--;
            }
        }
        else{
            moveAlt();
        }
    }

    /**
     * Move alt.
     */
    public void moveAlt(){
        if (mExpiry ==0){
            CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
        }
        else {
            for (int n=0; n<foeLines.length;n++){
                Line foeLine=foeLines[n];
                foeLines[n]=new Line(foeLine.X1()+deltas[n].x,foeLine.Y1()+deltas[n].y,foeLine.X2()
                        +deltas[n].x,foeLine.Y2()+deltas[n].y, foeLine.getColor());
            }
            mExpiry--;
        }
    }

    @Override
    public void draw(Graphics g) {
        if(!foeActive){
            g.setColor(new Color(mRandom.nextInt(256),
                    mRandom.nextInt(256),
                    mRandom.nextInt(256)));
            g.fillOval(mPoint.x, mPoint.y, mExpiry, mExpiry);
        }
        else{
            drawAlt(g);
        }
    }

    /**
     * Draw alt.
     *
     * @param g the g
     */
    public void drawAlt(Graphics g){
        g.setColor(Color.white);
        for (Line foeLine : foeLines) {
            g.setColor(foeLine.getColor());
            g.drawLine(foeLine.X1(),foeLine.Y1(),foeLine.X2(),foeLine.Y2());
        }
    }

    @Override
    public Team getTeam() {
        return Team.DEBRIS;
    }

    /**
     * Points int.
     *
     * @return the int
     */
    public int points() {
        return 0;
    }
    //collision detection


    @Override
    public Point getCenter() {
        return null;
    }

    @Override
    public int getRadius() {
        return 0;
    }

    /**
     * Expire.
     */
    public void expire() {

    }

    /**
     * Fade in out.
     */
    public void fadeInOut() {

    }

}
