package _08final.mvc.model;

import _08final.sounds.Sound;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Command center.
 */
public class CommandCenter {

    private int nNumFalcon;
    private int nNumShields;
    private boolean shieldOn = false;
    private int nLevel;
    private long lScore;
    private Falcon falShip;
    private TargetMissile tarMis;
    private boolean bPlaying;
    private boolean bPaused;
    private boolean controllingMissile;
    private boolean ufoOn=false;
    private SprayGun sgun;

    // These ArrayLists with capacities set
    private List<Movable> movDebris = new ArrayList<Movable>(300);
    private List<Movable> movFriends = new ArrayList<Movable>(100);
    private List<Movable> movFoes = new ArrayList<Movable>(200);
    private List<Movable> movFloaters = new ArrayList<Movable>(50);

    private GameOpsList opsList = new GameOpsList();


    private static CommandCenter instance = null;

    // Constructor made private - static Utility class only
    private CommandCenter() {
    }


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CommandCenter getInstance() {
        if (instance == null) {
            instance = new CommandCenter();
        }
        return instance;
    }


    /**
     * Init game.
     */
    public void initGame() {
        setLevel(1);
        setScore(0);
        setNumFalcons(5);
        spawnFalcon(true);
        setnNumShields(4);
        sgun=new SprayGun();
    }

    /**
     * Spawn falcon.
     *
     * @param bFirst the b first
     */
// The parameter is true if this is for the beginning of the game, otherwise false
    // When you spawn a new falcon, you need to decrement its number
    public void spawnFalcon(boolean bFirst) {
        if (getNumFalcons() != 0) {
            falShip = new Falcon();

            opsList.enqueue(falShip, CollisionOp.Operation.ADD);
            if (!bFirst)
                setNumFalcons(getNumFalcons() - 1);
        }

        Sound.playSound("shipspawn.wav");

    }

    /**
     * Spawn target missile.
     */
    public void spawnTargetMissile() {
        tarMis = new TargetMissile(falShip);
        opsList.enqueue(tarMis, CollisionOp.Operation.ADD);
        Sound.playSound("missile-launch.wav");

    }


    /**
     * Gets ops list.
     *
     * @return the ops list
     */
    public GameOpsList getOpsList() {
        return opsList;
    }

    /**
     * Sets ops list.
     *
     * @param opsList the ops list
     */
    public void setOpsList(GameOpsList opsList) {
        this.opsList = opsList;
    }

    /**
     * Clear all.
     */
    public void clearAll() {
        movDebris.clear();
        movFriends.clear();
        movFoes.clear();
        movFloaters.clear();
    }

    /**
     * Is playing boolean.
     *
     * @return the boolean
     */
    public boolean isPlaying() {
        return bPlaying;
    }

    /**
     * Sets playing.
     *
     * @param bPlaying the b playing
     */
    public void setPlaying(boolean bPlaying) {
        this.bPlaying = bPlaying;
    }

    /**
     * Is paused boolean.
     *
     * @return the boolean
     */
    public boolean isPaused() {
        return bPaused;
    }

    /**
     * Sets paused.
     *
     * @param bPaused the b paused
     */
    public void setPaused(boolean bPaused) {
        this.bPaused = bPaused;
    }

    /**
     * Is game over boolean.
     *
     * @return the boolean
     */
    public boolean isGameOver() {        //if the number of falcons is zero, then game over
        if (getNumFalcons() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return nLevel;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public long getScore() {
        return lScore;
    }

    /**
     * Sets score.
     *
     * @param lParam the l param
     */
    public void setScore(long lParam) {
        lScore = lParam;
    }

    /**
     * Sets level.
     *
     * @param n the n
     */
    public void setLevel(int n) {
        nLevel = n;
    }

    /**
     * Gets num falcons.
     *
     * @return the num falcons
     */
    public int getNumFalcons() {
        return nNumFalcon;
    }

    /**
     * Sets num falcons.
     *
     * @param nParam the n param
     */
    public void setNumFalcons(int nParam) {
        nNumFalcon = nParam;
    }

    /**
     * Gets falcon.
     *
     * @return the falcon
     */
    public Falcon getFalcon() {
        return falShip;
    }

    /**
     * Gets num shields.
     *
     * @return the num shields
     */
    public int getnNumShields() {
        return nNumShields;
    }

    /**
     * Sets num shields.
     *
     * @param nNumShields the n num shields
     */
    public void setnNumShields(int nNumShields) {
        this.nNumShields = nNumShields;
    }

    /**
     * Sets falcon.
     *
     * @param falParam the fal param
     */
    public void setFalcon(Falcon falParam) {
        falShip = falParam;
    }

    /**
     * Gets mov debris.
     *
     * @return the mov debris
     */
    public List<Movable> getMovDebris() {
        return movDebris;
    }


    /**
     * Gets mov friends.
     *
     * @return the mov friends
     */
    public List<Movable> getMovFriends() {
        return movFriends;
    }


    /**
     * Gets mov foes.
     *
     * @return the mov foes
     */
    public List<Movable> getMovFoes() {
        return movFoes;
    }


    /**
     * Gets mov floaters.
     *
     * @return the mov floaters
     */
    public List<Movable> getMovFloaters() {
        return movFloaters;
    }

    /**
     * Is shield on boolean.
     *
     * @return the boolean
     */
    public boolean isShieldOn() {
        return shieldOn;
    }

    /**
     * Sets shield on.
     *
     * @param shieldOn the shield on
     */
    public void setShieldOn(boolean shieldOn) {
        this.shieldOn = shieldOn;
    }

    /**
     * Is controlling missile boolean.
     *
     * @return the boolean
     */
    public boolean isControllingMissile() {
        return controllingMissile;
    }

    /**
     * Gets tar mis.
     *
     * @return the tar mis
     */
    public TargetMissile getTarMis() {
        return tarMis;
    }

    /**
     * Sets controlling missile.
     *
     * @param controllingMissile the controlling missile
     */
    public void setControllingMissile(boolean controllingMissile) {
        this.controllingMissile = controllingMissile;
    }

    /**
     * Gets sgun.
     *
     * @return the sgun
     */
    public SprayGun getSgun() {
        return sgun;
    }

    /**
     * Is ufo on boolean.
     *
     * @return the boolean
     */
    public boolean isUfoOn() {
        return ufoOn;
    }

    /**
     * Sets ufo on.
     *
     * @param ufoOn the ufo on
     */
    public void setUfoOn(boolean ufoOn) {
        this.ufoOn = ufoOn;
    }
}
