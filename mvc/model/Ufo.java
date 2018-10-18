package _08final.mvc.model;

import _08final.mvc.controller.Game;
import _08final.sounds.Sound;

import java.awt.*;
import java.util.ArrayList;

/**
 * The type Ufo.
 */
public class Ufo extends Sprite implements Scorable{

    private Falcon falcon;
    private UfoOrb orb;
    private boolean ufoActive;
    private int level;
    private int strtLevel;
    private static final int POWER = 10;
    /**
     * The Score.
     */
    int score=level*100;


    /**
     * Instantiates a new Ufo.
     *
     * @param fal the fal
     */
    public Ufo(Falcon fal) {

        super();
        orb = new UfoOrb(this);
        CommandCenter.getInstance().getOpsList().enqueue(orb, CollisionOp.Operation.ADD);
        falcon = fal;
        setTeam(Team.FOE);
        ArrayList<Point> pntCs = new ArrayList<Point>();
        pntCs.add(new Point(-138, -5));
        pntCs.add(new Point(-126, -11));
        pntCs.add(new Point(-105, -17));
        pntCs.add(new Point(-82, -24));
        pntCs.add(new Point(-54, -27));
        pntCs.add(new Point(-36, -28));
        pntCs.add(new Point(-24, -22));
        pntCs.add(new Point(-9, -16));
        pntCs.add(new Point(2, -17));
        pntCs.add(new Point(17, -22));
        pntCs.add(new Point(24, -29));
        pntCs.add(new Point(34, -32));
        pntCs.add(new Point(48, -32));
        pntCs.add(new Point(63, -27));
        pntCs.add(new Point(78, -23));
        pntCs.add(new Point(98, -18));
        pntCs.add(new Point(110, -14));
        pntCs.add(new Point(125, -3));
        pntCs.add(new Point(112, 0));
        pntCs.add(new Point(97, 3));
        pntCs.add(new Point(81, 4));
        pntCs.add(new Point(68, 5));
        pntCs.add(new Point(51, 7));
        pntCs.add(new Point(29, 10));
        pntCs.add(new Point(20, 17));
        pntCs.add(new Point(11, 21));
        pntCs.add(new Point(-9, 25));
        pntCs.add(new Point(-24, 22));
        pntCs.add(new Point(-33, 15));
        pntCs.add(new Point(-41, 9));
        pntCs.add(new Point(-59, 6));
        pntCs.add(new Point(-83, 4));
        pntCs.add(new Point(-104, 2));
        pntCs.add(new Point(-121, 1));
        pntCs.add(new Point(-137, -5));
        pntCs.add(new Point(119, -4));
        assignPolarPoints(pntCs);

        setExpire(19999);
        setRadius(75);
        setColor(Color.RED);


        setRandDirection();

        setCenter(new Point(Game.R.nextInt(Game.DIM.width),
                Game.R.nextInt(Game.DIM.height)));
        ufoActive = true;
        level = CommandCenter.getInstance().getLevel() * POWER;
        strtLevel = level;

    }

    @Override
    public int getScore() {
        return score;
    }

    private void setRandDirection() {
        int nX = Game.R.nextInt(10);
        int nY = Game.R.nextInt(10);

        //set random DeltaX
        if (nX % 2 == 0)
            setDeltaX(nX);
        else
            setDeltaX(-nX);

        //set random DeltaY
        if (nY % 2 == 0)
            setDeltaY(nY);
        else
            setDeltaY(-nY);
    }

    @Override
    public void move() {
        super.move();
        int dX, dY;
        int angle;
        if (getCenter().y - falcon.getCenter().y < 0) {
            dX = getCenter().x - falcon.getCenter().x;
            dY = getCenter().y - falcon.getCenter().y;
            angle = (int) Math.toDegrees(Math.atan2(dY, dX)) - 180;
        } else {
            dX = falcon.getCenter().x - getCenter().x;
            dY = falcon.getCenter().y - getCenter().y;
            angle = (int) Math.toDegrees(Math.atan2(dY, dX));
        }
        setOrientation(angle);

        //adding expire functionality
        if (getExpire() == 0){
           //CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
           //CommandCenter.getInstance().getOpsList().enqueue(getOrb(), CollisionOp.Operation.REMOVE);

        }


        else {
            if (getExpire() % 75 == 0) {
                //fire bullet
                CommandCenter.getInstance().getOpsList().enqueue(new UfoBullet((this)), CollisionOp.Operation.ADD);
                Sound.playSound("ufoFire.wav");
                //setRandDirection();
            }
            setExpire(getExpire() - 1);
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        //fill this polygon (with whatever color it has)
        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
        //now draw a white border
        g.setColor(Color.WHITE);
        g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
        drawDefense(g);
    }

    /**
     * Sets falcon.
     *
     * @param falcon the falcon
     */
    public void setFalcon(Falcon falcon) {
        this.falcon = falcon;
    }

    /**
     * Is ufo active boolean.
     *
     * @return the boolean
     */
    public boolean isUfoActive() {
        return ufoActive;
    }

    /**
     * Sets ufo active.
     *
     * @param ufoActive the ufo active
     */
    public void setUfoActive(boolean ufoActive) {
        this.ufoActive = ufoActive;
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

    /**
     * Draw defense.
     *
     * @param g the g
     */
    public void drawDefense(Graphics g) {
        g.setColor(Color.white);
        int x = (int)Game.DIM.getWidth();
        int y = (int)Game.DIM.getHeight()-100;
        g.drawRect(20, y, 82, 20);
        String stgDisplay = "UFO Life: " + level;
        g.drawString(stgDisplay, 21, y - 15);
        int scale = (int) ((level * 1.0) / (strtLevel * 1.0) * 80);
        g.setColor(Color.RED);
        g.fillRect(21, y+1, scale, 18);
    }

    /**
     * Gets orb.
     *
     * @return the orb
     */
    public UfoOrb getOrb() {
        return orb;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
