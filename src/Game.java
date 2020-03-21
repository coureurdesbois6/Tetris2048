import java.util.ArrayList;

public class Game {

    private static final int WIDTH = 8;
    private static final int HEIGHT = 12;
    private static final String tetrominoes[] = {"0000011001100000",
            "0100010001000100", "0000001101100000",
            "0000110001100000", "0000010001000110",
            "0000001000100110", "0000011100100000"}; //4x4
    private static boolean gameOver = false;
    private int field[][] = new int[WIDTH][HEIGHT];
    private int screen[][] = new int[WIDTH][HEIGHT];

    private int currentFallingPiece = 0;
    private int currentFallingRotation = 0;
    private int currentFallingX = WIDTH/2;
    private int currentFallingY = 0;

    public void start() {
        while (!gameOver) {
            //INPUT

            //TIME

            //LOGIC

            //DRAW
            //assign field to screen
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    screen[i][j] = field[i][j];
                }
            }

            //assign piece to screen
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    screen[currentFallingX+i][currentFallingY+j] = (int)(tetrominoes[currentFallingPiece].charAt(j*4+i));
                }
            }
            //draw screen
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    System.out.print(screen[i][j]);
                }
                System.out.print("\n");
            }
            System.out.println("===============");
            gameOver = true;
        }
    }

    public int rotate(int x, int y, int rotation) {
        int result = 0;

        switch(rotation) {
            case 0: result = y * 4 + x;
                break;
            case 1: result = 12 + y - (x * 4);
                break;
            case 2: result = 15 - (y * 4) - x;
                break;
            case 3: result = 3 - y + (x * 4);
                break;
        }

        return result;
    }

    public static boolean fits(int piece, int rotation, int x, int y) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

            }
        }

        return true;
    }
}
