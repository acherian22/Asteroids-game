package _08final.mvc.model;

import java.awt.*;

/**
 * The type Target missile.
 */
public class TargetMissile extends Cruise {
    private final int MAX_EXPIRE = 1000;
    private final double FIRE_POWER = 20;
    private boolean bTurningRight = false;
    private boolean bTurningLeft = false;
    /**
     * The Degree step.
     */
    final int DEGREE_STEP = 7;
    private int expiretime=1;


    /**
     * Instantiates a new Target missile.
     *
     * @param fal the fal
     */
    public TargetMissile(Falcon fal) {
        super(fal);
        setExpire(MAX_EXPIRE);
        setRadius(20);

        //everything is relative to the falcon ship that fired the bullet
        setDeltaX(fal.getDeltaX()
                + Math.cos(Math.toRadians(fal.getOrientation())) * FIRE_POWER);
        setDeltaY(fal.getDeltaY()
                + Math.sin(Math.toRadians(fal.getOrientation())) * FIRE_POWER);
        setCenter(fal.getCenter());

        //set the bullet orientation to the falcon (ship) orientation
        setOrientation(fal.getOrientation());
        setColor(Color.ORANGE);

    }

    /**
     * Rotate left.
     */
    public void rotateLeft() {
        bTurningLeft = true;
    }

    @Override
    public void move() {

        super.move();
        if (CommandCenter.getInstance().isControllingMissile()) {
            double dAdjustX = Math.cos(Math.toRadians(getOrientation()))*FIRE_POWER*.5;
            double dAdjustY = Math.sin(Math.toRadians(getOrientation()))*FIRE_POWER*.5;
            setDeltaX(getDeltaX()*.5+dAdjustX);
            setDeltaY(getDeltaY()*.5+dAdjustY);
        }
        if (bTurningLeft) {

            if (getOrientation() <= 0 && bTurningLeft) {
                setOrientation(360);
            }
            setOrientation(getOrientation() - DEGREE_STEP);
        }
        if (bTurningRight) {
            if (getOrientation() >= 360 && bTurningRight) {
                setOrientation(0);
            }
            setOrientation(getOrientation() + DEGREE_STEP);
        }

        if (getExpire() <= 0) {
            CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
        } else {
            setExpire(getExpire() - expiretime);
        }


    }

    /**
     * Rotate right.
     */
    public void rotateRight() {
        bTurningRight = true;
    }

    /**
     * Stop rotating.
     */
    public void stopRotating() {
        bTurningRight = false;
        bTurningLeft = false;
    }

    @Override
    public void draw(Graphics g) {


        if (getExpire() < MAX_EXPIRE - 25)
            fillDraw(g);
        else {
            drawAlt(g);
        }

    }

    /**
     * Sets expiretime.
     *
     * @param expiretime the expiretime
     */
    public void setExpiretime(int expiretime) {
        this.expiretime = expiretime;
    }
}
