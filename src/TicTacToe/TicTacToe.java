package TicTacToe;
// TicTacToe
// Bjørn Christensen, 1/3 2014

import java.util.ArrayList;
import java.util.Scanner;

class Move {
  char player;  // 'X' , 'O'
  int row, col;

  Move(char player, int row, int col) {
    this.player = player;
    this.row = row;
    this.col = col;
  }

  public String toString() {
    return player + " to " + "(" + row + "," + col + ")";
  }
}

class GameState {
  char[][] board = new char[3][3];  // board index is [row][col] like this:
                                    // [0][0]  [0][1]  [0][2]
                                    // [1][0]  [1][1]  [1][2]
                                    // [2][0]  [2][1]  [2][2]
  char playerToMove;                // 'X' or 'O'
  Move bestMove;
  static int[][] fieldValueTable = {{3, 2, 3},
                                    {2, 4, 2},
                                    {3, 2, 3}};
  static int inf=100;               // very big number

  GameState() {                     // Default constructor creates initial position
    playerToMove = 'X';             // 'X' is always first player
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        board[r][c] = ' ';
      }
    }
  }

  GameState(GameState s) {              // Copy constructor
    playerToMove = s.playerToMove;
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        board[r][c] = s.board[r][c];
      }
    }
  }

  // The alpha-beat algorithm
  // X is maximizer, O is Minimizer
  // Depth d counts down
  double AB(double a, double b, int d){
    if (threeInARow()){       // End of game
      if (playerToMove=='X')  // last piece was set by 'O' so Min won the game
        return -(inf+d);      // subtract d to win in as few moves as possible
      else                    // Max won
        return inf+d;
    }
    if (boardIsFull()) return 0;  // game is draw
    
    if (d==0) return staticEvaluation();  // this is a leaf node
    
    ArrayList<Move> movelist=generateMoves();
    if (playerToMove=='X'){  // this is a Maximizer node
      int i=0;
      while ((a<b) && (i<movelist.size())){
        GameState si=makeMove(movelist.get(i));
        double v=si.AB(a, b, d-1);
        if (v>a){
          a=v;
          bestMove=movelist.get(i);
        }
        i++;
      }
      return a;
    }
    else { // this is a Minimizer node
      int i=0;
      while ((a<b) && (i<movelist.size())){
        GameState si=makeMove(movelist.get(i));
        double v=si.AB(a, b, d-1);
        if (v<b){
          b=v;
          bestMove=movelist.get(i);
        }
        i++;
      }
      return b;
    }
  }
  
  ArrayList<Move> generateMoves() {
    ArrayList<Move> movelist = new ArrayList<Move>();
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        if (board[r][c] == ' ') {
          movelist.add(new Move(playerToMove, r, c));
        }
      }
    }
    return movelist;
  }

  GameState makeMove(Move m) { // return new gameState with move implemented. This gameState is untouched
    GameState newState = new GameState(this);  // copy this state
    newState.board[m.row][m.col] = m.player;   // make the move
    if (playerToMove == 'X') {
      newState.playerToMove = 'O';
    } else {
      newState.playerToMove = 'X';             // change playerToMove
    }
    return newState;
  }
  
  boolean threeInARow(){
    if ((board[0][0]!=' ')&&(board[0][0]==board[0][1])&&(board[0][0]==board[0][2])) return true; // row 0
    if ((board[1][0]!=' ')&&(board[1][0]==board[1][1])&&(board[1][0]==board[1][2])) return true; // row 1
    if ((board[2][0]!=' ')&&(board[2][0]==board[2][1])&&(board[2][0]==board[2][2])) return true; // row 2
    if ((board[0][0]!=' ')&&(board[0][0]==board[1][0])&&(board[0][0]==board[2][0])) return true; // col 0
    if ((board[0][1]!=' ')&&(board[0][1]==board[1][1])&&(board[0][1]==board[2][1])) return true; // col 1
    if ((board[0][2]!=' ')&&(board[0][2]==board[1][2])&&(board[0][2]==board[2][2])) return true; // col 2
    if ((board[0][0]!=' ')&&(board[0][0]==board[1][1])&&(board[0][0]==board[2][2])) return true; // diagonal
    if ((board[0][2]!=' ')&&(board[0][2]==board[1][1])&&(board[0][2]==board[2][0])) return true; // diagonal
    return false;
  }

  boolean boardIsFull() {
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        if (board[r][c] == ' ') return false;
      }
    }
    return true;
  }
  
  boolean gameIsOver(){
    return (threeInARow() || boardIsFull());
  }
  
  boolean fieldIsEmpty(int r, int c){
    return board[r][c]==' ';
  }
  
  double staticEvaluation(){
    double result=0;
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        if (board[r][c] == 'X') result+=fieldValueTable[r][c];  // 'X' is Maximizer
        if (board[r][c] == 'O') result-=fieldValueTable[r][c];  // 'O' is Minimizer
      }
    }
    return result;
  }
  
  public String toString() {
    String result = "-----" + "\n";
    for (int r = 0; r < 3; r++) {
      result += "|";
      for (int c = 0; c < 3; c++) {
        result += board[r][c];
      }
      result += "|\n";
    }
    result += "-----";
//    result+="\nplayerToMove=" + playerToMove + " staticEvaluation()=" + staticEvaluation();
//    result+=" generateMoves()=" + generateMoves();
    return result;
  }
}

public class TicTacToe {
  public static void main(String[] args) {
    char user='X';             // Set user to play 'X' or 'O'. 'X' starts the game
    int depth=9;               // Search depth in gametree
   
    Scanner keyboard=new Scanner(System.in);
    GameState currentState=new GameState();
    System.out.println(currentState);
    while(!currentState.gameIsOver()) {
      Move nextMove;
      if (currentState.playerToMove==user){ // user moves
        int r,c;
        do {
          System.out.println("Enter your move - row and column");
          r=keyboard.nextInt();
          c=keyboard.nextInt();
        } while (!(((0<=r)&&(r<=2))&&((0<=c)&&(c<=2))&&currentState.fieldIsEmpty(r, c)));
        nextMove=new Move(user,r,c);
      } 
      else {  // computer moves
        currentState.AB(-(GameState.inf+10), GameState.inf+10, depth);
        nextMove=currentState.bestMove;
      }
      currentState=currentState.makeMove(nextMove);
      System.out.println(currentState);
    }
    
    if (currentState.threeInARow()){
      if (currentState.playerToMove=='X')  // last piece was set by 'O' so 'O' won the game
        System.out.println("Game won by O");
      else
        System.out.println("Game won by X");
    }
    if (currentState.boardIsFull())
      System.out.println("Game is draw");
  }
}