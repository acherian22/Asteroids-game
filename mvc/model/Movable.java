package _08final.mvc.model;

import java.awt.*;

/**
 * The interface Movable.
 */
public interface Movable {

    /**
     * The enum Team.
     */
    public static enum Team {
        /**
         * Friend team.
         */
        FRIEND,
        /**
         * Foe team.
         */
        FOE,
        /**
         * Floater team.
         */
        FLOATER,
        /**
         * Debris team.
         */
        DEBRIS
	}

    /**
     * Move.
     */
//for the game to move and draw movable objects
	public void move();

    /**
     * Draw.
     *
     * @param g the g
     */
    public void draw(Graphics g);

    /**
     * Gets center.
     *
     * @return the center
     */
//for collision detection
	public Point getCenter();

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public int getRadius();

    /**
     * Gets team.
     *
     * @return the team
     */
    public Team getTeam();


} //end Movable
