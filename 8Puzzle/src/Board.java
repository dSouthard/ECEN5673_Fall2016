public class Board {
    private int size; 
    private int[][] tiles;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of blocks out of place
    public int hamming() {
        int outOfPosition = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != i + j + 1 || (tiles[i][j] == 0 && (i + j + 1) != 9))
                    outOfPosition++;
            }
        }
        
        return outOfPosition;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
        }
        
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}
