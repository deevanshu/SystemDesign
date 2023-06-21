package  Chess;

import java.util.LinkedList;
import java.util.Queue;

public class ChessMain {

	private Board board;
	Queue<Player> turn = new LinkedList<>();
	private GameStatus status;

	public static void main(String args[]) {

		Player p1 = new Player("Divs", 1, true);

		Player p2 = new Player("Arora" , 2 , false);

		Queue <Player>players1 = new LinkedList<>();
		players1.add(p1);
		players1.add(p2);

		Board board = null;
		playerMove(board , players1 ,0 ,0 ,1 ,2 );
	}

	public static boolean playerMove(Board board , Queue <Player>players1 , int startX , int endX , int startY , int endY) {

		Box start = new Box(startX , startY , null) ; 
		Box end   = new Box(endX   , endY   , null) ;
		
		Piece sourcePiece = start.getPiece();
		boolean result = sourcePiece.canMove(board, start, end);
		if(result==true) {
			end.getPiece().setKilled(true);
			end.setPiece(sourcePiece);
		}
		return result;
		//Box end   = startY;
	}
}
enum   GameStatus{

	FINISHED , ONGOING;

}
// pawn , Rook , Knight (horse), Bishop
abstract class Piece{

	private boolean white;
	private boolean killed=false;

	public Piece(boolean white) {

		this.white = white;
		this.killed = false;
	}

	public boolean isWhite() {

		return this.white;
	}

	public boolean isKilled() {

		return this.killed;
	}

	public void setWhite(boolean white) {
		this.white = white;
	}

	public void setKilled(boolean killed) {
		this.killed = killed;
	}

	public abstract boolean canMove(Board board , Box start , Box end);
}

class King extends Piece{

	public King(boolean white) {
		super(white);
	}

	@Override
	public boolean canMove(Board board, Box start, Box end) {

		if(start.getPiece()!=null) {
			if(start.getPiece().isWhite() && end.getPiece().isWhite()) {
				return false;
			}
			if(!start.getPiece().isWhite() && !end.getPiece().isWhite()) {
				return false;
			}
		}

		int x = Math.abs(end.getX() - start.getX());
		int y = Math.abs(end.getY() - start.getY());
		if( x + y == 1 || x+y ==2  ) {
			return true;
		}
		else {
			return false; 
		}
	}
}

class Queen extends Piece{

	public Queen(boolean white) {
		super(white);
	}

	@Override
	public boolean canMove(Board board, Box start, Box end) {
		return false;
	}
}
class Rook extends Piece{

	public Rook(boolean white) {
		super(white);
	}

	@Override
	public boolean canMove(Board board, Box start, Box end) {
		return false;
	}
}
class Pawn extends Piece{

	public Pawn(boolean white) {
		super(white);
	}

	@Override
	public boolean canMove(Board board, Box start, Box end) {
		return false;
	}
}
class Bishop extends Piece{

	public Bishop(boolean white) {
		super(white);
	}

	@Override
	public boolean canMove(Board board, Box start, Box end) {
		return false;
	}
}
class Knight extends Piece{

	public Knight(boolean white) {
		super(white);
	}

	@Override
	public boolean canMove(Board board, Box start, Box end) {

		if(end.getPiece().isWhite()==this.isWhite()) {
			return false;
		}
		int x = Math.abs(end.getX() - start.getX());
		int y = Math.abs(end.getY() - start.getY());
		if(( x * y == 2  && (end.getPiece()==null || !end.getPiece().isWhite())) ) {
			return true;
		}
		else {
			return false; 
		}
	}
}
class Box{

	private int x ;
	private int y ;
	private Piece piece;
	// If no piece is there then null is passed in piece place
	public Box(int x, int y, Piece piece) {

		this.x = x;
		this.y = y;
		this.piece = piece;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

}

class Board{

	Box[][] boxes;	

	public Board() {

		this.initializeBoard();
	}

	private void initializeBoard() {

		boxes[0][0] = new Box(0,0,new Rook(true)); // white elephant at 0 ,0 for white
		boxes[0][7] = new Box(0,7,new Rook(true));//  white elephant at 0 ,7 for white

		boxes[7][0] = new Box(7,0,new Rook(false)); // black elephant at 7 ,0 for white
		boxes[7][7] = new Box(7,7,new Rook(false));// black elephant at 7 ,7 for white
        
		// Similarly for all the black & white pieces like this only .
	}
}

class Player{

	private String PlayerName;
	private int Id;
	boolean whiteSide;

	public Player(String playerName, int id, boolean whiteSide) {
		PlayerName = playerName;
		Id = id;
		this.whiteSide = whiteSide;
	}
}