import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class Game {

    private static final int WIDTH = 8;
    private static final int HEIGHT = 12;
    private static final String[] tetrominoes = {"0000011001100000",
            "0100010001000100", "0000001101100000",
            "0000110001100000", "0000010001000110",
            "0000001000100110", "0000011100100000"}; //4x4
    private static boolean gameOver = false;
    private static final int[] sysKeys = {KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN};

    //2 for borders on the sides, 1 for border at the bottom
    private int[][] field = new int[WIDTH+2][HEIGHT+1]; //holds game information, excluding current piece location
    private int[][] screen = new int[WIDTH+2][HEIGHT+1]; //holds draw information

    private int currentPiece = 0;
    private int currentRotation = 0;
    private int currentX = WIDTH / 4;
    private int currentY = 0;

    private int difficulty = 500;
    private int tickCount = 0;
    private boolean forceDown = false;
    private boolean[] keys = new boolean[4]; //R,L,U,D

    public void start() {
        StdDraw.setXscale(0, 16);
        StdDraw.setYscale(0, 24);
        //StdDraw.setXscale(0, 8);
        //StdDraw.setYscale(0, 12);

        for (int i = 0; i < HEIGHT+1; i++) {
            field[0][i] = -1;
            field[9][i] = -1;
            screen[0][i] = -1;
            screen[9][i] = -1;
        }
        for (int i = 1; i < WIDTH+1; i++) {
            field[i][12] = -1;
            screen[i][12] = -1;
        }

        while (!gameOver) {
            long startTime = System.nanoTime() / 1000000;
            long endTime = System.nanoTime() / 1000000;
            //INPUT
            for (int i = 0; i < 4; i++) {
                keys[i] = StdDraw.isKeyPressed(sysKeys[i]);
            }
            //Test keys
            /*
            for (int i = 0; i < 4; i++) {
                int x = keys[i] ? 1 : 0;
                System.out.print(x);
            }
            System.out.println();
             */


            //TIME


            //LOGIC
            //force down
            if (tickCount == 1) {
                tickCount = 0;
                if (doesPieceFit(currentPiece, currentRotation, currentX, currentY + 1))
                    currentY += 1;
                else {
                    //paste screen onto field, inserting the piece
                    for (int i = 1; i < WIDTH+1; i++) {
                        for (int j = 0; j < HEIGHT; j++) {
                            field[i][j] = screen[i][j];
                        }
                    }

                    //reset
                    currentPiece = (currentPiece + 1) % 7;
                    currentRotation = 0;
                    currentX = WIDTH / 4;
                    currentY = 0;

                    //game over if cant spawn new piece
                    if (!doesPieceFit(currentPiece, currentRotation, currentX, currentY)) {
                        gameOver = true;
                        break;
                    }
                }
            } else {
                tickCount++;
            }

            if (keys[0]) //RIGHT
                if (doesPieceFit(currentPiece, currentRotation, currentX + 1, currentY))
                    currentX += 1;
            if (keys[1]) //LEFT
                if (doesPieceFit(currentPiece, currentRotation, currentX - 1, currentY))
                    currentX -= 1;
            if (keys[2]) //UP (ROTATE)
                if (doesPieceFit(currentPiece, currentRotation + 1, currentX, currentY))
                    currentRotation += 1;
            //if (doesPieceFit(currentPiece, currentRotation, currentX, currentY - 1))
            //currentY -= 1;
            if (keys[3]) //DOWN
                if (doesPieceFit(currentPiece, currentRotation, currentX, currentY + 1))
                    currentY += 1;


            //assign field to screen, ignore borders
            for (int i = 1; i < WIDTH+1; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    screen[i][j] = field[i][j];
                }
            }

            //assign piece to screen
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (tetrominoes[currentPiece].charAt(rotate(i, j, currentRotation)) == '1')
                        screen[currentX + i][currentY + j] = 1;
                }
            }

            //DRAW
            //draw screen
            for (int j = 0; j < HEIGHT+1; j++) {
                for (int i = 0; i < WIDTH+2; i++) {
                    if (screen[i][j] == 0)
                        StdDraw.setPenColor(Color.BLACK);
                    else if (screen[i][j] == 1)
                        StdDraw.setPenColor(Color.RED);
                    else if (screen[i][j] == -1)
                        StdDraw.setPenColor(Color.ORANGE);

                    StdDraw.filledSquare(i+3, 12-j, 0.5);
                }
            }
            //System.out.println("end of tick");
            //gameOver = true;
        }
    }

    private int rotate(int x, int y, int rotation) {
        int result = 0;

        switch (rotation%4) {
            case 0:
                result = y * 4 + x;
                break;
            case 1:
                result = 12 + y - (x * 4);
                break;
            case 2:
                result = 15 - (y * 4) - x;
                break;
            case 3:
                result = 3 - y + (x * 4);
                break;
        }

        return result;
    }

    private boolean doesPieceFit(int piece, int rotation, int x, int y) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tetrominoes[piece].charAt(rotate(i, j, rotation)) == '1' && field[x+i][y+j] != 0) {
                    //System.out.println("doesnt fit");
                    return false;
                }
            }
        }

        return true;
    }
}
