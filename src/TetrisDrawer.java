/**
 * Drawing class for tetris game, methods here
 * are written to use in the Game class.
 *
 * @author  Yigit Sezer, Pinar Haskiris
 * @version 1.0
 * @since   2020-03-20
 */

import java.awt.*;

public class TetrisDrawer {

    private static Font font = new Font("Arial", Font.BOLD, 16);
    private static Font fontSmaller = new Font("Arial", Font.BOLD, 8);


    private static Color[] colorLevel = {
            new Color(204,195,182), //0
            new Color(237, 228, 219), //2
            new Color(235,224,203), //4
            new Color(233,179,129), //8
            new Color(232, 153, 108), //16
            new Color(231, 131, 103), //32
            new Color(229,105,70), //64
            new Color(233,207,127), //128
            new Color(232,204,114), //256
            new Color(232,200,100),//512
            new Color(231,195,77), //1024
            new Color(231,197,88) //2048
    };

    //drawing the game background
    public static void drawBackground() {
        StdDraw.setCanvasSize(500,500);
        StdDraw.setXscale(0, 500);
        StdDraw.setYscale(0, 500);

        //drawing the background
        StdDraw.setPenColor(113, 103, 94);
        StdDraw.filledRectangle(0,0,500,500);

        //drawing the squares (grid)
        StdDraw.setPenColor(171, 155, 141);
        StdDraw.filledRectangle(201.5,250,195.5, 244);

        //drawing the sidebar
        StdDraw.setPenColor(150, 143, 132);
        StdDraw.filledRectangle(449, 250,46 ,244);

        //adding next
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(449, 465,"SCORE");
        StdDraw.text(449, 120,"NEXT");
    }

    //drawing the next tetromino piece
    public static void drawNext(int[] nextTetromino) {

        //delete where the next block is going to be -> paint with the same color of background
        StdDraw.setPenColor(150, 143, 132);
        StdDraw.filledRectangle(449, 70, 40,35);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int val = nextTetromino[4 * j + i]; //determining the value
                if (val != 0) {
                    double x = 430+(12*i);
                    double y = 86-(12*j);
                    StdDraw.setPenColor(130,112,114);
                    StdDraw.filledRectangle(x, y,6,6);
                    StdDraw.setPenColor(colorLevel[val]);
                    StdDraw.filledRectangle(x, y,5.6,5.6);
                    StdDraw.setFont(fontSmaller);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.text(x,y, Integer.toString(2<<val-1));
                }
            }
        }
    }

    //drawing the game itself
    public static void drawGame(int[][] screen) {
        StdDraw.setFont(font);
        for (int i = 1; i < Game.WIDTH+1; i++){
            for (int j = 0; j < Game.HEIGHT; j++){
                double x = 6 + (double)(397/Game.WIDTH)*(i - 0.5);
                double y = 6 + (double)(494/Game.HEIGHT)*(Game.HEIGHT-j-0.5);

                //drawing the background
                StdDraw.setPenColor(130,112,114);
                StdDraw.filledRectangle(x,y,397.0/Game.WIDTH/2,494.0/Game.HEIGHT/2);
                StdDraw.setPenColor(colorLevel[screen[i][j]]);
                StdDraw.filledRectangle(x,y,397.0/Game.WIDTH/2.1,494.0/Game.HEIGHT/2.1);

                if (screen[i][j] != 0)
                    StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text(x,y, Integer.toString(2<<screen[i][j]-1));
            }
        }
    }

    //drawing the score
    public static void drawScore(int score) {

        //delete the old score
        StdDraw.setPenColor(150, 143, 132);
        StdDraw.filledRectangle(449, 435, 40,20);

        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(449, 435, String.valueOf(score));
    }

    //drawing the splash screen when game is over
    public static void drawSplashScreen(int score) {
        StdDraw.setPenColor(210,105,30);
        StdDraw.filledRectangle(250,250,110,110);

        //Try again button
        StdDraw.setPenColor(105, 30, 210);
        StdDraw.filledRectangle(250,220,70,14);

        //Quit button
        StdDraw.setPenColor(105, 30, 210);
        StdDraw.filledRectangle(250,180,70,14);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font);
        StdDraw.text(250, 300, "Game over!");
        String str = "You scored:" + score;
        StdDraw.text(250,270, str);
        StdDraw.text(250,220, "Try again: Enter");
        StdDraw.text(250,180, "Quit: Escape");
    }
}