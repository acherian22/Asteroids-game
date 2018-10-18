package _08final.mvc.controller;

import _08final.mvc.model.*;
import _08final.mvc.view.GamePanel;
import _08final.sounds.Sound;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Random;

// ===============================================
// == This Game class is the CONTROLLER
// ===============================================

/**
 * The type Game.
 */
public class Game implements Runnable, KeyListener {

    // ===============================================
    // FIELDS
    // ===============================================

    /**
     * The constant DIM.
     */
    public static final Dimension DIM = new Dimension(1100, 800); //the dimension of the game.
    private GamePanel gmpPanel;
    /**
     * The constant R.
     */
    public static Random R = new Random();
    /**
     * The constant ANI_DELAY.
     */
    public final static int ANI_DELAY = 45; // milliseconds between screen
    // updates (animation)
    private Thread thrAnim;
    private int nLevel = 1;
    private int nTick = 0;
    private long score = 0;

    private boolean bMuted = true;


    private final int PAUSE = 80, // p key
            QUIT = 81, // q key
            LEFT = 37, // rotate left; left arrow
            RIGHT = 39, // rotate right; right arrow
            UP = 38, // thrust; up arrow
            START = 83, // s key
            FIRE = 32, // space key
            MUTE = 77, // m-key mute

    // for possible future use
    HYPER = 68,                        // D key
            SHIELD = 72,                       // H Key
            NUM_ENTER = 82,                    // R Key Nuke All
            SPECIAL = 70,                      // fire special weapon;  F key
            SPRAY = 89,                      // fire special weapon;  Y key
            CRUISE = 69;                      // fire cruise missle;  E key

    private Clip clpThrust;
    private Clip clpMusicBackground;
    private Clip shieldActive;


    private static final int SPAWN_NEW_SHIP_FLOATER = 1200;


    // ===============================================
    // ==CONSTRUCTOR
    // ===============================================

    /**
     * Instantiates a new Game.
     */
    public Game() {

        gmpPanel = new GamePanel(DIM);
        gmpPanel.addKeyListener(this);
        clpThrust = Sound.clipForLoopFactory("whitenoise.wav");
        clpMusicBackground = Sound.clipForLoopFactory("music-background.wav");
        shieldActive = Sound.clipForLoopFactory("shieldActive.wav");


    }

    // ===============================================
    // ==METHODS
    // ===============================================

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() { // uses the Event dispatch thread from Java 5 (refactored)
            public void run() {
                try {
                    Game game = new Game(); // construct itself
                    game.fireUpAnimThread();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fireUpAnimThread() { // called initially
        if (thrAnim == null) {
            thrAnim = new Thread(this); // pass the thread a runnable object (this)
            thrAnim.start();
        }
    }

    // implements runnable - must have run method
    public void run() {

        // lower this thread's priority; let the "main" aka 'Event Dispatch'
        // thread do what it needs to do first
        thrAnim.setPriority(Thread.MIN_PRIORITY);

        // and get the current time
        long lStartTime = System.currentTimeMillis();

        // this thread animates the scene
        while (Thread.currentThread() == thrAnim) {
            tick();
            spawnNewShipFloater();
            gmpPanel.update(gmpPanel.getGraphics()); // update takes the graphics context we must
            // surround the sleep() in a try/catch block
            // this simply controls delay time between
            // the frames of the animation

            //this might be a good place to check for collisions
            checkCollisions();
            //this might be a god place to check if the level is clear (no more foes)
            //if the level is clear then spawn some big asteroids -- the number of asteroids
            //should increase with the level.
            checkForUfo();
            checkNewLevel();

            try {
                // The total amount of time is guaranteed to be at least ANI_DELAY long.  If processing (update)
                // between frames takes longer than ANI_DELAY, then the difference between lStartTime -
                // System.currentTimeMillis() will be negative, then zero will be the sleep time
                lStartTime += ANI_DELAY;
                Thread.sleep(Math.max(0,
                        lStartTime - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                // just skip this frame -- no big deal
                continue;
            }
        } // end while
    } // end run

    private void checkForUfo() {
        int count = 0;
        int level = CommandCenter.getInstance().getLevel();
        //check to see if there are any more foes left and whether to add ufo
        for (Movable movFoe : CommandCenter.getInstance().getMovFoes()) {
            count = count + 1;
        }
        if (count == 0 & level > 1 & !(level % 2 == 0)) {
            if (!CommandCenter.getInstance().isUfoOn()) {
                spawnUfo();
            } else {
                CommandCenter.getInstance().setUfoOn(false);
            }
        }
    }

    private void checkCollisions() {


        Point pntFriendCenter, pntFoeCenter;
        int nFriendRadiux, nFoeRadiux;

        for (Movable movFriend : CommandCenter.getInstance().getMovFriends()) {
            for (Movable movFoe : CommandCenter.getInstance().getMovFoes()) {

                pntFriendCenter = movFriend.getCenter();
                pntFoeCenter = movFoe.getCenter();
                nFriendRadiux = movFriend.getRadius();
                nFoeRadiux = movFoe.getRadius();
                if (movFoe instanceof Ufo) {
                    ((Ufo) movFoe).setFalcon(CommandCenter.getInstance().getFalcon());
                }

                //detect collision
                if (pntFriendCenter.distance(pntFoeCenter) < (nFriendRadiux + nFoeRadiux)) {
                    //Shield
                    if ((movFriend instanceof Shield)) {
                        if (((Shield) movFriend).getLevel() <= 1) {
                            CommandCenter.getInstance().getOpsList().enqueue(movFriend, CollisionOp.Operation.REMOVE);
                            CommandCenter.getInstance().setShieldOn(false);
                        }
                        ((Shield) movFriend).decrementLevel();  //decrement shield level
                    }
                    //falcon
                    else if ((movFriend instanceof Falcon)) {
                        if (!CommandCenter.getInstance().getFalcon().getProtected() & !CommandCenter.getInstance().isShieldOn()) {
                            CommandCenter.getInstance().getOpsList().enqueue(movFriend, CollisionOp.Operation.REMOVE);
                            CommandCenter.getInstance().spawnFalcon(false);
                        }
                    }
                    //not the falcon
                    else {
                        CommandCenter.getInstance().getOpsList().enqueue(movFriend, CollisionOp.Operation.REMOVE);
                    }//end else
                    //kill the foe and if asteroid, then spawn new asteroids
                    if (movFoe instanceof Asteroid & !(movFoe instanceof TitaniumAsteroid)) {
                        CommandCenter.getInstance().getOpsList()
                                .enqueue(new Debris(40, movFoe.getCenter(), (Sprite) movFoe),
                                        CollisionOp.Operation.ADD);
                        killFoe(movFoe);
                    } else if (movFoe instanceof Ufo) {
                        if (movFriend instanceof Cruise) {
                            ((Ufo) movFoe).setLevel(((Ufo) movFoe).getLevel() - 3);
                            Sound.playSound("ufoHit.wav");
                        } else {
                            ((Ufo) movFoe).setLevel(((Ufo) movFoe).getLevel() - 1);
                            Sound.playSound("ufoHit.wav");
                        }
                        //kill the ufo and its orb every time one of them gets to level 0
                        if (((Ufo) movFoe).getLevel() < 1) {
                            UfoOrb ufo = ((Ufo) movFoe).getOrb();
                            killFoe(movFoe);
                            killFoe(ufo);
                            scatteredDebris(movFoe);
                            Sound.playSound("ufoDeath.wav");
                        }

                    } else if (movFoe instanceof UfoOrb) {
                        if (movFriend instanceof Cruise) {
                            ((UfoOrb) movFoe).setLevel(((UfoOrb) movFoe).getLevel() - 3);
                            Sound.playSound("ufoHit.wav");
                        } else {
                            ((UfoOrb) movFoe).setLevel(((UfoOrb) movFoe).getLevel() - 1);
                            Sound.playSound("ufoHit.wav");
                        }
                        if (((UfoOrb) movFoe).getLevel() < 1) {
                            Ufo ufo = ((UfoOrb) movFoe).getUfo();
                            ufo.setScore(ufo.getScore()*2);
                            killFoe(movFoe);
                            killFoe(ufo);
                            scatteredDebris(ufo);
                            Sound.playSound("ufoDeath.wav");
                        }

                    } else if (movFoe instanceof TitaniumAsteroid) {
                        TitaniumAsteroid tAst = (TitaniumAsteroid) movFoe;
                        if (tAst.isbProtected()) {
                        } else if (movFriend instanceof Cruise) {
                            tAst.setLevel(tAst.getLevel() - 3);
                            Sound.playSound("bite1.wav");
                            CommandCenter.getInstance().getOpsList()
                                    .enqueue(new Debris(40, movFoe.getCenter()),
                                            CollisionOp.Operation.ADD);
                        } else {
                            tAst.setLevel(tAst.getLevel() - 1);
                            Sound.playSound("bite1.wav");
                            CommandCenter.getInstance().getOpsList()
                                    .enqueue(new Debris(40, movFoe.getCenter()),
                                            CollisionOp.Operation.ADD);
                        }
                        if (tAst.getLevel() < 1) {
                            killFoe(movFoe);
                            scatteredDebris(movFoe);
                        }
                    } else {
                        CommandCenter.getInstance().getOpsList()
                                .enqueue(new Debris(40, movFoe.getCenter()),
                                        CollisionOp.Operation.ADD);
                        killFoe(movFoe);
                    }
                }//end if
            }//end inner for
        }//end outer for


        //check for collisions between falcon and floaters
        if (CommandCenter.getInstance().getFalcon() != null) {
            Point pntFalCenter = CommandCenter.getInstance().getFalcon().getCenter();
            int nFalRadiux = CommandCenter.getInstance().getFalcon().getRadius();
            Point pntFloaterCenter;
            int nFloaterRadiux;

            for (Movable movFloater : CommandCenter.getInstance().getMovFloaters()) {
                pntFloaterCenter = movFloater.getCenter();
                nFloaterRadiux = movFloater.getRadius();

                //detect collision
                if (pntFalCenter.distance(pntFloaterCenter) < (nFalRadiux + nFloaterRadiux)) {
                    CommandCenter.getInstance().setNumFalcons(CommandCenter.getInstance().getNumFalcons() + 1);
                    CommandCenter.getInstance().getOpsList().enqueue(movFloater, CollisionOp.Operation.REMOVE);
                    Sound.playSound("pacman_eatghost.wav");


                }//end if
            }//end inner for
        }//end if not null


        checkScore(); //check to see if Score change leads to new powerups
        if (CommandCenter.getInstance().isShieldOn()) {
            shieldActive.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            stopLoopingSounds(shieldActive);
        }


        //we are dequeuing the opsList and performing operations in serial to avoid mutating the movable arraylists while iterating them above
        while (!CommandCenter.getInstance().getOpsList().isEmpty()) {
            CollisionOp cop = CommandCenter.getInstance().getOpsList().dequeue();
            Movable mov = cop.getMovable();
            CollisionOp.Operation operation = cop.getOperation();

            switch (mov.getTeam()) {
                case FOE:
                    if (operation == CollisionOp.Operation.ADD) {
                        CommandCenter.getInstance().getMovFoes().add(mov);
                    } else {
                        CommandCenter.getInstance().getMovFoes().remove(mov);
                    }

                    break;
                case FRIEND:
                    if (operation == CollisionOp.Operation.ADD) {
                        CommandCenter.getInstance().getMovFriends().add(mov);
                    } else {
                        CommandCenter.getInstance().getMovFriends().remove(mov);
                    }
                    break;

                case FLOATER:
                    if (operation == CollisionOp.Operation.ADD) {
                        CommandCenter.getInstance().getMovFloaters().add(mov);
                    } else {
                        CommandCenter.getInstance().getMovFloaters().remove(mov);
                    }
                    break;

                case DEBRIS:
                    if (operation == CollisionOp.Operation.ADD) {
                        CommandCenter.getInstance().getMovDebris().add(mov);
                    } else {
                        CommandCenter.getInstance().getMovDebris().remove(mov);
                    }
                    break;


            }

        }
        //a request to the JVM is made every frame to garbage collect, however, the JVM will choose when and how to do this
        System.gc();

    }//end meth

    private void scatteredDebris(Movable mov) {
        Sprite sprite = (Sprite) mov;
        for (int n = 0; n < 360; n++) {
            CommandCenter.getInstance().getOpsList().enqueue(new Shard(sprite, n), CollisionOp.Operation.ADD);
        }
    }


    private void killFoe(Movable movFoe) {
        Sound.playSound("kapow.wav");

        if (movFoe instanceof Asteroid) {
            Asteroid astExploded = (Asteroid) movFoe;
            if (movFoe instanceof TitaniumAsteroid) {
                TitaniumAsteroid tAstExploded = (TitaniumAsteroid) movFoe;
                if (tAstExploded.getSize() < 3) {
                    CommandCenter.getInstance().getOpsList().enqueue(new TitaniumAsteroid(tAstExploded), CollisionOp.Operation.ADD);
                } else {
                }
            }
            //we know this is an Asteroid, so we can cast without threat of ClassCastException
            //big asteroid
            else if (astExploded.getSize() == 0) {
                //spawn two medium Asteroids
                CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(astExploded), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(astExploded), CollisionOp.Operation.ADD);

            }
            //medium size aseroid exploded
            else if (astExploded.getSize() == 1) {
                //spawn three small Asteroids
                CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(astExploded), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(astExploded), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(astExploded), CollisionOp.Operation.ADD);

            }

        }
        if (movFoe instanceof Scorable) {
            CommandCenter.getInstance().setScore(CommandCenter.getInstance().getScore() + ((Scorable) movFoe).getScore());
        }

        //remove the original Foe
        CommandCenter.getInstance().getOpsList().enqueue(movFoe, CollisionOp.Operation.REMOVE);

    }

    //some methods for timing events in the game,
    //such as the appearance of UFOs, floaters (power-ups), etc.

    /**
     * Tick.
     */
    public void tick() {
        if (nTick == Integer.MAX_VALUE)
            nTick = 0;
        else
            nTick++;
    }

    /**
     * Gets tick.
     *
     * @return the tick
     */
    public int getTick() {
        return nTick;
    }

    private void spawnNewShipFloater() {
        if (nTick % (SPAWN_NEW_SHIP_FLOATER - nLevel * 7) == 0) {
            CommandCenter.getInstance().getOpsList().enqueue(new NewShipFloater(), CollisionOp.Operation.ADD);
        }
    }

    private void spawnUfo() {
        CommandCenter.getInstance().getMovFoes().add(new Ufo(CommandCenter.getInstance().getFalcon()));
        CommandCenter.getInstance().setUfoOn(true);
        CommandCenter.getInstance().getFalcon().setProtected(true);
        Sound.playSound("ufoLaunch.wav");

        //}
    }


    // Called when user presses 's'
    private void startGame() {
        CommandCenter.getInstance().clearAll();
        CommandCenter.getInstance().initGame();
        CommandCenter.getInstance().setLevel(0);
        CommandCenter.getInstance().setPlaying(true);
        CommandCenter.getInstance().setPaused(false);
        if (!bMuted)
            clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
    }

    //this method spawns new asteroids
    private void spawnAsteroids(int nNum) {
        for (int nC = 0; nC < nNum; nC++) {
            //Asteroids with size of zero are big
            CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(0), CollisionOp.Operation.ADD);

        }
    }

    private void spawnTAsteroids(int nNum) {
        for (int nC = 0; nC < nNum; nC++) {
            //Asteroids with size of zero are big
            CommandCenter.getInstance().getOpsList().enqueue(new TitaniumAsteroid(2), CollisionOp.Operation.ADD);

        }
    }


    private boolean isLevelClear() {
        //if there are no more Asteroids on the screen
        boolean bAsteroidFree = true;
        int count = 0;
        for (Movable movFoe : CommandCenter.getInstance().getMovFoes()) {
            count = count + 1;
        }
        if (count > 0) {
            bAsteroidFree = false;
        }
        return bAsteroidFree;
    }

    //check score to see if we got 10000 pts and add shield
    private void checkScore() {
        long oldScore = score;
        score = CommandCenter.getInstance().getScore();
        if (score / 5000 > oldScore / 5000) {
            CommandCenter.getInstance().setnNumShields(CommandCenter.getInstance().getnNumShields() + 1);
        }


    }


    private void checkNewLevel() {

        if (isLevelClear()) {
            if (CommandCenter.getInstance().getFalcon() != null)
                CommandCenter.getInstance().getFalcon().setProtected(true);

            spawnAsteroids(CommandCenter.getInstance().getLevel() / 2 + 1);
            CommandCenter.getInstance().setLevel(CommandCenter.getInstance().getLevel() + 1);
            if (CommandCenter.getInstance().getLevel() % 2 == 0) {
                spawnTAsteroids(CommandCenter.getInstance().getLevel() / 2);
            }

        }
    }


    // Varargs for stopping looping-music-clips
    private static void stopLoopingSounds(Clip... clpClips) {
        for (Clip clp : clpClips) {
            clp.stop();
        }
    }

    // ===============================================
    // KEYLISTENER METHODS
    // ===============================================

    @Override
    public void keyPressed(KeyEvent e) {
        Falcon fal = CommandCenter.getInstance().getFalcon();
        TargetMissile tarMis = CommandCenter.getInstance().getTarMis();
        int nKey = e.getKeyCode();
        // System.out.println(nKey);

        if (nKey == START && !CommandCenter.getInstance().isPlaying())
            startGame();

        if (fal != null) {

            switch (nKey) {
                case PAUSE:
                    CommandCenter.getInstance().setPaused(!CommandCenter.getInstance().isPaused());
                    if (CommandCenter.getInstance().isPaused())
                        stopLoopingSounds(clpMusicBackground, clpThrust);
                    else
                        clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
                    break;
                case QUIT:
                    System.exit(0);
                    break;
                case UP:
                    fal.thrustOn();
                    if (!CommandCenter.getInstance().isPaused())
                        clpThrust.loop(Clip.LOOP_CONTINUOUSLY);
                    break;
                case LEFT:
                    if (CommandCenter.getInstance().isControllingMissile()) {
                        tarMis.rotateLeft();
                    } else {
                        fal.rotateLeft();
                    }
                    break;
                case RIGHT:
                    if (CommandCenter.getInstance().isControllingMissile()) {
                        tarMis.rotateRight();
                    } else {
                        fal.rotateRight();
                    }
                    break;

                case SPECIAL:
                    CommandCenter.getInstance().spawnTargetMissile();
                    CommandCenter.getInstance().setControllingMissile(true);
                    break;

                // possible future use
                // case KILL:

                // case NUM_ENTER:

                default:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Falcon fal = CommandCenter.getInstance().getFalcon();
        TargetMissile tarMis = CommandCenter.getInstance().getTarMis();
        int nKey = e.getKeyCode();
        System.out.println(nKey);

        if (fal != null) {
            switch (nKey) {
                case FIRE:
                    CommandCenter.getInstance().getOpsList().enqueue(new Bullet(fal), CollisionOp.Operation.ADD);
                    Sound.playSound("laser.wav");
                    break;

                //special is a special weapon, current it just fires the cruise missile.
                case SPECIAL:
                    CommandCenter.getInstance().setControllingMissile(false);
                    tarMis.setExpiretime(75);
                    tarMis.stopRotating();
                    break;

                case LEFT:
                    fal.stopRotating();
                    if (CommandCenter.getInstance().isControllingMissile()) {
                        tarMis.stopRotating();
                    }
                    break;

                case CRUISE:
                    CommandCenter.getInstance().getOpsList().enqueue(new Cruise(fal), CollisionOp.Operation.ADD);
                    Sound.playSound("cruiserSound.wav");
                    break;

                case RIGHT:
                    fal.stopRotating();
                    if (CommandCenter.getInstance().isControllingMissile()) {
                        tarMis.stopRotating();
                    }
                    break;
                case UP:
                    fal.thrustOff();
                    clpThrust.stop();
                    break;

                case MUTE:
                    if (!bMuted) {
                        stopLoopingSounds(clpMusicBackground);
                        bMuted = !bMuted;
                    } else {
                        clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
                        bMuted = !bMuted;
                    }
                    break;

                case HYPER:
                    Point point = new Point(
                            Game.R.nextInt((int) Game.DIM.getWidth()),
                            Game.R.nextInt((int) Game.DIM.getHeight()));
                    CommandCenter.getInstance().getFalcon().setCenter(point);
                    break;

                case NUM_ENTER:
                    //get all foes and destroy them
                    for (Movable movable : CommandCenter.getInstance().getMovFoes()) {
                        killFoe(movable);
                    }
                    break;
                case SHIELD:
                    if (CommandCenter.getInstance().getnNumShields() > 0 & !CommandCenter.getInstance().isShieldOn()) {
                        CommandCenter.getInstance().getOpsList().enqueue(new Shield(), CollisionOp.Operation.ADD);
                        CommandCenter.getInstance().setnNumShields(CommandCenter.getInstance().getnNumShields() - 1);
                        CommandCenter.getInstance().setShieldOn(true);
                        Sound.playSound("shieldOn.wav");
                    }
                    break;
                case SPRAY:
                    CommandCenter.getInstance().getSgun().spray();
                default:
                    break;


            }
        }
    }

    @Override
    // Just need it b/c of KeyListener implementation
    public void keyTyped(KeyEvent e) {
    }

}


