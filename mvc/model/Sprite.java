package _08final.mvc.model;

import _08final.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * The type Sprite.
 */
public abstract class Sprite implements Movable {
	//the center-point of this sprite
	private Point pntCenter;
	//this causes movement; change in x and change in y
	private double dDeltaX, dDeltaY;
	//every sprite needs to know about the size of the gaming environ
	private Dimension dim; //dim of the gaming environment

	//we need to know what team we're on
	private Team mTeam;

	//the radius of circumscribing circle
	private int nRadius;

	private int nOrientation;
	private int nExpiry; //natural mortality (short-living objects)
	//the color of this sprite
	private Color col;

    /**
     * The D lengths.
     */
//radial coordinates
	//this game uses radial coordinates to render sprites
	public double[] dLengths;
    /**
     * The D degrees.
     */
    public double[] dDegrees;
	

	//fade value for fading in and out
	private int nFade;

	//these are used to draw the polygon. You don't usually need to interface with these
	private Point[] pntCoords; //an array of points used to draw polygon
	private int[] nXCoords;
	private int[] nYCoords;


	@Override
	public Team getTeam() {
		//default
	  return mTeam;
	}

    /**
     * Set team.
     *
     * @param team the team
     */
    public void setTeam(Team team){
		mTeam = team;
	}

	public void move() {

		Point pnt = getCenter();
		double dX = pnt.x + getDeltaX();
		double dY = pnt.y + getDeltaY();
		
		//this just keeps the sprite inside the bounds of the frame
		if (pnt.x > getDim().width) {
			setCenter(new Point(1, pnt.y));

		} else if (pnt.x < 0) {
			setCenter(new Point(getDim().width - 1, pnt.y));
		} else if (pnt.y > getDim().height) {
			setCenter(new Point(pnt.x, 1));

		} else if (pnt.y < 0) {
			setCenter(new Point(pnt.x, getDim().height - 1));
		} else {

			setCenter(new Point((int) dX, (int) dY));
		}

	}

    /**
     * Instantiates a new Sprite.
     */
    public Sprite() {

	//you can override this and many more in the subclasses
		setDim(Game.DIM);
		setColor(Color.white);
		setCenter(new Point(Game.R.nextInt(Game.DIM.width),
				Game.R.nextInt(Game.DIM.height)));


	}

    /**
     * Sets expire.
     *
     * @param n the n
     */
    public void setExpire(int n) {
		nExpiry = n;

	}

    /**
     * Get lengths double [ ].
     *
     * @return the double [ ]
     */
    public double[] getLengths() {
		return this.dLengths;
	}

    /**
     * Sets lengths.
     *
     * @param dLengths the d lengths
     */
    public void setLengths(double[] dLengths) {
		this.dLengths = dLengths;
	}

    /**
     * Get degrees double [ ].
     *
     * @return the double [ ]
     */
    public double[] getDegrees() {
		return this.dDegrees;
	}

    /**
     * Sets degrees.
     *
     * @param dDegrees the d degrees
     */
    public void setDegrees(double[] dDegrees) {
		this.dDegrees = dDegrees;
	}

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
		return col;
	}

    /**
     * Sets color.
     *
     * @param col the col
     */
    public void setColor(Color col) {
		this.col = col;

	}

    /**
     * Points int.
     *
     * @return the int
     */
    public int points() {
		//default is zero
		return 0;
	}

    /**
     * Gets expire.
     *
     * @return the expire
     */
    public int getExpire() {
		return nExpiry;
	}

    /**
     * Gets orientation.
     *
     * @return the orientation
     */
    public int getOrientation() {
		return nOrientation;
	}

    /**
     * Sets orientation.
     *
     * @param n the n
     */
    public void setOrientation(int n) {
		nOrientation = n;
	}

    /**
     * Sets delta x.
     *
     * @param dSet the d set
     */
    public void setDeltaX(double dSet) {
		dDeltaX = dSet;
	}

    /**
     * Sets delta y.
     *
     * @param dSet the d set
     */
    public void setDeltaY(double dSet) {
		dDeltaY = dSet;
	}

    /**
     * Gets delta y.
     *
     * @return the delta y
     */
    public double getDeltaY() {
		return dDeltaY;
	}

    /**
     * Gets delta x.
     *
     * @return the delta x
     */
    public double getDeltaX() {
		return dDeltaX;
	}

	public int getRadius() {
		return nRadius;
	}

    /**
     * Sets radius.
     *
     * @param n the n
     */
    public void setRadius(int n) {
		nRadius = n;

	}

    /**
     * Gets dim.
     *
     * @return the dim
     */
    public Dimension getDim() {
		return dim;
	}

    /**
     * Sets dim.
     *
     * @param dim the dim
     */
    public void setDim(Dimension dim) {
		this.dim = dim;
	}

	public Point getCenter() {
		return pntCenter;
	}

    /**
     * Sets center.
     *
     * @param pntParam the pnt param
     */
    public void setCenter(Point pntParam) {
		pntCenter = pntParam;
	}


    /**
     * Sets ycoord.
     *
     * @param nValue the n value
     * @param nIndex the n index
     */
    public void setYcoord(int nValue, int nIndex) {
		nYCoords[nIndex] = nValue;
	}

    /**
     * Sets xcoord.
     *
     * @param nValue the n value
     * @param nIndex the n index
     */
    public void setXcoord(int nValue, int nIndex) {
		nXCoords[nIndex] = nValue;
	}


    /**
     * Gets ycoord.
     *
     * @param nIndex the n index
     * @return the ycoord
     */
    public int getYcoord( int nIndex) {
		return nYCoords[nIndex];// = nValue;
	}

    /**
     * Gets xcoord.
     *
     * @param nIndex the n index
     * @return the xcoord
     */
    public int getXcoord( int nIndex) {
		return nXCoords[nIndex];// = nValue;
	}


    /**
     * Get xcoords int [ ].
     *
     * @return the int [ ]
     */
    public int[] getXcoords() {
		return nXCoords;
	}

    /**
     * Get ycoords int [ ].
     *
     * @return the int [ ]
     */
    public int[] getYcoords() {
		return nYCoords;
	}


    /**
     * Sets xcoords.
     *
     * @param nCoords the n coords
     */
    public void setXcoords( int[] nCoords) {
		 nXCoords = nCoords;
	}

    /**
     * Sets ycoords.
     *
     * @param nCoords the n coords
     */
    public void setYcoords(int[] nCoords) {
		 nYCoords =nCoords;
	}

    /**
     * Hypot double.
     *
     * @param dX the d x
     * @param dY the d y
     * @return the double
     */
    protected double hypot(double dX, double dY) {
		return Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
	}


    /**
     * Convert to polar degs double [ ].
     *
     * @param pntPoints the pnt points
     * @return the double [ ]
     */
//utility function to convert from cartesian to polar
	//since it's much easier to describe a sprite as a list of cartesean points
	//sprites (except Asteroid) should use the cartesean technique to describe the coordinates
	//see Falcon or Bullet constructor for examples
	protected double[] convertToPolarDegs(ArrayList<Point> pntPoints) {

	   double[] dDegs = new double[pntPoints.size()];

		int nC = 0;
		for (Point pnt : pntPoints) {
			dDegs[nC++]=(Math.toDegrees(Math.atan2(pnt.y, pnt.x))) * Math.PI / 180 ;
		}
		return dDegs;
	}

    /**
     * Convert to polar lens double [ ].
     *
     * @param pntPoints the pnt points
     * @return the double [ ]
     */
//utility function to convert to polar
	protected double[] convertToPolarLens(ArrayList<Point> pntPoints) {

		double[] dLens = new double[pntPoints.size()];

		//determine the largest hypotenuse
		double dL = 0;
		for (Point pnt : pntPoints)
			if (hypot(pnt.x, pnt.y) > dL)
				dL = hypot(pnt.x, pnt.y);

		int nC = 0;
		for (Point pnt : pntPoints) {
			if (pnt.x == 0 && pnt.y > 0) {
				dLens[nC] = hypot(pnt.x, pnt.y) / dL;
			} else if (pnt.x < 0 && pnt.y > 0) {
				dLens[nC] = hypot(pnt.x, pnt.y) / dL;
			} else {
				dLens[nC] = hypot(pnt.x, pnt.y) / dL;
			}
			nC++;
		}

		// holds <thetas, lens>
		return dLens;

	}

    /**
     * Assign polar points.
     *
     * @param pntCs the pnt cs
     */
    protected void assignPolarPoints(ArrayList<Point> pntCs) {
		setDegrees(convertToPolarDegs(pntCs));
		setLengths(convertToPolarLens(pntCs));

	}

	@Override
    public void draw(Graphics g) {
        nXCoords = new int[dDegrees.length];
        nYCoords = new int[dDegrees.length];
        //need this as well
        pntCoords = new Point[dDegrees.length];
        

        for (int nC = 0; nC < dDegrees.length; nC++) {
            nXCoords[nC] =    (int) (getCenter().x + getRadius() 
                            * dLengths[nC] 
                            * Math.sin(Math.toRadians(getOrientation()) + dDegrees[nC]));
            nYCoords[nC] =    (int) (getCenter().y - getRadius()
                            * dLengths[nC]
                            * Math.cos(Math.toRadians(getOrientation()) + dDegrees[nC]));
            
            
            //need this line of code to create the points which we will need for debris
            pntCoords[nC] = new Point(nXCoords[nC], nYCoords[nC]);
        }

        g.setColor(getColor());
        g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
    }

    /**
     * Fill draw.
     *
     * @param g the g
     */
    public void fillDraw(Graphics g) {
		nXCoords = new int[dDegrees.length];
		nYCoords = new int[dDegrees.length];
		//need this as well
		pntCoords = new Point[dDegrees.length];


		for (int nC = 0; nC < dDegrees.length; nC++) {
			nXCoords[nC] =    (int) (getCenter().x + getRadius()
					* dLengths[nC]
					* Math.sin(Math.toRadians(getOrientation()) + dDegrees[nC]));
			nYCoords[nC] =    (int) (getCenter().y - getRadius()
					* dLengths[nC]
					* Math.cos(Math.toRadians(getOrientation()) + dDegrees[nC]));


			//need this line of code to create the points which we will need for debris
			pntCoords[nC] = new Point(nXCoords[nC], nYCoords[nC]);
		}

		g.setColor(getColor());
		g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
	}


    /**
     * Get object points point [ ].
     *
     * @return the point [ ]
     */
    public Point[] getObjectPoints() {
		return pntCoords;
	}

    /**
     * Sets object points.
     *
     * @param pntPs the pnt ps
     */
    public void setObjectPoints(Point[] pntPs) {
		 pntCoords = pntPs;
	}

    /**
     * Sets object point.
     *
     * @param pnt    the pnt
     * @param nIndex the n index
     */
    public void setObjectPoint(Point pnt, int nIndex) {
		 pntCoords[nIndex] = pnt;
	}

    /**
     * Gets fade value.
     *
     * @return the fade value
     */
    public int getFadeValue() {
		return nFade;
	}

    /**
     * Sets fade value.
     *
     * @param n the n
     */
    public void setFadeValue(int n) {
		nFade = n;
	}

    /**
     * Get pnt coords point [ ].
     *
     * @return the point [ ]
     */
    public Point[] getPntCoords() {
		return pntCoords;
	}
}
