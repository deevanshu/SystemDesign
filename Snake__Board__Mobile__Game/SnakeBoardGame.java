package Snake__Board__Mobile__Game;
import java.util.*;

// Directions the snake can move
enum SnakeDirection {
	UP, DOWN, LEFT, RIGHT
}

// Snake Game interface
interface SnakeGame {
	
	void moveSnake(SnakeDirection dir);  // Move the snake in given direction
	boolean isGameOver();                // Check if game has ended
}

// Snake Game implementation
class SnakeGameImpl implements SnakeGame {

	private Queue<int[]> snake;          // Snake body -> each element is [row, col]
	private Set<String> occupied;        // Fast lookup for self-collision
	private int moves;                   // Count moves to decide when to grow
	private boolean gameOver;
	private int rows, cols;
	private char[][] board;

	// Constructor: initialize board and snake
	SnakeGameImpl(int rows, int cols) {
		board = new char[rows][cols];
		this.rows = rows;
		this.cols = cols;
		this.snake = new LinkedList<>();
		this.occupied = new HashSet<>();
		this.moves = 0;
		this.gameOver = false;

		// Initial snake of size 3 at top row (0,0) → (0,1) → (0,2)
		snake.add(new int[]{0, 0}); // equivalent to int[] arr = new int[2]; arr[0] = 0; arr[1] = 0;
		snake.add(new int[]{0, 1});
		snake.add(new int[]{0, 2});

		for (int[] part : snake) {
			occupied.add(part[0] + "," + part[1]); // [ "0,0"  , "0,1" , "0,2"]
		}
	}

	// Move snake in given direction
	public void moveSnake(SnakeDirection dir) {
		if (gameOver) return;

		moves++; // Count each move

		// Current head of snake
		int[] head = ((LinkedList<int[]>) snake).getLast(); 
		int r = head[0], c = head[1];

		// Update coordinates based on direction
		if (dir == SnakeDirection.UP) r--;
		else if (dir == SnakeDirection.DOWN) r++;
		else if (dir == SnakeDirection.LEFT) c--;
		else if (dir == SnakeDirection.RIGHT) c++;

		String key = r + "," + c; // key after changing either r or c 

		// Check collision with wall or self
		if (r < 0 || c < 0 || r >= rows || c >= cols || occupied.contains(key)) {
			gameOver = true;
			System.out.println("Game Over! Snake crashed.");
			return;
		}

		// Add new head
		snake.add(new int[]{r, c});
		occupied.add(key);

		// Grow every 5 moves, else remove tail
		if (moves % 5 != 0) {
			int[] tail = snake.poll();
			occupied.remove(tail[0] + "," + tail[1]);
		}
	}

	public boolean isGameOver() {
		return gameOver;
	}

	// Utility: Print current board with snake
	public void printBoard() {
		
		for (char[] row : board) Arrays.fill(row, '.'); // empty cell

		for (int[] part : snake) {
			board[part[0]][part[1]] = 'S'; // mark snake body
		}

		// Print board row by row
		for (char[] row : board) {
			for (char cell : row) System.out.print(cell + " ");
			System.out.println();
		}
		
		System.out.println();
	}
}

// Demo class with main()
public class SnakeBoardGame {

	public static void main(String[] args) {
		// Initialize a 5x5 board
		SnakeGameImpl game = new SnakeGameImpl(5, 5);

		// Print initial board
		System.out.println("Initial State:");
		game.printBoard();

		// Simulate moves ( hardcoded instead of taking i/p every time from user
		SnakeDirection[] moves = {
				SnakeDirection.RIGHT, SnakeDirection.DOWN, SnakeDirection.DOWN, SnakeDirection.LEFT, SnakeDirection.UP, SnakeDirection.UP
		};

		for (SnakeDirection move : moves) {
			System.out.println("Move: " + move);
			game.moveSnake(move);
			game.printBoard();

			if (game.isGameOver()) break;
		}
	}
}

