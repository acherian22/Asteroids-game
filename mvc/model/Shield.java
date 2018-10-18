package _08final.mvc.model;

import _08final.mvc.controller.Game;
import _08final.sounds.Sound;

import java.awt.*;


/**
 * The type Shield.
 */
public class Shield extends Sprite {
    /**
     * The Shieldsize.
     */
    int shieldsize = CommandCenter.getInstance().getFalcon().getRadius() * 2 + 50;
    /**
     * The Level.
     */
    int level = 10;

    /**
     * Instantiates a new Shield.
     */
    public Shield() {
        super();
        setTeam(Team.FRIEND);
        this.setCenter(CommandCenter.getInstance().getFalcon().getCenter());
        setRadius(shieldsize / 2);
    }

    @Override
    public void move() {
        super.move();
        Point falCen = CommandCenter.getInstance().getFalcon().getCenter();
        this.setCenter(falCen);
    }


    @Override
    //Change color as the power of the shield goes down from 10 to 0.
    public void draw(Graphics g) {
        g.setColor(new Color(25 * level));
        g.drawOval(getCenter().x - shieldsize / 2, getCenter().y - shieldsize / 2, shieldsize, shieldsize);
        g.drawOval(getCenter().x - shieldsize / 2 - 3, getCenter().y - shieldsize / 2 - 3, shieldsize + 6, shieldsize + 6);
        g.drawOval(getCenter().x - shieldsize / 2 - 6, getCenter().y - shieldsize / 2 - 6, shieldsize + 12, shieldsize + 12);
        drawShieldBar(g);
    }

    /**
     * Draw shield bar.
     *
     * @param g the g
     */
    public void drawShieldBar(Graphics g) {
        g.setColor(Color.white);
        String stgDisplay = "Shield Power: "+level;
        g.drawString(stgDisplay, Game.DIM.width - 110, Game.DIM.height - 110);
        g.drawRect(Game.DIM.width - 100, Game.DIM.height - 100, 82, 20);
        g.setColor(Color.yellow);
        g.fillRect(Game.DIM.width - 99, Game.DIM.height - 99, 8 * level, 18);
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
     * Decrement level.
     */
    public void decrementLevel() {
        level = level - 1;
    }
}

