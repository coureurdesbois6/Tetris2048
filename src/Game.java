import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Scanner;

public class Game {

    public static final int WIDTH = 8;
    public static final int HEIGHT = 12;
    private static final String[] tetrominoes = {"0000011001100000",
            "0100010001000100", "0000001101100000",
            "0000110001100000", "0000010001000110",
            "0000001000100110", "0000011100100000"}; //4x4
    private static boolean gameOver = false;
    private static final int[] sysKeys = {KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN};

    //2 for borders on the sides, 1 for border at the bottom
    private int[][] field = new int[WIDTH + 2][HEIGHT + 1]; //holds game information, excluding current piece location
    private int[][] screen = new int[WIDTH + 2][HEIGHT + 1]; //holds draw information

    private int currentPiece = 0;
    private int currentRotation = 0;
    private int currentX = WIDTH / 4;
    private int currentY = 0;

    private int difficulty = 30;
    private int tickCount = 0;
    private boolean forceDown = false;
    private boolean[] keys = new boolean[4]; //R,L,U,D

    public void start() {
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        TetrisDrawer.drawBackground();

        for (int i = 0; i < HEIGHT + 1; i++) {
            field[0][i] = -1;
            field[9][i] = -1;
            screen[0][i] = -1;
            screen[9][i] = -1;
        }

        for (int i = 1; i < WIDTH + 1; i++) {
            field[i][12] = -1;
            screen[i][12] = -1;
        }


        while (!gameOver) {

            //INPUT
            for (int i = 0; i < 4; i++) {
                keys[i] = StdDraw.isKeyPressed(sysKeys[i]);
            }

            //TIME
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //LOGIC
            //force down
            if (tickCount == difficulty) {
                tickCount = 0;
                if (doesPieceFit(currentPiece, currentRotation, currentX, currentY + 1))
                    currentY += 1;
                else {
                    //paste field onto screen, inserting the piece
                    for (int i = 1; i < WIDTH + 1; i++) {
                        for (int j = 0; j < HEIGHT; j++) {
                            field[i][j] = screen[i][j];
                        }
                    }


                    //check row clearances
                    for (int i = 0; i < HEIGHT; i++) {
                        boolean clearRow = true;

                        for (int j = 1; j < WIDTH + 1; j++) {
                            if (field[j][i] == 0) {
                                clearRow = false;
                                break;
                            }
                        }

                        if (clearRow) {
                            //clear the row
                            for (int k = 1; k < WIDTH + 1; k++) {
                                field[k][i] = 0;
                            }

                            /* bring everything above the
                            cleared line one line down */
                            for (int j = i - 1; j >= 0; j--) {
                                for (int k = 1; k < WIDTH + 1; k++) {
                                    field[k][j + 1] = field[k][j];
                                    field[k][j] = 0;
                                }
                            }
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
            for (int i = 1; i < WIDTH + 1; i++) {
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
            logic2048();

            //DRAW
            TetrisDrawer.drawGame(screen);
            StdDraw.show();
        }
    }

    private int rotate(int x, int y, int rotation) {
        int result = 0;

        switch (rotation % 4) {
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
                if (tetrominoes[piece].charAt(rotate(i, j, rotation)) == '1' && field[x + i][y + j] != 0) {
                    //System.out.println("doesnt fit");
                    return false;
                }
            }
        }
        return true;
    }


    private void logic2048() {
        for (int i = 1; i < WIDTH + 1; i++) {
            for (int j = HEIGHT; j >= 0; j--) {
                if (field[i][j] != 0) {
                    for (int k = j + 1; k < HEIGHT; k++) {
                        if (field[i][k] == 0) {
                            continue;
                        } else {
                            if (field[i][k] == field[i][j]) {
                                field[i][k] += 1;
                                field[i][j] = 0;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        for (int i = 1; i < WIDTH + 1; i++) {
            for (int j = HEIGHT - 1; j >= 0; j--) {
                int down = 0;
                if (field[i][j] != 0) {
                    for (int k = j + 1; k < HEIGHT; k++) {
                        if (field[i][k] == 0)
                            down++;
                        else
                            break;
                    }

                    if (down != 0) {
                        field[i][j + down] = field[i][j];
                        field[i][j] = 0;
                    }
                }
            }
        }
    }
}
