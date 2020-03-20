import java.awt.*;
import java.util.Random;

public class SimpleTetris {
    public static void main(String[] args) {

        //CREATING A WINDOW (CANVAS)
        StdDraw.setCanvasSize(450 + 20, 550); //20 for the sidebar -> canvasHeight was 550
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        drawBackground();

        //GENERATING A RANDOM LETTER
        Character[] letters = {'I', 'O', 'L', 'J', 'T', 'Z', 'S'}; //stores all the characters
        Random random = new Random();
        int randomInt = random.nextInt(7); //generating a random number between 0-7
        Character inputLetter = letters[randomInt]; //getting a random letter

        //GENERATING A RANDOM TETROMINO
        Tetrominoes tet = new Tetrominoes();
        tet = tet.createTetromino(inputLetter);
        drawTet(tet, 0, 0, tet.getColor());

    }
    public static void drawBackground(){

        //DRAWING THE BACKGROUND
        StdDraw.setPenColor(195, 183, 166);
        StdDraw.filledRectangle(0,0,1,1);

        StdDraw.setPenColor(171, 155, 141);
        StdDraw.setPenRadius(0.005);

        //DRAWING THE SQUARES - part of the background
        for (int row = 0; row < 11; row++){
            for (int column = 0; column < 8; column++){
                double x = (0.091 * column) + 0.06;
                double y = (0.091 * row) + 0.045;

                StdDraw.square(x,y,0.0475);
            }
        }

        //DRAWING THE SIDEBAR
        StdDraw.setPenColor(150, 143, 132);
        StdDraw.filledRectangle(0.9,0.5,0.15, 0.5); // new


        //DRAWING THE BIG SQUARE AROUND THE CANVAS
        StdDraw.setPenColor(113, 103, 94);
        StdDraw.setPenRadius(0.015);

        StdDraw.line(0, 0.005, 1, 0.005); //bottom line
        StdDraw.line(0, 1, 1, 1); //top line
        StdDraw.line(0.005, 1, 0.005,0); //left line
        StdDraw.line(0.995, 1, 0.995, 0); //right line
        StdDraw.line(0.75, 1, 0.75, 0); //middle line

        //ADDING TEXT
        StdDraw.setPenColor(255, 255, 255);
        Font font = new Font("Arial", Font.BOLD, 16);
        StdDraw.setFont(font);
        StdDraw.text(0.87, 0.92, "SCORE");
        StdDraw.text(0.87, 0.25, "NEXT");
    }

    public static void drawTet(Tetrominoes tet, double x, double y, Color color){
        for (int row = 0; row < tet.getReference().length; row++){
            for (int column = 0; column < tet.getReference()[0].length; column++){
                if (tet.getReference()[row][column] != 0){
                    StdDraw.setXscale(0, 1);
                    StdDraw.setYscale(0, 1);
                    StdDraw.setPenColor(color);
                    StdDraw.filledSquare(column * 0.087 + x+0.06, row * 0.07 + y + 0.06, 0.045);
                }
            }
        }
    }
}
