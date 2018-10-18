package _08final.mvc.model;

import java.awt.*;
import java.util.ArrayList;


/**
 * The type Ufo bullet.
 */
public class UfoBullet extends Sprite {

	  private final double FIRE_POWER = 35.0;


    /**
     * Instantiates a new Ufo bullet.
     *
     * @param ufo the ufo
     */
    public UfoBullet(Ufo ufo){
		
		super();
	    setTeam(Team.FOE);
		
		//defined the points on a cartesean grid
		ArrayList<Point> pntCs = new ArrayList<Point>();


		pntCs.add(new Point(0, 5));
		pntCs.add(new Point(1, 3));
		pntCs.add(new Point(1, -2));
		pntCs.add(new Point(-1, -2));
		pntCs.add(new Point(-1, 3));

		assignPolarPoints(pntCs);

		//a bullet expires after 20 frames
	    setExpire( 20 );
	    setRadius(15);
	    setColor(ufo.getColor());

	    //everything is relative to the falcon ship that fired the bullet
	    setDeltaX( ufo.getDeltaX() +
	               Math.cos( Math.toRadians( ufo.getOrientation() ) ) * FIRE_POWER );
	    setDeltaY( ufo.getDeltaY() +
	               Math.sin( Math.toRadians( ufo.getOrientation() ) ) * FIRE_POWER );
	    setCenter( ufo.getCenter() );

	    //set the bullet orientation to the falcon (ship) orientation
	    setOrientation(ufo.getOrientation());


	}

	@Override
	public void move(){

		super.move();

		if (getExpire() == 0)
			CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
		else
			setExpire(getExpire() - 1);

	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		fillDraw(g);
	}
}
