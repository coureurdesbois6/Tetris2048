import java.awt.*;

public class TetrisDrawer {

    private static Font font = new Font("Arial", Font.BOLD, 16);
    private static Font fontSmaller = new Font("Arial", Font.BOLD, 8);


    private static Color[] colorLevel = {new Color(204, 195, 192),
            new Color(235, 224, 203),
            new Color(231, 177, 128),
            new Color(232, 153, 108),
            new Color(231, 131, 103),
            new Color(229, 105, 70),
            new Color(233, 207, 127),
            new Color(232, 204, 114),
            new Color(232, 200, 100),
            new Color(231, 197, 88),
            new Color(231, 195, 77)
    };


    public static void drawBackground() {
        StdDraw.setCanvasSize(500,500);
        StdDraw.setXscale(0, 500);
        StdDraw.setYscale(0, 500);

        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.filledRectangle(250, 0, 250, 6);
        StdDraw.filledRectangle(250, 500, 250, 6);
        StdDraw.filledRectangle(0, 250, 6, 250);
        StdDraw.filledRectangle(500, 250, 6, 250);
        StdDraw.filledRectangle(400,250,3,250);

        //Game at 6,6 to 397, 494
        //391x488 game size
        StdDraw.setPenColor(StdDraw.BLACK);

        //Grid background
        StdDraw.setPenColor(171, 155, 141);
        StdDraw.filledRectangle(201.5,250,195.5, 244);

        //Sidebar
        StdDraw.setPenColor(166, 160, 152);
        StdDraw.filledRectangle(449, 250,46 ,244);

        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(449, 465,"SCORE");
        StdDraw.text(449, 120,"NEXT");



        //while(true)
        //    System.out.println(StdDraw.mouseX() + " " + StdDraw.mouseY());

    }

    public static void drawNext(String nextTetromino) {
        StdDraw.setPenColor(166, 160, 152);
        StdDraw.filledRectangle(449, 70, 40,35);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                char currentChar = nextTetromino.charAt(4 * j + i);
                if (currentChar != '0') {
                    int currentValue = Integer.parseInt(Character.toString(currentChar));
                    double x = 430+(12*i);
                    double y = 86-(12*j);
                    StdDraw.setPenColor(130,112,114);
                    StdDraw.filledRectangle(x, y,6,6);
                    StdDraw.setPenColor(colorLevel[currentValue]);
                    StdDraw.filledRectangle(x, y,5.6,5.6);
                    StdDraw.setFont(fontSmaller);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.text(x,y, Integer.toString(2<<currentValue-1));
                }
            }
        }
    }


    public static void drawGame(int[][] screen) {
        StdDraw.setFont(font);
        for (int i = 0; i < Game.WIDTH; i++){
            for (int j = 0; j < Game.HEIGHT; j++){
                double x = 6 + (double)(397/Game.WIDTH)*(0.5 + i);
                double y = 6 + (double)(494/Game.HEIGHT)*(Game.HEIGHT-j-0.5);

                StdDraw.setPenColor(130,112,114);
                StdDraw.filledRectangle(x,y,397.0/Game.WIDTH/2,494.0/Game.HEIGHT/2);
                StdDraw.setPenColor(colorLevel[screen[i+1][j]]);
                StdDraw.filledRectangle(x,y,397.0/Game.WIDTH/2.1,494.0/Game.HEIGHT/2.1);

                //TODO: Make a better statement
                if (screen[i+1][j] != 0)
                    StdDraw.setPenColor(StdDraw.BLACK);

                StdDraw.text(x,y, Integer.toString(2<<screen[i+1][j]-1));
            }
        }
    }

    public static void drawScore(int score) {
        //delete the old
        StdDraw.setPenColor(166, 160, 152);
        StdDraw.filledRectangle(449, 435, 40,20);

        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(449, 435, String.valueOf(score));
    }
}
