package _08final.mvc.view;

import _08final.mvc.controller.Game;
import _08final.mvc.model.CommandCenter;
import _08final.mvc.model.Falcon;
import _08final.mvc.model.Movable;

import java.awt.*;
import java.util.ArrayList;


/**
 * The type Game panel.
 */
public class GamePanel extends Panel {

    // ==============================================================
    // FIELDS
    // ==============================================================

    // The following "off" vars are used for the off-screen double-bufferred image.
    private Dimension dimOff;
    private Image imgOff;
    private Graphics grpOff;

    private GameFrame gmf;
    private Font fnt = new Font("SansSerif", Font.BOLD, 12);
    private Font fntBig = new Font("SansSerif", Font.BOLD + Font.ITALIC, 36);
    private FontMetrics fmt;
    private int nFontWidth;
    private int nFontHeight;
    private String strDisplay = "";


    // ==============================================================
    // CONSTRUCTOR
    // ==============================================================

    /**
     * Instantiates a new Game panel.
     *
     * @param dim the dim
     */
    public GamePanel(Dimension dim) {
        gmf = new GameFrame();
        gmf.getContentPane().add(this);
        gmf.pack();
        initView();

        gmf.setSize(dim);
        gmf.setTitle("Game Base");
        gmf.setResizable(false);
        gmf.setVisible(true);
        this.setFocusable(true);
    }


    // ==============================================================
    // METHODS
    // ==============================================================

    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(fnt);
        if (CommandCenter.getInstance().getScore() != 0) {
            g.drawString("SCORE :  " + CommandCenter.getInstance().getScore(), nFontWidth, nFontHeight);
        } else {
            g.drawString("NO SCORE", nFontWidth, nFontHeight);
        }
    }

    private void drawLevel(Graphics g) {
        g.setColor(Color.white);
        g.setFont(fnt);
        String stgDisplay = "Level: " + CommandCenter.getInstance().getLevel();
        g.drawString(stgDisplay, 20, 30);
    }

    private void drawShield(Graphics g) {
        g.setColor(Color.white);
        g.setFont(fnt);
        String stgDisplay;
        if (CommandCenter.getInstance().getnNumShields() != 0) {
            stgDisplay = "Shields :  " + CommandCenter.getInstance().getnNumShields();
        } else {
            stgDisplay = "No Shields";
        }
        g.drawString(stgDisplay,
                Game.DIM.width - 80, Game.DIM.height - 60);

    }

    @SuppressWarnings("unchecked")
    public void update(Graphics g) {
        if (grpOff == null || Game.DIM.width != dimOff.width
                || Game.DIM.height != dimOff.height) {
            dimOff = Game.DIM;
            imgOff = createImage(Game.DIM.width, Game.DIM.height);
            grpOff = imgOff.getGraphics();
        }
        // Fill in background with black.
        grpOff.setColor(Color.black);
        grpOff.fillRect(0, 0, Game.DIM.width, Game.DIM.height);

        drawScore(grpOff);


        if (!CommandCenter.getInstance().isPlaying()) {
            displayTextOnScreen();
        } else if (CommandCenter.getInstance().isPaused()) {
            strDisplay = "Game Paused";
            grpOff.drawString(strDisplay,
                    (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);
        }

        //playing and not paused!
        else {

            //draw them in decreasing level of importance
            //friends will be on top layer and debris on the bottom
            iterateMovables(grpOff,
                    (ArrayList<Movable>) CommandCenter.getInstance().getMovFriends(),
                    (ArrayList<Movable>) CommandCenter.getInstance().getMovFoes(),
                    (ArrayList<Movable>) CommandCenter.getInstance().getMovFloaters(),
                    (ArrayList<Movable>) CommandCenter.getInstance().getMovDebris());

            drawShield(grpOff);
            drawLevel(grpOff);

            drawNumberShipsLeft(grpOff);

            if (CommandCenter.getInstance().isGameOver()) {
                CommandCenter.getInstance().setPlaying(false);
                //bPlaying = false;
            }
        }
        //draw the double-Buffered Image to the graphics context of the panel
        g.drawImage(imgOff, 0, 0, this);
    }


    //for each movable array, process it.
    private void iterateMovables(Graphics g, ArrayList<Movable>... movMovz) {

        for (ArrayList<Movable> movMovs : movMovz) {
            for (Movable mov : movMovs) {

                mov.move();
                mov.draw(g);

            }
        }

    }


    // Draw the number of falcons left on the bottom-right of the screen.
    private void drawNumberShipsLeft(Graphics g) {
        Falcon fal = CommandCenter.getInstance().getFalcon();
        double[] dLens = fal.getLengths();
        int nLen = fal.getDegrees().length;
        Point[] pntMs = new Point[nLen];
        int[] nXs = new int[nLen];
        int[] nYs = new int[nLen];

        //convert to cartesean points
        for (int nC = 0; nC < nLen; nC++) {
            pntMs[nC] = new Point((int) (10 * dLens[nC] * Math.sin(Math
                    .toRadians(90) + fal.getDegrees()[nC])),
                    (int) (10 * dLens[nC] * Math.cos(Math.toRadians(90)
                            + fal.getDegrees()[nC])));
        }

        //set the color to white
        g.setColor(Color.white);
        //for each falcon left (not including the one that is playing)
        for (int nD = 1; nD < CommandCenter.getInstance().getNumFalcons(); nD++) {
            //create x and y values for the objects to the bottom right using cartesean points again
            for (int nC = 0; nC < fal.getDegrees().length; nC++) {
                nXs[nC] = pntMs[nC].x + Game.DIM.width - (20 * nD);
                nYs[nC] = pntMs[nC].y + Game.DIM.height - 40;
            }
            g.drawPolygon(nXs, nYs, nLen);
        }
    }

    private void initView() {
        Graphics g = getGraphics();            // get the graphics context for the panel
        g.setFont(fnt);                        // take care of some simple font stuff
        fmt = g.getFontMetrics();
        nFontWidth = fmt.getMaxAdvance();
        nFontHeight = fmt.getHeight();
        g.setFont(fntBig);                    // set font info
    }

    // This method draws some text to the middle of the screen before/after a game
    private void displayTextOnScreen() {

        strDisplay = "GAME OVER";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);

        strDisplay = "Use the arrow keys to turn and thrust";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 40);

        strDisplay = "Use the space bar to fire";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 60);

        strDisplay = "'S' to Start";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 80);

        strDisplay = "'P' to Pause";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 100);

        strDisplay = "'Q' to Quit";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 120);
        strDisplay = "'H' for Shield-Win shields every time you score 5,000 points.";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 140);

        strDisplay = "Hold left index finger on 'F' for Guided Missile " +
                "\n(You will lose control of Falcon while guiding missile)";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 160);

        strDisplay = "'D' for Hyperspace";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 180);
        strDisplay = "'R' to Nuke all enemies.";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 200);
        strDisplay = "'E' for the standard Cruise Missile.";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 220);
        strDisplay = "'Y' for shorter length Bullet Spray.";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 240);
        strDisplay = "Bullets take one hit point away from Foes.\n" +
                "Use Cruise or Targeted Missiles to zap 3 points from Special Foes." +
                "\nGet 2x the score for killing a UFO if you destroy its ORB!";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 260);

    }

    /**
     * Gets frm.
     *
     * @return the frm
     */
    public GameFrame getFrm() {
        return this.gmf;
    }

    /**
     * Sets frm.
     *
     * @param frm the frm
     */
    public void setFrm(GameFrame frm) {
        this.gmf = frm;
    }
}