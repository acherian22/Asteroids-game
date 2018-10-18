package _08final.mvc.model;

import _08final.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The type Ufo orb.
 */
public class UfoOrb extends Sprite {
    private Ufo ufo;
    private boolean orbActive;
    private int level;
    private int strtLevel;
    private static final int POWER=1;
    private Random mRandom= new Random();

    /**
     * Instantiates a new Ufo orb.
     *
     * @param ufo the ufo
     */
    public UfoOrb(Ufo ufo){
        setTeam(Team.FOE);
        this.ufo=ufo;

        //defined the points on a cartesean grid
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(-275,-75));
        pntCs.add(new Point(-186,-23));
        pntCs.add(new Point(-174,68));
        pntCs.add(new Point(-244,140));
        pntCs.add(new Point(-147,117));
        pntCs.add(new Point(-74,170));
        pntCs.add(new Point(-75,273));
        pntCs.add(new Point(-22,185));
        pntCs.add(new Point(69,173));
        pntCs.add(new Point(141,245));
        pntCs.add(new Point(116,146));
        pntCs.add(new Point(170,74));
        pntCs.add(new Point(272,72));
        pntCs.add(new Point(183,19));
        pntCs.add(new Point(173,-66));
        pntCs.add(new Point(243,-142));
        pntCs.add(new Point(145,-116));
        pntCs.add(new Point(74,-171));
        pntCs.add(new Point(72,-274));
        pntCs.add(new Point(21,-185));
        pntCs.add(new Point(-69,-173));
        pntCs.add(new Point(-142,-247));
        pntCs.add(new Point(-115,-148));
        pntCs.add(new Point(-173,-74));

        assignPolarPoints(pntCs);

        //a bullet expires after 20 frames
        setExpire( 20 );
        setRadius(25);
        orbActive=true;
        level=CommandCenter.getInstance().getLevel()*POWER;
        strtLevel=level;
    }


    @Override
    public void draw(Graphics g) {
        super.draw(g);
        int dX=(int)(ufo.getCenter().x + ufo.getRadius()*1.25 * Math.cos(Math.toRadians(ufo.getOrientation()-180)));
        int dY=(int)(ufo.getCenter().y + ufo.getRadius()*1.25 * Math.sin(Math.toRadians(ufo.getOrientation()-180)));
        this.setCenter(new Point(dX,dY));
        g.setColor(new Color(mRandom.nextInt(200)+50));
        g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
        drawDefense(g);
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
     * Gets ufo.
     *
     * @return the ufo
     */
    public Ufo getUfo() {
        return ufo;
    }

    /**
     * Draw defense.
     *
     * @param g the g
     */
    public void drawDefense(Graphics g) {
        g.setColor(Color.white);
        int x = (int) Game.DIM.getWidth();
        int y = (int)Game.DIM.getHeight()-175;
        g.drawRect(20, y, 82, 20);
        String stgDisplay = "ORB Life: " + level;
        g.drawString(stgDisplay, 21, y - 15);
        int scale = (int) ((level * 1.0) / (strtLevel * 1.0) * 80);
        g.setColor(Color.BLUE);
        g.fillRect(21, y+1, scale, 18);
    }
}
