package _08final.mvc.model;

import _08final.sounds.Sound;

/**
 * The type Spray gun.
 */
public class SprayGun extends Sprite {

    /**
     * Instantiates a new Spray gun.
     */
    public SprayGun() {
    }

    /**
     * Spray.
     */
    public void spray() {
        Sound.playSound("laserSpray.wav");
        Falcon fal = CommandCenter.getInstance().getFalcon();
        int x = fal.getOrientation();
        Bullet b1 = new Bullet(fal);
        fal.setOrientation(x + 45);
        Bullet b2 = new Bullet(fal);
        fal.setOrientation(x - 45);
        Bullet b3 = new Bullet(fal);
        fal.setOrientation(x - 23);
        Bullet b4 = new Bullet(fal);
        fal.setOrientation(x + 23);
        Bullet b5 = new Bullet(fal);
        fal.setOrientation(x);
        b1.setExpire(10);
        b2.setExpire(10);
        b3.setExpire(10);
        b4.setExpire(10);
        b5.setExpire(10);
        CommandCenter.getInstance().getOpsList().enqueue(b1, CollisionOp.Operation.ADD);
        CommandCenter.getInstance().getOpsList().enqueue(b2, CollisionOp.Operation.ADD);
        CommandCenter.getInstance().getOpsList().enqueue(b3, CollisionOp.Operation.ADD);
        CommandCenter.getInstance().getOpsList().enqueue(b4, CollisionOp.Operation.ADD);
        CommandCenter.getInstance().getOpsList().enqueue(b5, CollisionOp.Operation.ADD);
    }

}

