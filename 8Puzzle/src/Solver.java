import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<SearchNode> pq;
    private SearchNode dequeue; // Holder for dequeued search nodes

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        pq = new MinPQ<>();

        // First, insert the initial search node (initial board, 0 moves, and a null previous search node) into the queue
        pq.insert(new SearchNode(initial, null, 0));
        
        // Repeat this procedure until the search node dequeued corresponds to a goal board
        while (!pq.isEmpty()) {
            // Then, delete from the queue the search node with the minimum priority
            dequeue = pq.delMin();

            // Check if this board is the goal board
            if (dequeue.board.isGoal()) {
                break;
            }
            
            // Otherwise, insert onto the priority queue all neighboring search nodes -- those that can be reached in 1 move from the dequeued node
            int moves = dequeue.moves;
            moves++;
            Iterable<Board> neighbors = dequeue.board.neighbors();
            for (Board board : neighbors) {
                if (board != dequeue.board)
                    pq.insert(new SearchNode(board, dequeue, moves));
            }
        }
        
        
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return false;
    }

    // minimum number of moves to solve initial board; -1 if unsolvable
    public int moves() {                    
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
    private class SearchNode {
        Board board;
        SearchNode previous;
        int moves = 0;
        
        public SearchNode(Board board, SearchNode previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
        }
    }
}