import java.awt.*;
import java.util.Random;

public class Tetrominoes {
    private Color color;
    private double x;
    private double y;
    private int[][] reference;

    /** Shape should also have:
     * time related fields -> to use while moving
     * speed related fields -> again, to use while moving
     * filled related field(s) -> boolean -> to check whether the line is filled
     */

    public Tetrominoes(){}
    public Tetrominoes(Color color, int[][] reference){
        this.x = x;
        this.y = y;
        this.color = color;

        this.reference = new int[reference.length][reference[0].length]; //creating a new 2d array
        System.arraycopy(reference, 0, this.reference, 0, reference.length);
    }

    public Color getColor(){
        return color;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int[][] getReference(){
        return reference;
    }


    // ---------------------------- MAKING SHAPES --------------------------------------------

    public Tetrominoes createTetromino(Character inputLetter) {
        Tetrominoes[] tetrominoes = new Tetrominoes[7]; //will store the possible shapes

        tetrominoes[0] = new Tetrominoes(Color.red, new int[][]{
                {1, 1, 1, 1} //SHAPE I
        });

        tetrominoes[1] = new Tetrominoes(Color.orange, new int[][]{
                {1, 1},
                {1, 1} //SHAPE O
        });

        tetrominoes[2] = new Tetrominoes(Color.green, new int[][]{
                {1, 1, 1},
                {1, 0, 0} //SHAPE L
        });
        tetrominoes[3] = new Tetrominoes(Color.magenta, new int[][]{
                {1, 1, 1},
                {0, 0, 1} //SHAPE J
        });

        tetrominoes[4] = new Tetrominoes(Color.blue, new int[][]{
                {1, 1, 1},
                {0, 1, 0} //SHAPE T
        });

        tetrominoes[5] = new Tetrominoes(Color.yellow, new int[][]{
                {1, 1, 0},
                {0, 1, 1} //SHAPE Z
        });

        tetrominoes[6] = new Tetrominoes(Color.pink, new int[][]{
                {0, 1, 1},
                {1, 1, 0} //SHAPE S
        });

        if (inputLetter.equals('I')) { return tetrominoes[0]; }
        else if (inputLetter.equals('O')) { return tetrominoes[1]; }
        else if (inputLetter.equals('L')) { return tetrominoes[2]; }
        else if (inputLetter.equals('J')) { return tetrominoes[3]; }
        else if (inputLetter.equals('T')) { return tetrominoes[4]; }
        else if (inputLetter.equals('Z')) { return tetrominoes[5]; }
        else if (inputLetter.equals('S')) { return tetrominoes[6]; }
        else { return null; }

    }
}


