package _08final.mvc.model;

import _08final.mvc.controller.Game;

import java.awt.*;
import java.util.Random;
import java.util.logging.Level;


/**
 * The type Titanium asteroid.
 */
public class TitaniumAsteroid extends Asteroid {
    private Random mRandom = new Random();
    private int RAD = 25;
    private boolean bProtected = true;
    private int protectNum = 260;
    private int level = 2;
    private int strtLevel = 2;
    private Color col;


    /**
     * Instantiates a new Titanium asteroid.
     *
     * @param astExploded the ast exploded
     */
    public TitaniumAsteroid(TitaniumAsteroid astExploded) {
        super(astExploded);
        bProtected = true;
        protectNum = 0;
        setTeam(Team.FOE);
        int nSizeNew = astExploded.getSize() + 1;

        int nSpin = Game.R.nextInt(10);
        if (nSpin % 2 == 0)
            nSpin = -nSpin;
        setSpin(nSpin);

        int nDX = Game.R.nextInt(10 + nSizeNew * 2);
        if (nDX % 2 == 0)
            nDX = -nDX;
        setDeltaX(nDX);

        int nDY = Game.R.nextInt(10 + nSizeNew * 2);
        if (nDY % 2 == 0)
            nDY = -nDY;
        setDeltaY(nDY);

        assignRandomShape();
        setRadius(RAD * nSizeNew * 2);
        setCenter(astExploded.getCenter());
        col = new Color(mRandom.nextInt(256),
                mRandom.nextInt(256),
                mRandom.nextInt(256));
        level = level * (getSize() + 1);
        strtLevel = level;
        setColor(col);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (protectNum < 260 & bProtected) {
            setColor(new Color(protectNum));
            protectNum += 5;
        } else {
            setColor(col);
            bProtected = false;
            protectNum = 0;
        }
        fillDraw(g);
        drawDefense(g);
    }

    /**
     * Instantiates a new Titanium asteroid.
     *
     * @param nSize the n size
     */
    public TitaniumAsteroid(int nSize) {
        super(nSize);
        setDeltaX(getDeltaX() * .5);
        setDeltaY(getDeltaY() * .5);
        col = new Color(mRandom.nextInt(256),
                mRandom.nextInt(256),
                mRandom.nextInt(256));
        setColor(col);
    }

    /**
     * Draw defense.
     *
     * @param g the g
     */
    public void drawDefense(Graphics g) {
        g.setColor(Color.white);
        int radius = getRadius();
        int x = (int) getCenter().getX() - radius;
        int y = (int) getCenter().getY() - radius;
        if (bProtected) {
            g.setColor(new Color(protectNum));
            String stgDisplay = "RESPAWNING";
            g.drawString(stgDisplay, x, y);
        } else {
            g.drawRect(x - 8, y - 8, radius * 2, 10);
            g.setColor(Color.white);
            int scale = (int) ((level * 1.0) / (strtLevel * 1.0) * radius * 2);
            String stgDisplay = "Defense: " + level;
            g.drawString(stgDisplay, x - 6, y - 10);
            g.setColor(Color.gray);
            g.fillRect(x - 7, y - 7, scale, 8);
        }
    }

    public int getSize() {

        int nReturn = 0;

        switch (getRadius()) {
            case 25:
                nReturn = 0;
                break;
            case 50:
                nReturn = 1;
                break;
            case 100:
                nReturn = 2;
                break;
            case 150:
                nReturn = 3;
        }
        return nReturn;

    }

    /**
     * Isb protected boolean.
     *
     * @return the boolean
     */
    public boolean isbProtected() {
        return bProtected;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        switch (getSize()) {
            case 0:
                return 50;
            case 1:
                return 100;
            case 2:
                return 250;
            case 3:
                return 500;

            default:
                return 0;
        }
    }

}
