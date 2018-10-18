package _08final.mvc.model;

import java.awt.*;
import java.util.ArrayList;


/**
 * The type Shard.
 */
public class Shard extends Sprite {

    private final double FIRE_POWER = 35.0;
    private static final int FADE = 15;
    private int fadeRed, fadeGreen, fadeBlue;

    /**
     * Instantiates a new Shard.
     *
     * @param sprite the sprite
     * @param nOR    the n or
     */
    public Shard(Sprite sprite, int nOR) {

        super();
        setTeam(Team.DEBRIS);


        //defined the points on a cartesean grid
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(1, 0));
        pntCs.add(new Point(-1, 0));

        assignPolarPoints(pntCs);

        //a bullet expires after 20 frames
        setExpire(4);
        setRadius(sprite.getRadius()/3);
        setOrientation(nOR);


        //everything is relative to the falcon ship that fired the bullet
        setDeltaX(sprite.getDeltaX() +
                Math.cos(Math.toRadians(getOrientation())) * FIRE_POWER);
        setDeltaY(sprite.getDeltaY() +
                Math.sin(Math.toRadians(getOrientation())) * FIRE_POWER);
        setCenter(sprite.getCenter());

        fadeRed=sprite.getColor().getRed()/FADE;
        fadeGreen=sprite.getColor().getGreen()/FADE;
        fadeBlue=sprite.getColor().getBlue()/FADE;



    }

    @Override
    public void move() {

        super.move();

        if (getExpire() == 0)
            CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
        else {
            setExpire(getExpire() - 1);
            Color currentColor=getColor();
            setColor(new Color(
                    Math.abs(currentColor.getRed()-fadeRed),
                    Math.abs(currentColor.getGreen()-fadeGreen),
                    Math.abs(currentColor.getBlue()-fadeBlue)));

        }


    }

}
