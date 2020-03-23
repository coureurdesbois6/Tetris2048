import java.awt.*;

public class TetrisDrawer {
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
        //
        StdDraw.setPenColor(StdDraw.BLACK);
        //StdDraw.square(397,494, 5); //DEBUG
        //StdDraw.square(6,6, 5); //DEBUG

        //Grid background
        StdDraw.setPenColor(171, 155, 141);
        StdDraw.filledRectangle(201.5,250,195.5, 244);



        //while(true)
        //    System.out.println(StdDraw.mouseX() + " " + StdDraw.mouseY());

    }


    public static void drawGame(int[][] screen) {
        for (int i = 0; i < Game.WIDTH; i++){
            for (int j = 0; j < Game.HEIGHT; j++){
                double x = 6 + (double)(397/Game.WIDTH)*(0.5 + i);
                double y = 6 + (double)(494/Game.HEIGHT)*(Game.HEIGHT-j-0.5);

                StdDraw.setPenColor(180,170,160);
                //StdDraw.filledRectangle(x,y,397.0/Game.WIDTH/2,494.0/Game.HEIGHT/2);

                if (screen[i+1][j] == 0) //blank
                    StdDraw.setPenColor(Color.BLACK);
                else if (screen[i+1][j] == 1) //squares
                    StdDraw.setPenColor(Color.RED);
                else if (screen[i+1][j] == -1) //borders
                    StdDraw.setPenColor(Color.ORANGE);

                StdDraw.filledRectangle(x,y,397.0/Game.WIDTH/4,494.0/Game.HEIGHT/4);

                StdDraw.setPenColor(StdDraw.BLUE);
                //StdDraw.text(x,y, Integer.toString(screen[i+1][j]));


            }
        }
    }
}
