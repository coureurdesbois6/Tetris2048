import java.awt.event.KeyEvent;
import java.util.Random;

public class Game {

    public static final int WIDTH = 8;
    public static final int HEIGHT = 12;
    private static final int[][] tetrominoes = {{0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0},
            {0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0}, {0,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0},
            {0,0,0,0,1,1,0,0,0,1,1,0,0,0,0,0}, {0,0,0,0,0,1,0,0,0,1,0,0,0,1,1,0},
            {0,0,0,0,0,0,1,0,0,0,1,0,0,1,1,0}, {0,0,0,0,0,1,1,1,0,0,1,0,0,0,0,0}};
    private static boolean gameOver = false;
    private static final int[] sysKeys = {KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE};

    //2 for borders on the sides, 1 for border at the bottom
    private int[][] field = new int[WIDTH + 2][HEIGHT + 1]; //holds game information, excluding current piece location
    private int[][] screen = new int[WIDTH + 2][HEIGHT + 1]; //holds draw information

    public int score = 0;

    private int[] nextPieceArr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private int[] currentPieceArr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private int currentPiece = 0;
    private int currentRotation = 0;
    private int currentX = WIDTH / 4;
    private int currentY = 0;

    private int difficulty = 30; //Unused difficulty variable
    private int tickCount = 0;
    private boolean rotateHold = false;
    private boolean[] keys = new boolean[sysKeys.length]; //R,L,U,D, Enter, Escape

    public void start() {
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        TetrisDrawer.drawBackground();

        int randomNext = (currentPiece + 1 + (int) (Math.random()*6)) % 7;
        currentPieceArr = shuffleTetromino(tetrominoes[currentPiece]);
        nextPieceArr = tetrominoes[randomNext];
        nextPieceArr = shuffleTetromino(nextPieceArr);
        TetrisDrawer.drawNext(nextPieceArr);

        for (int i = 0; i < HEIGHT + 1; i++) {
            field[0][i] = -1;
            field[WIDTH+1][i] = -1;
            screen[0][i] = -1;
            screen[WIDTH+1][i] = -1;
        }

        for (int i = 1; i < WIDTH + 1; i++) {
            field[i][HEIGHT] = -1;
            screen[i][HEIGHT] = -1;
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
                    logic2048();


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
                            //clear the row and add to score
                            for (int k = 1; k < WIDTH + 1; k++) {
                                score += 2<<field[k][i]-1;
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
                    currentPiece = randomNext;
                    currentRotation = 0;
                    currentX = WIDTH / 4;
                    currentY = 0;
                    currentPieceArr = nextPieceArr;
                    randomNext = (currentPiece + 1 + (int) (Math.random()*6)) % 7;
                    nextPieceArr = tetrominoes[randomNext];
                    nextPieceArr = shuffleTetromino(nextPieceArr);
                    TetrisDrawer.drawNext(nextPieceArr);

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
                if (!rotateHold && doesPieceFit(currentPiece, currentRotation + 1, currentX, currentY)) {
                    currentRotation += 1;
                    rotateHold = true;
                } else {
                    rotateHold = false;
                }
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
            int val = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (tetrominoes[currentPiece][rotate(i, j, currentRotation)] != 0) {
                        val = currentPieceArr[rotate(i, j, currentRotation)];
                        screen[currentX + i][currentY + j] = val;
                    }
                }
            }

            //DRAW
            TetrisDrawer.drawScore(score);
            TetrisDrawer.drawGame(screen);
            StdDraw.show();
        }


        TetrisDrawer.drawSplashScreen(score);
        StdDraw.show();

        while(true) {
            if (StdDraw.isKeyPressed(sysKeys[4])) {
                gameOver = false;
                field = new int[WIDTH + 2][HEIGHT + 1];
                screen = new int[WIDTH + 2][HEIGHT + 1];
                score = 0;
                this.start();
            } else if (StdDraw.isKeyPressed(sysKeys[5])) {
                System.exit(0);
                break;
            }
        }
    }

    private int[] shuffleTetromino(int[] next) {
        Random rd = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (next[4*j + i] == 1){
                    if (rd.nextBoolean()) {
                        next[4*j + i] = 2;
                    }
                }
            }
        }
        return next;
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
                if (tetrominoes[piece][rotate(i, j, rotation)] != 0 && field[x + i][y + j] != 0) {
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
                    for (int k = j - 1; k >=0; k--) {
                        if (field[i][k] == field[i][j]) {
                            field[i][j] += 1;
                            score += 2<<field[i][j]-1;
                            field[i][k] = 0;
                            j=HEIGHT;
                        } else if (field[i][k] != 0){
                            break;
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
